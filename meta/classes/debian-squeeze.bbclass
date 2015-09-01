#
# debian-squeeze.bbclass
#
# Common functions for Debian recipes
#
# Task flow of a recipe which inherits this bbclass is the following:
#   ...
#   -> do_fetch_srcpkg  : Fetch all Debian source packages to "DL_DIR"
#   -> do_fetch         : (Poky default)
#   -> do_unpack_srcpkg : Unpack fetched Debian source packages "WORKDIR"
#   -> do_unpack        : (Poky default)
#   -> do_patch_srcpkg  : Apply debian/patches/* for each source package
#   -> do_patch         : (Poky default)
#   ...
# Fetched, unpacked and patched source package files are defined
# according to "DEBIAN_SQUEEZE_SRCPKG" and "DEBIAN_SQUEEZE_SUBSRCPKGS"
#

inherit debian-squeeze-git

# DEBIAN_SQUEEZE_SRCPKG
#   Main source package name and version.
#   Directory "S" must point a directory included in this package.
#   e.g. "busybox_1.17.1-8"
# DEBIAN_SQUEEZE_SRCPKG_NAME
#   Source package name. It must be re-defined if
#   "BPN" and Debian source package name differ.
# DEBIAN_SQUEEZE_SRCPKG_VERSION
#   Source package version.
DEBIAN_SQUEEZE_SRCPKG_NAME ?= "${BPN}"
DEBIAN_SQUEEZE_SRCPKG_VERSION ?= "${PV}"
DEBIAN_SQUEEZE_SRCPKG ?= "${DEBIAN_SQUEEZE_SRCPKG_NAME}_${DEBIAN_SQUEEZE_SRCPKG_VERSION}"
DEBIAN_SQUEEZE_UNPACKDIR ?= "${WORKDIR}/${DEBIAN_SQUEEZE_SRCPKG_NAME}-${DEBIAN_SQUEEZE_SRCPKG_VERSION}"

# Parameters of sub source packages (Whitespace-separated array).
# These source packages are used to get some files
# required to build the main source package.
# e.g. "busybox_1.17.1-8 eglibc_2.11.2-10 apt_0.8.10.3"
DEBIAN_SQUEEZE_SUBSRCPKGS ?= ""

# Default git server
DEBIAN_SQUEEZE_GIT ?= "${DEBIAN_SQUEEZE_GIT_APP}"

# "S" must be re-defined if main source package directory has
# a sub source directory which includes configure* or Makefile*
S = "${DEBIAN_SQUEEZE_UNPACKDIR}"

# SRC_URI must not be used to remote sources
SRC_URI = ""

# information for do_package
PKG_SRC_CATEGORY ?= "debian-squeeze"
PKG_SRC_NAME ?= "${DEBIAN_SQUEEZE_SRCPKG_NAME}"
PKG_SRC_VERSION ?= ""
PKG_SRC_URI ?= ""

#
# Fetch functions
#

# concatenate "DEBIAN_SQUEEZE_SRCPKG" and "DEBIAN_SQUEEZE_SUBSRCPKGS"
def debian_squeeze_get_srcpkg_list(d):
	srcpkg = bb.data.getVar("DEBIAN_SQUEEZE_SRCPKG", d, True)
	if (srcpkg == ""):
		bb.fatal("SRCPKG is empty")
	subsrcpkgs = bb.data.getVar("DEBIAN_SQUEEZE_SUBSRCPKGS", d, True)
	return (srcpkg + " " + subsrcpkgs).split()

addtask fetch_srcpkg before do_fetch
do_fetch_srcpkg[dirs] = "${DL_DIR}"
python do_fetch_srcpkg() {
	git = bb.data.getVar("DEBIAN_SQUEEZE_GIT", d, True)
	srcpkg_list = debian_squeeze_get_srcpkg_list(d)

	# Generate internal srcuri
	srcuri = ""
	for i, srcpkg in enumerate(srcpkg_list):
		splited = srcpkg.split("_")
		if (len(splited) != 2):
			bb.fatal("%s: Invalid SRCPKG name" % (srcpkg))

		srcuri += git + splited[0] + ".git"

	# Do fetch
	if (srcuri != ""):
		debian_squeeze_fetch(d, srcuri)
}

#
# Unpack functions
#

debian_squeeze_unpack_git() {
	SRCPKG="$1"
	SRCPKG_DIR="$2"

	REPOPREFIX=""

	NAME=$(echo ${SRCPKG} | sed "s|_.*||")
	GIT_URI="${DEBIAN_SQUEEZE_GIT}$NAME"
	GIT_FETCH_DIR=$(echo "$GIT_URI" | sed "s|^git://||;s|/|.|g")

	# Use the latest commit of ${DEBIAN_SQUEEZE_CODENAME}-master
	# branch if DEBIAN_SQUEEZE_SRCPKG_VERSION is "git" (default).
	# Otherwise, use the specified version by tag.
	if [ "${DEBIAN_SQUEEZE_SRCPKG_VERSION}" = "git" ]; then
		cd ${GITDIR}/$GIT_FETCH_DIR.git
		echo "DEBIGINFO: Checking branch for ${DEBIAN_SQUEEZE_CODENAME}-security-master"
		if git show-ref | grep -q "${DEBIAN_SQUEEZE_CODENAME}-security-master"; then
			# Overwrite REPOPREFIX
			REPOPREFIX="-security"
		fi
		GIT_COMMIT_DEFAULT="${DEBIAN_SQUEEZE_CODENAME}${REPOPREFIX}-master"
	else
		# git-debimport automatically converts "~" to "_"
		GIT_COMMIT_DEFAULT=$(echo "v${DEBIAN_SQUEEZE_SRCPKG_VERSION}" | sed "s@~@_@g")
	fi
	debian_squeeze_git_unpack "$GIT_URI" "$GIT_COMMIT_DEFAULT" "$SRCPKG_DIR"

	# export Debian version information for do_package
	if [ -f $SRCPKG_DIR/debian/changelog ]; then
		head -1 $GIT_UNPACK_DIR/debian/changelog | sed "s@.*(\(.*\)).*@\1@" > ${WORKDIR}/.debian-version
	else
		echo "$SRCPKG_DIR/debian/changelog not found"
		exit 1
	fi
}

addtask unpack_srcpkg before do_unpack after do_fetch
do_unpack_srcpkg[dirs] = "${WORKDIR}"
do_unpack_srcpkg() {
	for srcpkg in ${DEBIAN_SQUEEZE_SRCPKG} ${DEBIAN_SQUEEZE_SUBSRCPKGS}; do
		srcpkg_dir="${WORKDIR}/"$(echo $srcpkg | sed "s/_/-/")
		rm -rf $srcpkg_dir
		debian_squeeze_unpack_git $srcpkg $srcpkg_dir
	done
}

#
# Patch functions
#

# Filter list for "patchfiles". Sometimes unused patches lie in debian directory.
# You can exclude them easily by setting filter values into this variable.
# This variable is used as arguments of grep -v, i.e.
#   grep -v ${DEBIAN_SQUEEZE_PATCH_FILTER}
# so multiple values can be set with -e options, e.g.
#   DEBIAN_SQUEEZE_PATCH_FILETER = "-e filter1 -e filter2"
# This variable must not be empty.
DEBIAN_SQUEEZE_PATCH_FILTER ?= "debian-squeeze_patch_filter"

# Apply all patches in debian/patches/*
# NOTE: "quilt" and "dpatch" must be installed in native system
addtask patch_srcpkg before do_patch after do_unpack
do_patch_srcpkg[dirs] = "${WORKDIR}"
do_patch_srcpkg() {
	for srcpkg in ${DEBIAN_SQUEEZE_SRCPKG} ${DEBIAN_SQUEEZE_SUBSRCPKGS}; do
		srcpkg_dir="${WORKDIR}/"$(echo $srcpkg | sed "s/_/-/")
		cd $srcpkg_dir

		# Some build fails make "srcpkg_dir" be in an incomplete state
		if [ -d .pc -o -d .pc.debian ]; then
			echo "$srcpkg: Unknown patches are already applied"
			exit 1
		fi
		# some packages don't have debian/patches
		if [ ! -d debian/patches ]; then
			echo "NOTE: $srcpkg: debian/patches not found"
			# find patch-related files
			patchfiles=$(find debian \( \
				-name "*patch" -o \
				-name "*.diff" -o \
				-name "series" -o \
				-name "00list" \) | grep -v ${DEBIAN_SQUEEZE_PATCH_FILTER} || true)
			if [ -z "$patchfiles" ]; then
				# nothing to do
				echo "$srcpkg: no patch-related file found, skipping"
				continue
			else
				# some packages have patches in non-standard
				# directories like debian/patches-xxx
				echo "ERROR: $srcpkg: patch-related files found"
				echo "maybe you need to override do_patch_srcpkg"
				echo "to apply the following files"
				echo ""
				echo "$patchfiles"
				exit 1
			fi
		fi

		# Apply patches according to their patch format
		if [ -f debian/patches/series ]; then
			# quilt
			if [ -s debian/patches/series ]; then
				QUILT_PATCHES=debian/patches \
				quilt --quiltrc /dev/null push -a || test $$? = 2
			else
				echo "$srcpkg: No patches in series"
			fi
		elif [ -f debian/patches/00list ]; then
			# dpatch
			dpatch apply-all
		else
			# Unsupported format
			echo "$srcpkg: Unsupported patch format"
			exit 1
		fi

		# Rename .pc to avoid conflict with "do_patch"
		if [ -d .pc ]; then
			mv .pc .pc.debian
		fi
	done
}

EXPORT_FUNCTIONS do_fetch_srcpkg do_unpack_srcpkg do_patch_srcpkg
