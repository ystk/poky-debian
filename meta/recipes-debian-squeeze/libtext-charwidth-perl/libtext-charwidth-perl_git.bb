#
# debian-squeeze
#

DESCRIPTION = "get display widths of characters on the terminal"
SECTION = "perl"
LICENSE = "Artistic & GPL"
LIC_FILES_CHKSUM = "file://README;md5=d8d54c8c500cbdd57a4c15911d9d96db"

inherit debian-squeeze
inherit cpan

FILES_${PN}-dbg =+ ${libdir}/perl/*/*/auto/Text/CharWidth/.debug
