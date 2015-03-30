LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=6e29c688d912da12b66b73e32b03d812"
DEPENDS = "gettext"

PR = "r0"

inherit debian-squeeze autotools

SRC_URI = " \
file://libelf-cross.patch \
"

do_configure_prepend() {
	echo "Add install path to instroot in Makefile.in"

	cat Makefile.in | sed "s%instroot =%instroot = ${D}%" > Makefile.in.tmp
	mv Makefile.in.tmp Makefile.in

	cat lib/Makefile.in | sed "s%instroot =%instroot = ${D}%" \
							> lib/Makefile.in.tmp
	mv lib/Makefile.in.tmp lib/Makefile.in

	cat po/Makefile.in | sed "s%instroot =%instroot = ${D}%" \
							> po/Makefile.in.tmp
	mv po/Makefile.in.tmp po/Makefile.in
}

# Don't use autoconf because "libelf-cross.patch" is created for configure file
do_configure() {
	cd ${S}
	oe_runconf
}
