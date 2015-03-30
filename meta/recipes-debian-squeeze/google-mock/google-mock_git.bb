
#
# debian-squeeze
#
DESCRIPTION = "Google's framework for writing and using C++ mock classes"
SECTION = "devel"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=cbbd27594afd089daa160d3a16dd515a"


inherit debian-squeeze
inherit autotools
DEPENDS += "gtest"
