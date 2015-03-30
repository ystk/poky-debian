#
# libftdi_0.18.bb
# This recipe is imported from OpenEmbedded
# commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

DESCRIPTION = "libftdi is a library to talk to FTDI chips.\
FT232BM/245BM, FT2232C/D and FT232/245R using libusb,\
including the popular bitbang mode."
HOMEPAGE = "http://www.intra2net.com/en/developer/libftdi/"
#LICENSE = "LGPL GPLv2+linking exception"
SECTION = "libs"

#DEPENDS = "virtual/libusb0"
#DEPENDS_virtclass-native = "virtual/libusb0-native"
#SRC_URI = "http://www.intra2net.com/en/developer/libftdi/download/libftdi-${PV}.tar.gz \
#	   file://libtool-m4.patch \
#	  "

inherit autotools binconfig pkgconfig

BBCLASSEXTEND = "native"

#SRC_URI[md5sum] = "916f65fa68d154621fc0cf1f405f2726"
#SRC_URI[sha256sum] = "5b6f3c3ee51c6aa24d3b87135e01762cf68821d1c3599d87d349fea4ede74c62"

#
# debian-squeeze
#

inherit debian-squeeze

# only ftdipp is GPLv2 with special exception (see LICENSE)
LICENSE = "LGPL-2.1 & GPL-2.0-with-special-exception"

LIC_FILES_CHKSUM = " \
file://LICENSE;md5=2e20d74de059b32006dc58fafdfa59b0 \
file://COPYING.LIB;md5=db979804f025cf55aabec7129cb671ed \
file://COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe \
"

DEPENDS = "libusb"

# libtool-m4.patch solves "QA Issue: package libftdi contains bad RPATH"
SRC_URI = "file://libtool-m4.patch"

PACKAGES =+ "${PN}-config"
FILES_${PN}-config = "${bindir}/libftdi-config"
RDEPENDS_${PN}-dev += "${PN}-config"
