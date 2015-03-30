#
# debian-squeeze-git.bbclass
#

inherit debian-squeeze-fetch

# Dummy SRCREV to clone repository without checkout in do_fetch_*
SRCREV = "master"

DEBIAN_SQUEEZE_GIT_LIST_BASE = "${TMPDIR}/debian-squeeze-git.list"

# define patterns to be excluded from target of fixing timestamp
DEBIAN_SQUEEZE_GIT_TSFIX_EXCLUDES ?= ""

debian_squeeze_git_unpack() {
	GIT_URI="$1"
	GIT_COMMIT_ARG="$2"
	GIT_UNPACK_DIR="$3"

	# Clone from bare repository
	GIT_FETCH_DIR=$(echo "$GIT_URI" | sed "s|^git://||;s|/|.|g")
	echo "git clone ${GITDIR}/$GIT_FETCH_DIR $GIT_UNPACK_DIR"
	git clone ${GITDIR}/$GIT_FETCH_DIR $GIT_UNPACK_DIR

	cd $GIT_UNPACK_DIR

	# Define GIT_COMMIT (the target commit of git checkout)
	# Priority: DEBIAN_SQUEEZE_TAG >> DEBIAN_SQUEEZE_PREFERRED_TAG > GIT_COMMIT_ARG
	# When DEBIAN_SQUEEZE_TAG is defined, DEBIAN_SQUEEZE_PREFERRED_TAG and
	# GIT_COMMIT_ARG are completely ignored. DEBIAN_SQUEEZE_PREFERRED_TAG
	# is used only if it's available. If DEBIAN_SQUEEZE_PREFERRED_TAG is
	# undefined or unavailable, GIT_COMMIT_ARG is used instead.
	if [ -n "${DEBIAN_SQUEEZE_TAG}" ]; then
		# DEBIAN_SQUEEZE_TAG must be already defined in git
		GIT_COMMIT="${DEBIAN_SQUEEZE_TAG}"
	elif [ -n "${DEBIAN_SQUEEZE_PREFERRED_TAG}" ]; then
		# use DEBIAN_SQUEEZE_PREFERRED_TAG only if available in git
		if git tag | grep -q "^${DEBIAN_SQUEEZE_PREFERRED_TAG}$"; then
			GIT_COMMIT="${DEBIAN_SQUEEZE_PREFERRED_TAG}"
		else
			GIT_COMMIT="$GIT_COMMIT_ARG"
		fi
	else
		GIT_COMMIT="$GIT_COMMIT_ARG"
	fi
	[ -n "$GIT_COMMIT" ] || { echo "ERROR: GIT_COMMIT is not defined"; exit 1; }

	# Do checkout
	echo "git checkout -f $GIT_COMMIT"
	git checkout -f $GIT_COMMIT
	# Update time stamp of each file according to its commit date.
	# git stores no time stamp information. It causes various
	# build sequences and sometimes it failed because of a various reasons.
	# The following steps is required to always do the same build processes.
	DIRNAME=$(basename $GIT_UNPACK_DIR)
	git ls-files | sort > ${WORKDIR}/.git-files.$DIRNAME
	for ex in ${DEBIAN_SQUEEZE_GIT_TSFIX_EXCLUDES}; do
		ex_conv=$(echo $ex | sed "s@/@\\\\/@g")
		echo "excluded from ${WORKDIR}/.git-files.$DIRNAME: $ex_conv"
		sed -i "/$ex_conv/d" ${WORKDIR}/.git-files.$DIRNAME
	done
	while read file; do
		[ ! -L "$file" ] || continue
		# Get commit time from git
		time=$(git log -n 1 --pretty=format:%ci "$file")
		stamp=$(date -d "$time" +"%y%m%d%H%M.%S")
		touch -t $stamp "$file"
	done < ${WORKDIR}/.git-files.$DIRNAME

	GIT_DIRPATH=$(echo $GIT_URI | sed "s@^.*://[^/]*/@@")
	GIT_SERVER=$(echo $GIT_URI | sed "s@^.*://\([^/]*\)/.*@\1@")
	GIT_COMMITID=$(git log -1 --pretty=format:%H)
	# fetch & checkout log
	echo "$GIT_DIRPATH $GIT_COMMITID" \
		>> ${DEBIAN_SQUEEZE_GIT_LIST_BASE}.$GIT_SERVER
	# used to generate rootfs-summary
	echo "$GIT_URI;$(echo $GIT_COMMIT | sed "s@.*/@@");$GIT_COMMITID" \
		>> ${WORKDIR}/.debian-squeeze-uri

	cd ${WORKDIR}
}
