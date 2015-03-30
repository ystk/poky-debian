#
# debian-squeeze
#
DESCRIPTION = "	convert text file line endings between CRLF and LF "
SECTION = "text"
HOMEPAGE = "http://freshmeat.net/projects/dos2unix"
LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://COPYING.txt;md5=4ed765c051d75f3f948c5b63e0b71a5d"

inherit autotools
inherit debian-squeeze

do_compile_prepend() {
	sed -i 's:CC=gcc::' ${S}/Makefile
}
