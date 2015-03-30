#
# debian-squeeze
#

DESCRIPTION = " Using libc functions for internationalization in Perl"
HOMEPAGE = "http://search.cpan.org/dist/gettext/gettext.pm"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=59fd505e62386656216ae20374a68934"
SECTION = "perl"
PR = "r0"

inherit debian-squeeze
inherit cpan

FILES_${PN}-dbg =+ ${libdir}/perl/*/*/auto/Locale/gettext/.debug
