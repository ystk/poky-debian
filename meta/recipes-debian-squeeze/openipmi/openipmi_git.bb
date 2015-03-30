#
# debian-squeeze
#

DESCRIPTION = "OpenIPMI is an effort to create a full-function IPMI system"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f" 
SECTION = "admin"
PR = "r0"

inherit autotools  gettext
inherit debian-squeeze

DEPENDS += "net-snmp popt ncurses"
EXTRA_OECONF += "--with-perl=no"
