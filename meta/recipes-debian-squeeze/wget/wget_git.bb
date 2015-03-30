#
# debian-squeeze
#

DESCRIPTION = "retrieves files from the web"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504" 
DEPENDS = "openssl "
PR = "r0"

inherit autotools gettext
inherit debian-squeeze

do_install (){
	oe_runmake install 'DESTDIR=${D}'
	cp -r tests ${D}/usr/share/tests
}
