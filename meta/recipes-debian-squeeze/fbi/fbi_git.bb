LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

PR = "r0"

inherit debian-squeeze autotools

SRC_URI = "file://disable_extra_format.patch"

DEPENDS = " \
freetype \
fontconfig \
libexif \
jpeg \
giflib \
libpng \
"

RDEPENDS_${PN} = " \
freetype \
fontconfig \
libexif \
jpeg \
giflib \
libpng \
ttf-takao \
"
