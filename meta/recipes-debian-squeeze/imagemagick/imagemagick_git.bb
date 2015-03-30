#
# imagemagick/imagemagick_6.4.4-1.bb
# This recipe is imported from OpenEmbedded
# Commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

DESCRIPTION = "ImageMagick contains image manipulation and conversion tools"
HOMEPAGE = "http://www.imagemagick.org/"
SECTION = "console/utils"
LICENSE = "Apache-2.0"
# FIXME: There is much more checked libraries. All should be added or explicitly disabled to get consistent results.
DEPENDS = " bzip2 jpeg libpng  tiff zlib lcms"
# librsvg
#PR = "r10"

#SRC_URI = "ftp://ftp.nluug.nl/pub/ImageMagick/ImageMagick-${PV}.tar.bz2 \
#           file://PerlMagic_MakePatch;apply=yes \
#          "

#IMVER = "6.4.4"

#S = "${WORKDIR}/ImageMagick-${IMVER}"

inherit autotools binconfig

EXTRA_OECONF = "--program-prefix= --without-x --without-freetype --without-perl --disable-openmp"

FILES_${PN} += "${libdir}/ImageMagick-${IMVER}/modules-Q16/*/*.so \
                ${libdir}/ImageMagick-${IMVER}/modules-Q16/*/*.la \
                ${libdir}/ImageMagick-${IMVER}/config/ \
                ${datadir}/ImageMagick-${IMVER}"

FILES_${PN}-dev += "${libdir}/ImageMagick-${IMVER}/modules-Q16/*/*.a"

FILES_${PN}-dbg += "${libdir}/ImageMagick-${IMVER}/modules-Q16/*/.debug/*"

BBCLASSEXTEND = "native"

LEAD_SONAME = "libMagickCore.so.*"


#SRC_URI[md5sum] = "882ff241f6ad39655541d5055596f93b"
#SRC_URI[sha256sum] = "5a5b2779707bfd9816cf17d8f53d242c05005092da192a898ac10961b3b19dda"

#
# debian-squeeze
#

inherit debian-squeeze

LIC_FILES_CHKSUM = "file://LICENSE;md5=da982bfa303440d17878bfb39aa86b1e"

PR = "r0"

IMVER = "6.6.0"

SRC_URI += " \
file://PerlMagic_MakePatch;apply=yes \
file://skip-native-largefile-test.patch \
"
