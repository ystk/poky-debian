#
# Openembedded Source
#
DESCRIPTION = "BSD replacement for libreadline"
HOMEPAGE = "http://www.thrysoee.dk/editline/"
SECTION = "libs"
LICENSE = "BSD"
DEPENDS = "ncurses"
PR = "r1"

inherit autotools

#SRC_URI = "http://www.thrysoee.dk/editline/${PN}-${PV}-3.0.tar.gz"

#S = ${WORKDIR}/${PN}-${PV}-3.0

#SRC_URI[md5sum] = "eb4482139525beff12c8ef59f1a84aae"
#SRC_URI[sha256sum] = "602b385906b6057f52922afc42cafbceadd8bae4be43c9189ea7fa870a561d86"

#
# debian-squeeze
#
inherit debian-squeeze
LIC_FILES_CHKSUM = "file://term.h;md5=f9ab402b1895fce477bb109457d01cb4"

DEPENDS += "libbsd"
RDEPENDS += "libbsd"

S = ${WORKDIR}/${PN}-${PV}/libedit
do_configure() {
	cp ${WORKDIR}/Makefile ${S}
        chmod +x ${S}/makelist
	mkdir .a .so
}
do_compile_prepend() {
	oe_runmake fcns.h
}
do_install() {
	install -d ${D}/${libdir}
	install -m 0755 ${S}/${PN}.so ${D}/${libdir}
	install -m 0755 ${S}/${PN}.so ${D}/${libdir}/${PN}.so.2.11
	ln -s ${PN}.so.2.11 ${D}/${libdir}/${PN}.so.2
	install -m 0644 ${S}/${PN}.a ${D}/${libdir}
	install -d ${D}/${includedir}/editline
	install -m 0644 ${S}/readline/readline.h ${D}/${includedir}/editline/history.h
	install -m 0644 ${S}/readline/readline.h ${D}/${includedir}/editline
	install -m 0644 ${S}/histedit.h ${D}/${includedir}
}
SRC_URI += "file://Makefile"
