#
# debian-squeeze 
#

DESCRIPTION = "Daemon to translate SE Linux MCS/MLS sensitivity labels"
SECTION = "admin" 
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=9683c1d90054819e7aa85dbcf40cdcd5"
PR = "r0"

DEPENDS = "libcap libselinux"

inherit autotools 
inherit debian-squeeze

