#
# debian-squeeze
#
DESCRIPTION = "collection of more utilities from FreeBSD"
SECTION = "utils"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;md5=5b08f126f0ddcdded4525fc96dcf8a60"
PR = "r0"

inherit debian-squeeze autotools
DEPENDS += "readline"
do_compile_prepend() {
	echo "LDFLAGS=-lreadline -termcap" >> ${S}/Makefile
}
