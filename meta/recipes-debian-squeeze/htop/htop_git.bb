#
# htop/htop_0.9.bb
# This recipe is imported from OpenEmbedded
# Commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

DESCRIPTION = "htop process monitor"
HOMEPAGE = "http://htop.sf.net"
SECTION = "console/utils"
PRIORITY = "optional"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=c312653532e8e669f30e5ec8bdc23be3"

DEPENDS = "ncurses"
RDEPENDS_${PN} = "ncurses-terminfo"

#SRC_URI = "${SOURCEFORGE_MIRROR}/htop/htop-${PV}.tar.gz \
#           file://remove-proc-test.patch"

inherit autotools

LDFLAGS_append_libc-uclibc = " -lubacktrace"

#SRC_URI[md5sum] = "7c5507f35f363f3f40183a2ba3c561f8"
#SRC_URI[sha256sum] = "4de65c38e1886bccd30ed692b30eb9bf195240680781bfe1eaf5faf84ee6fbfd"

#
# debian-squeeze
#

inherit debian-squeeze

LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

# exclude unneeded test
SRC_URI = "file://remove-proc-test.patch"
