#
# debian-squeeze 
#

DESCRIPTION = "	ELF prelinking utility to speed up dynamic linking"
SECTION = "admin"
LICENSE = "GPLv2"	
LIC_FILES_CHKSUM = "file://COPYING;md5=c93c0550bd3173f4504b2cbd8991e50b"

DEPENDS += "popt libselinux elfutils"

inherit autotools
inherit debian-squeeze
