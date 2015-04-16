#!/bin/sh
#
# autosync.sh
#
# this scripts automatically finds commits that are related to
# project repository (poky-debian-prj.git) from poky-debian.git
# and automatically applies them to poky-debian-prj.git
#
# usage:
#   $ /path/to/poky-debian-prj/scripts/debian-squeeze/autosync/autosync.sh
#
# 1. clone the latest poky-debian to ${COREBASE_COMMON}
# 2. get all commits between ${SYNC_COMMIT} and the latest commit
# 3. classify the above commits into three categories:
#    RELATED:   all updated files are related to ${COREBASE_TARGET};
#               these commits can be applied automatically
#    UNRELATED: all updated files are not related to ${COREBASE_TARGET};
#               no need to apply these commits
#    DIRTY:     some updated files are related to ${COREBASE_TARGET},
#               but others are not related; cannot be applied automatically
# 4a. if there is no DIRTY commit, apply all RELATED commits to
#     ${COREBASE_TARGET} and update ${SYNC_COMMIT} in autosync.conf
# 4b. if there are DIRTY commits, do nothing and exit with leaving
#     patch files in ${PATCH_DIR}; user needs to apply RELATED patches
#     and solve DIRTY commits by their hands
#

AUTOSYNC_DIR=scripts/debian-squeeze/autosync
POKY_DEBIAN_SQUEEZE_URI=git://debian-squeeze.swc.toshiba.co.jp/tools/poky-debian.git
CODENAME=ashitaka

# temporal working directories
COREBASE_COMMON=$(pwd)/__poky-debian
COMMIT_DIR=$(pwd)/__commits
PATCH_DIR=$(pwd)/__patches

die() {
	echo "FATAL: $@"
	rm -rf ${COREBASE_COMMON}
	exit 1
}

#
# preparation
#

REQUIRED_CMDS="git sed realpath basename head diff"
for cmd in ${REQUIRED_CMDS}; do
	if ! which ${cmd} > /dev/null 2>&1; then
		die "command ${cmd} not found, please install first"
	fi
done

# COREBASE_TARGET: path to poky-debian-prj
COREBASE_TARGET=$(realpath ${0} | sed "s@/${AUTOSYNC_DIR}/autosync.sh@@")

CONF_TARGET=${COREBASE_TARGET}/${AUTOSYNC_DIR}/autosync.conf
if [ ! -r ${CONF_TARGET} ]; then
	die "${CONF_TARGET}: not found"
fi

# COREBASE_COMMON: path to poky-debian
rm -rf ${COREBASE_COMMON}
if ! git clone -b ${CODENAME} ${POKY_DEBIAN_SQUEEZE_URI} ${COREBASE_COMMON} \
	> /dev/null 2>&1; then
	die "failed to clone ${POKY_DEBIAN_SQUEEZE_URI}"
fi

CONF_COMMON=${COREBASE_COMMON}/${AUTOSYNC_DIR}/autosync-common.conf
if [ ! -r ${CONF_COMMON} ]; then
	die "${CONF_COMMON}: not found"
fi

# load SYNC_FILES
. ${CONF_COMMON}
. ${CONF_TARGET}

if [ -z "${SYNC_COMMIT}" ]; then
	die "${SYNC_COMMIT} not defined"
fi
if [ -z "${SYNC_FILES}" ]; then
	die "${SYNC_FILES} not defined"
fi

#
# classifying commits
#

COMMIT_FLAG="### COMMIT="

rm -rf ${COMMIT_DIR}
mkdir -p ${COMMIT_DIR}
cd ${COREBASE_COMMON}
echo "
classifying all commits into RELATED or UNRELATED..."

git show ${SYNC_COMMIT}..HEAD \
--name-only --format="format:${COMMIT_FLAG}%H" | \
while read line; do
	# commit ID
	if echo ${line} | grep -q "^${COMMIT_FLAG}"; then
		commit=$(echo ${line} | sed "s@^${COMMIT_FLAG}@@")
		statefile=${COMMIT_DIR}/${commit}
		touch ${statefile}
		echo "classifying commit ${commit}..."
	# separator line
	elif [ -z "${line}" ]; then
		continue
	# updated file names
	else
		# does this file match ${SYNC_FILES}?
		found=0
		for sf in ${SYNC_FILES}; do
			if echo "${line}" | grep -q "^${sf}/\|^${sf}\$"; then
				found=1
				break
			fi
		done

		# if this is the first file, save the result to ${statefile}
		if [ ! -s ${statefile} ]; then
			echo -n ${found} > ${statefile}
			continue
		fi

		# confirm that the result (${found}) always same in a commit
		state=$(cat ${statefile})
		if [ ${state} = 0 -a ${found} = 0 ]; then
			continue
		elif [ ${state} = 1 -a ${found} = 1 ]; then
			continue
		else
			# dirty commit;
			# the both of related and unrelated files included
			echo -n 2 > ${statefile}
		fi
	fi
done

if [ -z "$(ls ${COMMIT_DIR})" ]; then
	echo "no update found, nothing to do"
	rm -rf ${COREBASE_COMMON} ${COMMIT_DIR}
	exit 0
fi

#
# generate patches
#

rm -rf ${PATCH_DIR}
mkdir -p ${PATCH_DIR}
echo "
generating patches..."
if ! git format-patch -o ${PATCH_DIR} ${SYNC_COMMIT}..HEAD; then
	die "git format-patch failed"
fi

echo "
classifying patches..."

RPATCH_DIR=${PATCH_DIR}/related
UPATCH_DIR=${PATCH_DIR}/unrelated
DPATCH_DIR=${PATCH_DIR}/dirty
mkdir -p ${RPATCH_DIR} ${UPATCH_DIR} ${DPATCH_DIR}

dirty=0
for p in $(ls ${PATCH_DIR}/*.patch); do
	commit=$(head -1 ${p} | sed "s@^From \([^ ]*\) .*@\1@")
	state=$(cat ${COMMIT_DIR}/${commit})
	if [ ${state} = 0 ]; then
		echo "$(basename ${p}): UNRELATED"
		mv ${p} ${UPATCH_DIR}
	elif [ ${state} = 1 ]; then
		echo "$(basename ${p}): RELATED"
		mv ${p} ${RPATCH_DIR}
	elif [ ${state} = 2 ]; then
		dirty=1
		echo "$(basename ${p}): DIRTY"
		mv ${p} ${DPATCH_DIR}
	else
		die "failed to classify ${p}"
	fi
done

if [ ${dirty} = 1 ]; then
	echo ""
	echo "**************************************************************"
	echo "ERROR: dirty patches found"
	echo "Cannot apply the following patches automatically because"
	echo "the both of RELATED and UNRELATED files are updated by them."
	echo "Please update only RELATED files and commit them by your hand."
	echo "**************************************************************"
	echo ""
	echo "dirty patches:"
	ls ${DPATCH_DIR}
	echo ""
	echo "RELATED patches (clean):"
	ls ${RPATCH_DIR}
	rm -rf ${COREBASE_COMMON} ${COMMIT_DIR}
	exit 2
fi

if [ -z "$(ls ${RPATCH_DIR})" ]; then
	echo "no RELATED patch found, nothing to do"
	rm -rf ${COREBASE_COMMON} ${COMMIT_DIR} ${PATCH_DIR}
	exit 0
fi

#
# apply patches
#

cd ${COREBASE_TARGET}

echo "
applying RELATED patches..."
if ! git am ${RPATCH_DIR}/*; then
	die "git am failed"
fi

echo "
****************************************
All RELATED patches applied successfully
****************************************"

echo "
applied RELATED patches:"
ls ${RPATCH_DIR}

# clean
rm -rf ${COMMIT_DIR} ${PATCH_DIR}

# confirm tree
echo "
Here are differences between two repository trees:"
diff --exclude .git --exclude ${AUTOSYNC_DIR} \
-r ${COREBASE_COMMON} ${COREBASE_TARGET}

#
# update SYNC_COMMIT
#

echo "
updating SYNC_COMMIT..."

cd ${COREBASE_COMMON}
CURRENT_COMMIT=$(git log -1 --format="format:%H")
if [ -z "${CURRENT_COMMIT}" ]; then
	die "failed to get current commit of ${COREBASE_COMMON}"
fi
echo "old SYNC_COMMIT: ${SYNC_COMMIT}"
echo "new SYNC_COMMIT: ${CURRENT_COMMIT}"

cd ${COREBASE_TARGET}
sed -i "s@SYNC_COMMIT=.*@SYNC_COMMIT=${CURRENT_COMMIT}@" ${CONF_TARGET}
if ! git commit -s \
-m "autosync: update SYNC_COMMIT to ${CURRENT_COMMIT}" ${CONF_TARGET}; then
	die "git commit failed"
fi

# clean
rm -rf ${COREBASE_COMMON}
