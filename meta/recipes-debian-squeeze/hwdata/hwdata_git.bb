#
# debian-squeeze
#
DESCRIPTION = "This package contains various hardware identification and configuration data"
HOMEPAGE = "http://git.fedorahosted.org/git/hwdata.git"
SECTION = "misc"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f\
		    file://LICENSE;md5=1556547711e8246992b999edd9445a57"
PR = "r0"

inherit autotools gettext
inherit debian-squeeze
