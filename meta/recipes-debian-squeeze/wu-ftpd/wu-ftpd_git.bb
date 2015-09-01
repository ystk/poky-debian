#
# debian-squeeze 
#

DESCRIPTION = "powerful and widely used FTP server"
SECTION = "net" 
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=d06717198a1e31bdb99dbeaaf4620b3d"
PR = "r0"

DEPENDS = "perl debianutils"

inherit debian-squeeze autotools 

# autoheader (autoreconf) should not be called for this source because
# some temprates like DAEMON are missing and they cannot be enabled.
# In order to keep old config.h.in that enables DAEMON, we use oe_runconf.
do_configure() {
	oe_runconf
}
