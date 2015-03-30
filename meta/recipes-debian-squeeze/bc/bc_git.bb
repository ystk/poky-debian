#
# HIDDEN/recipes-extended/bc/bc_1.06.bb 
#

DESCRIPTION = "An arbitrary precision calculator language."
HOMEPAGE = "http://www.gnu.org/software/bc/bc.html"
BUGTRACKER = ""

LICENSE = "GPLv2+ & LGPLv2.1"
#LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f \
#                    file://COPYING.LIB;md5=d8045f3b8f929c1cb29a1e3fd737b499 \
#                    file://bc/bcdefs.h;endline=31;md5=46dffdaf10a99728dd8ce358e45d46d8 \
#                    file://dc/dc.h;endline=25;md5=2f9c558cdd80e31b4d904e48c2374328 \
#                    file://lib/number.c;endline=31;md5=99434a0898abca7784acfd36b8191199"

SECTION = "base"
DEPENDS = "flex"
PR = "r0"

#SRC_URI = "${GNU_MIRROR}/bc/bc-${PV}.tar.gz"

#SRC_URI[md5sum] = "d44b5dddebd8a7a7309aea6c36fda117"
#SRC_URI[sha256sum] = "4ef6d9f17c3c0d92d8798e35666175ecd3d8efac4009d6457b5c99cea72c0e33"

inherit autotools

#
# debian-squeeze
#

inherit debian-squeeze
LIC_FILES_CHKSUM = "file://COPYING;md5=b492e6ce406929d0b0a96c4ae7abcccf \
                    file://COPYING.LIB;md5=bf0962157c971350d4701853721970b4 \
                    file://bc/bcdefs.h;endline=31;md5=98307a8f9946bd38e3d0202285d08797 \
                    file://dc/dc.h;endline=25;md5=7f59a1b8b1a26bcc6c4810b357519101 \
                    file://lib/number.c;endline=31;md5=d91046942ca41a87675d5e09f247caef "

BBCLASSEXTEND = "native"
