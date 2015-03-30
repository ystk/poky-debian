#
# debian-squeeze
#
DESCRIPTION = "Documentation system for C, C++, Java, Python and other languages"
LICENSE = "LGPLv2.1"
SECTION = "devel"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b380c86cea229fa42b9e543fc491f5eb"
PR = "r0"
inherit debian-squeeze autotools native
DEPENDS += "graphviz-native"
do_patch_srcpkg() {
	:
}
CONFIGUREOPTS=""
do_compile_prepend() {
	sed -i 's:INSTALL   = /usr/local:INSTALL   = /usr:' Makefile
}
do_install() {
	oe_runmake install DESTDIR=${D}${STAGING_DIR_NATIVE}
}
