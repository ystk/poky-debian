#
# debian-squeeze
#
DESCRIPTION = "Userspace interface to kernel DRM services -- development files"
SECTION = "libs"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://README;md5=4b3cbc63e3a058ac3259f62316a3ace5"
PR = "r0"

inherit debian-squeeze
inherit autotools
DEPENDS += ""

EXTRA_OECONF += " --enable-nouveau-experimental-api"

FILES_${PN} += "/usr/lib/libdrm_nouveau.so*"
