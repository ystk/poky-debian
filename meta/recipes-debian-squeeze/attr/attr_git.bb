#
# attr_2.4.46.bb
#

require attr.inc

#PR = "r2"

#SRC_URI[md5sum] = "db557c17fdfa4f785333ecda08654010"
#SRC_URI[sha256sum] = "dcd69bdca7ff166bc45141eddbcf21967999a6b66b0544be12a1cc2fd6340e1f"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

# Some library symlinks must be define by relative path
# to be shared in target sysroot. This patch breaks symlink paths
# in native sysroot, but fix_symlink func corrects the paths.
SRC_URI = "file://relative-libdir.patch"
