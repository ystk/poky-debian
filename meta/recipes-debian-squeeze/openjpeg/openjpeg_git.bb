#
# debian-squeeze
#

DESCRIPTION = "development files for libopenjpeg2, a JPEG 2000 image library"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://license.txt;md5=a57eb636ed9db19accce3d20c522609e"
SECTION = "libs"
PR = "r0"

DEPENDS += "tiff"

inherit autotools
inherit debian-squeeze

do_compile_prepend() {
	sed -i 's:CC = gcc::' ${S}/Makefile
	sed -i 's:AR = ar::' ${S}/Makefile
}

