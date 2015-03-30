#
# debian-squeeze-yocto.bbclass
#
# Common settings for recipes in recipes-yocto.
# These recipes use special source code of Yocto project.
#

inherit debian-squeeze-misc

DEBIAN_SQUEEZE_MISC_GIT = "${DEBIAN_SQUEEZE_GIT_YOCTO}"

DEBIAN_SQUEEZE_MISC_COMMIT = "${DEBIAN_SQUEEZE_CODENAME}-${DISTRO_VERSION}"

# information for do_package
# almost same as debian-squeeze-misc.bbclass
PKG_SRC_CATEGORY = "yocto"
