#
# debian-squeeze 
#
DESCRIPTION = "common build system for Debian packages"
SECTION = "devel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
DEPENDS = ""
PR = "r0"

inherit autotools
inherit debian-squeeze
do_compile_prepend() {
# skip create html and pdf file
	sed -i 's:SUBDIRS = . doc test:SUBDIRS = . test:' Makefile
}
