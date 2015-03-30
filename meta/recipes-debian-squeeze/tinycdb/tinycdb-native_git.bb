DESCRIPTION = "an utility to manipulate constant databases (cdb) \
tinycdb is a small, fast and reliable utility and subroutine \
library for creating and reading constant databases. The database \
structure is tuned for fast reading."

SECTION = "utils"
LICENSE = "Public Domain"
LIC_FILES_CHKSUM = "file://NEWS;md5=b5d16cb938b4b1626b42941052855e8b"

inherit debian-squeeze autotools native

do_compile_prepend() {
	sed -i -e "s:/local::" ${S}/Makefile
	sed -i -e "s:^CC .*:CC = ${CC} -fPIC:" ${S}/Makefile
}

do_install() {
	oe_runmake DESTDIR="${D}${STAGING_DIR_NATIVE}" install
}
