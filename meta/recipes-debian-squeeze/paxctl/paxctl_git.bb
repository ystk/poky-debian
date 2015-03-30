#
# debian-squeeze
#

DESCRIPTION = "user-space utility to control PaX flags - new major upstream version"
SECTION = "admin"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=0300f1edf2b2cadb5791f85cd84b8b23"

inherit autotools
inherit debian-squeeze

do_compile_prepend() {
	cd ${S}
	sed -i 's:CC\::#CC\::' Makefile
}
