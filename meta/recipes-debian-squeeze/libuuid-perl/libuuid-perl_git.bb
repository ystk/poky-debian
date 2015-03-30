#
# debian-squeeze
#

DESCRIPTION = "Perl extension for using UUID interfaces as defined in e2fsprogs"
SECTION = "perl"
LICENSE = "Artistic & GPL"
LIC_FILES_CHKSUM = "file://test.pl;md5=5105b151f001bd240059ad7f22b4fb41"

inherit debian-squeeze
inherit cpan

# uuid.h needed
DEPENDS += "util-linux"

FILES_${PN}-dbg =+ ${libdir}/perl/*/*/auto/UUID/.debug
