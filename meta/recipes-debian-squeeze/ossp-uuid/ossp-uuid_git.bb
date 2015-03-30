#
# debian-squeeze 
#

DESCRIPTION = "OSSP uuid ISO-C and C++ - shared library" 
SECTION = "libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=aa8bcf386d588846058900df336d3625"
PR = "r0"

DEPENDS += "libtool perl"

inherit autotools 
inherit debian-squeeze

SRC_URI += "file://fix-config.patch \
	    file://fix-makefile.patch"

do_configure() {
	cd ${S}
	aclocal
	autoheader
	oe_runconf	
}

