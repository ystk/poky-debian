#
# debian-squeeze
#
DESCRIPTION = "ISO language, territory, currency, script codes and their translations"
HOMEPAGE = "http://pkg-isocodes.alioth.debian.org"
LICENSE = "GPLv2.1 & LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fbc093901857fcd118f065f900982c24 \
		    file://README;md5=a3407094291ceaaa7247865ab36fd4c0"
SECTION = "misc"
PR = "r0"

inherit autotools
inherit debian-squeeze

FILES_${PN} += "/usr/share/*"

