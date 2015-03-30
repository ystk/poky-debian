#
# debian-squeeze
#

DESCRIPTION = "Data Management API"
SECTION = "admin"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://README;md5=a8784eb73597caaa12f40965b32fb92c"

inherit debian-squeeze
inherit autotools

PR = "r0"

DEPENDS += "xfsdump"

do_install() {
	sed -i 's:\/usr:'${D}'\/usr:g' include/builddefs
	oe_runmake install DESTDIR=${D}
}
