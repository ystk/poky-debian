#
# debian-squeeze-misc.bbclass
#
# Common functions for recipes which checkout source from
# non-standard Debian repository
#
# Task flow of a recipe which inherits this bbclass is the following:
#   ...
#   -> do_fetch_miscsrc  : Fetch git repository to "DL_DIR" (clone bare)
#   -> do_fetch          : (Poky default)
#   -> do_unpack_miscsrc : Checkout source from "DL_DIR" to "WORKDIR"
#   -> do_unpack         : (Poky default)
#   ...
#

inherit debian-squeeze-git

DEBIAN_SQUEEZE_MISC_NAME ?= "${BPN}"
DEBIAN_SQUEEZE_MISC_REPO = "${DEBIAN_SQUEEZE_MISC_GIT}${DEBIAN_SQUEEZE_MISC_NAME}.git"
DEBIAN_SQUEEZE_UNPACKDIR ?= "${WORKDIR}/${DEBIAN_SQUEEZE_MISC_NAME}-${PV}"

# Default git server
DEBIAN_SQUEEZE_MISC_GIT ?= "${DEBIAN_SQUEEZE_GIT_APP}"

# Must be set by each package.
# This value is ignored if DEBIAN_SQUEEZE_TAG or DEBIAN_SQUEEZE_PREFERRED_TAG is set
DEBIAN_SQUEEZE_MISC_COMMIT ?= " "

S = "${DEBIAN_SQUEEZE_UNPACKDIR}"

SRC_URI = ""

addtask fetch_miscsrc before do_fetch
do_fetch_miscsrc[dirs] = "${DL_DIR}"
python do_fetch_miscsrc() {
	srcuri = bb.data.getVar("DEBIAN_SQUEEZE_MISC_REPO", d, True)
	debian_squeeze_fetch(d, srcuri)
}

addtask unpack_miscsrc before do_unpack after do_fetch
do_unpack_miscsrc[dirs] = "${WORKDIR}"
do_unpack_miscsrc() {
	debian_squeeze_git_unpack ${DEBIAN_SQUEEZE_MISC_REPO} ${DEBIAN_SQUEEZE_MISC_COMMIT} \
		${DEBIAN_SQUEEZE_UNPACKDIR}
}

EXPORT_FUNCTIONS do_fetch_miscsrc do_unpack_miscsrc

# information for do_package
# PKG_SRC_VERSION should be defined in each recipe
# PKG_SRC_URI will be overwritten by do_package_deb
PKG_SRC_CATEGORY ?= "misc"
PKG_SRC_NAME ?= "${DEBIAN_SQUEEZE_MISC_NAME}"
PKG_SRC_VERSION ?= ""
PKG_SRC_URI ?= ""
