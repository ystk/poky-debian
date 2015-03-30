#
# debian-squeeze
#

DESCRIPTION = "converts between character sets in Perl"
SECTION = "perl"
LICENSE = "Artistic & GPL"
LIC_FILES_CHKSUM = "file://README;md5=b761cd2515c90411793a2f58d8920998"

inherit debian-squeeze
inherit cpan

FILES_${PN}-dbg =+ "${libdir}/perl/*/*/auto/Text/Iconv/.debug"
