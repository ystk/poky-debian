#
# debian-squeeze
#

DESCRIPTION = "Fixed-width fonts for fast reading"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=2b41ed8b801738ed60187444630d8c1f"
SECTION = "fonts"
PR = "r0"

inherit autotools
inherit debian-squeeze

FILES_${PN} += "/usr/share/fonts/*"

