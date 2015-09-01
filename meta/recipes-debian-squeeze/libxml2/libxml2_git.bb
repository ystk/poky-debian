#
# libxml2_2.7.8.bb
#

require libxml2.inc

#PR = "r2"

#SRC_URI[md5sum] = "8127a65e8c3b08856093099b52599c86"
#SRC_URI[sha256sum] = "cda23bc9ebd26474ca8f3d67e7d1c4a1f1e7106364b690d822e009fdc3c417ec"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

# NOTE: This is a temporal fix to ignore invalid patches in debian/patches.
# The same modification as these patches is already included in .diff.gz,
# and there is no function which applies debian/patches/* in debian/rules.
# We need to remove this override after the upstream source package are fixed.
do_patch_srcpkg() {
	:
}
