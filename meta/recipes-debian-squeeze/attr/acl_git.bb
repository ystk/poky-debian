#
# acl_2.2.51.bb
#

require acl.inc

#PR = "r2"

#SRC_URI[md5sum] = "3fc0ce99dc5253bdcce4c9cd437bc267"
#SRC_URI[sha256sum] = "06854521cf5d396801af7e54b9636680edf8064355e51c07657ec7442a185225"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

# Some library symlinks must be define by relative path
# to be shared in target sysroot. This patch breaks symlink paths
# in native sysroot, but fix_symlink func corrects the paths.
SRC_URI = "file://relative-libdir.patch"
