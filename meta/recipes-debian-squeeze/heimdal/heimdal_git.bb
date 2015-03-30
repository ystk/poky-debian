#
# debian-squeeze 
#
DESCRIPTION = "Heimdal"
SECTION = "net"
PR = "r0"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3d5aedd8ac19c7dcdfd994a75948e57e"

inherit autotools
inherit debian-squeeze
#DEPENDS = "openssl e2fsprogs sqlite3 db libxt heimdal-native ncurses libx11 openldap python db cdbs hesiod libice libxau libsm debconf"
DEPENDS = "openssl e2fsprogs sqlite3 db heimdal-native ncurses openldap python db cdbs hesiod libice libxau libsm debconf"
DEPENDS += "${@bb.utils.contains("DISTRO_FEATURES", "x11", "libxt libx11", "" ,d)}"
#libedit "
SRC_URI += "file://fix-code.patch \
	file://fix-make.patch" 

EXTRA_OECONF = "--with-sqlite3=${STAGING_DIR}/usr \
                --with-sqlite3-lib=${STAGING_LIBDIR} \
                --with-sqlite3-include=${STAGING_INCDIR} \
		"
PARALLEL_MAKE = ""
do_compile_prepend() {
	cd ${S}/lib/hx509
	sed -i 's:hxtool-commands.in $(SLC):hxtool-commands.in:' Makefile*
	cd ${S}
}
