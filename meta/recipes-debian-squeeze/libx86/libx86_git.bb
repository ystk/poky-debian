#
# debian-squeeze
#
DESCRIPTION = "x86 real-mode library - development files"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=633af6c02e6f624d4c472d970a2aca53"
inherit debian-squeeze
inherit autotools
DEPENDS += ""

SRC_URI += "file://update_lrmi.patch"
FILES_${PN} += "${base_libdir}/libx86.so* \
		  ${includedir}/* \
		  ${libdir}/*"
