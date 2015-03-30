#
# debian-squeeze
#
DESCRIPTION = "Tool for selecting tasks for installation on Debian systems"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=f503516a93218f56444c4a96ae40b7af"
SECTION = "admin"
PR = "r0"

inherit debian-squeeze autotools

DEPENDS += "liblocale-gettext-perl debconf" 

PARALLEL_MAKE = ""
