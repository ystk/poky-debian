#
# xorg-proto-common.inc
#

DESCRIPTION = "X protocol headers: ${XORG_PN}"
HOMEPAGE = "http://www.x.org"
BUGTRACKER = "https://bugs.freedesktop.org/enter_bug.cgi?product=xorg"

SECTION = "x11/libs"
LICENSE = "MIT-X"

XORG_PN = "${BPN}"

#SRC_URI = "${XORG_MIRROR}/individual/proto/${XORG_PN}-${PV}.tar.bz2"

S = "${WORKDIR}/${XORG_PN}-${PV}"

DEPENDS = "util-macros"

inherit autotools pkgconfig
EXTRA_OECONF = "--with-fop=no"

# ${PN} is empty so we need to tweak -dev and -dbg package dependencies
RDEPENDS_${PN}-dev = ""
RRECOMMENDS_${PN}-dbg = "${PN}-dev (= ${EXTENDPKGV})"

#
# debian-squeeze
#

inherit debian-squeeze
