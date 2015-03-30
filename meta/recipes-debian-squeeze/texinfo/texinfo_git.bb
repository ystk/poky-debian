DESCRIPTION = "Documentation system for on-line information and printed output"
SECTION = "console/utils"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=adefda309052235aa5d1e99ce7557010"


#
# debian-squeeze
#
inherit debian-squeeze gettext
inherit autotools
DEPENDS += "ncurses"
do_configure_prepend() {
	cp ${WORKDIR}/texinfo.txi ${S}/doc
	cp ${WORKDIR}/info.texi ${S}/doc
	cp ${WORKDIR}/info-stnd.texi ${S}/doc
	cp ${WORKDIR}/fdl.texi ${S}/doc
}
do_compile_prepend() {
	oe_runmake -C tools/gnulib/lib
}
SRC_URI = "file://gettext.patch \
		file://texinfo.txi \
		file://info.texi \
		file://info-stnd.texi \
		file://fdl.texi"
