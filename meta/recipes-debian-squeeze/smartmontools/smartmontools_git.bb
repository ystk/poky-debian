#
# debian-squeeze
#

DESCRIPTION = "control and monitor storage systems using S.M.A.R.T."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f" 
SECTION = "utils"
DEPENDS = " libselinux debianutils"
PR = "r0"

inherit autotools 
inherit debian-squeeze

SRC_URI += "file://fix_include.patch"
