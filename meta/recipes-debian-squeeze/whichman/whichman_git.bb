#
# debian-squeeze
#

DESCRIPTION = "Fault tolerant search utilities: whichman, ftff, ftwhich"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=f8fde40e05cd1e3026f89b8c754924c2"
SECTION = "utils"
PR = "r0"

inherit autotools gettext
inherit debian-squeeze

do_compile_prepend() {
	sed -i 's:CC=gcc:#CC=gcc:' Makefile
	sed -i 's:STRIP=strip:#STRIP=strip:' Makefile
}
