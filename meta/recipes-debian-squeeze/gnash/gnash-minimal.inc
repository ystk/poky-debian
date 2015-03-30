#
# gnash-minimal.inc
#

DESCRIPTION = "Gnash is a GNU Flash movie player that supports many SWF v7 features"
HOMEPAGE = "http://www.gnu.org/software/gnash"
LICENSE = "GPLv2"

#DEPENDS = "virtual/libiconv virtual/libintl libtool agg libxml2 zlib boost jpeg pango curl freetype \
#           ${@base_conditional('ENTERPRISE_DISTRO', '1', '', 'ffmpeg libmad', d)}"
#RRECOMMENDS_${PN} = "libltdl"

#SRC_URI = "ftp://ftp.gnu.org/pub/gnu/gnash/${PV}/gnash-${PV}.tar.bz2"
#S = ${WORKDIR}/gnash-${PV}

inherit autotools pkgconfig

# gnash-minimal is intended for running directly on a framebuffer device
# for memory constrained devices, but does not accept all SWF files.
# As such, it is useful as a GUI frontend for dedicated SWF files.

# JPEG support and libz cannot be disabled due to a bug in 0.8.2.
# maintainer-mode is enabled to disable the testsuite.

#EXTRA_OECONF="--enable-gui=gtk \
#                --enable-renderer=agg \
#                --enable-agg \
#                --enable-gui=fb \
#                --enable-z \
#                --enable-jpeg \
#                --disable-klash \
#                --disable-glext \
#                --disable-Xft \
#                --disable-expat \
#                ${@base_conditional('ENTERPRISE_DISTRO', '1', '', '--enable-mad', d)} \
#                --disable-gstreamer \
#                ${@base_conditional('ENTERPRISE_DISTRO', '1', '', '--enable-media=ffmpeg', d)} \
#                --enable-media=ffmpeg \
#                --disable-plugin \
#                --disable-cygnal \
#                --disable-testsuite \
#                --enable-maintainer-mode \
#                --enable-fps-debug \
#                --enable-allstatic \
#                --enable-static \
#                --with-top-level=${STAGING_DIR_HOST}/usr \
#                --disable-ltdl-install \
#                --without-included-ltdl \
##  --with-ltdl-include=${STAGING_INCDIR} \
##  --with-ltdl-lib=${STAGING_LIBDIR} \
#"

# the test for error_t is wrong for uclibc, needs _GNU_SOURCE for correct
# detection because uclibc does not have argz.h which enables error_t 
#CFLAGS += " -D_GNU_SOURCE  -I${S}/libltdl  -I${STAGING_INCDIR}/libltdl $(pkgconfig --cflags gstreamer)"

PACKAGES =+ "libgnashnet libgnashamf libgnashbase libgnashserver libgnashmedia"

FILES_${PN} += "${datadir}/gnash/*png ${datadir}/gnash/*ico"
FILES_${PN}-dbg += "${libdir}/gnash/.debug"
FILES_${PN}-dev += "${libdir}/gnash/*a"
FILES_libgnashamf = "${libdir}/gnash/libgnashamf-*.so"
FILES_libgnashbase = "${libdir}/gnash/libgnashbase-*.so"
FILES_libgnashmedia = "${libdir}/gnash/libgnashmedia-*.so"
FILES_libgnashserver = "${libdir}/gnash/libgnashserver-*.so"
FILES_libgnashnet = "${libdir}/gnash/libgnashnet.so.*"

#
# debian-squeeze
#

inherit gettext

EXTRA_OECONF = " \
--enable-renderer=agg \
--enable-gui=fb \
--disable-glext \
--disable-cygnal \
--disable-testsuite \
--disable-ltdl-install \
--without-included-ltdl \
\
--disable-sound \
--enable-media=none \
--without-swfdec-testsuite \
--without-ming \
--disable-dependency-tracking \
--disable-rpath \
--disable-python \
--enable-hwaccel=none \
\
--disable-gstreamer \
--with-boost-lib=${STAGING_DIR_TARGET}${libdir} \
--with-agg-incl=${STAGING_DIR_TARGET}${includedir}/agg2 \
--with-agg-lib=${STAGING_DIR_TARGET}${libdir} \
--with-freetype-incl=${STAGING_DIR_TARGET}${includedir}/freetype2 \
--with-freetype-lib=${STAGING_DIR_TARGET}${libdir} \
--enable-doublebuf \
"

DEPENDS = "virtual/libiconv virtual/libintl libtool libxml2 zlib jpeg giflib freetype gstreamer gst-plugins-base agg boost glib-2.0 gettext libpng"

do_configure_prepend() {
	echo "Add an include path of gstreamer-0.10, glib-2.0, libxml2, freetype2"

	awk '{print}/libgnashmedia_la_CPPFLAGS =/{X=NR}NR==X{print "\t-I${STAGING_DIR_TARGET}${includedir}/gstreamer-0.10 \\"}' ${S}/libmedia/Makefile.am > ${S}/libmedia/Makefile.am.tmp
	mv ${S}/libmedia/Makefile.am.tmp ${S}/libmedia/Makefile.am

	awk '{print}/libgnashmedia_la_CPPFLAGS =/{X=NR}NR==X{print "\t-I${STAGING_DIR_TARGET}${includedir}/glib-2.0 \\"}' ${S}/libmedia/Makefile.am > ${S}/libmedia/Makefile.am.tmp
	mv ${S}/libmedia/Makefile.am.tmp ${S}/libmedia/Makefile.am

	awk '{print}/libgnashmedia_la_CPPFLAGS =/{X=NR}NR==X{print "\t-I${STAGING_DIR_TARGET}${libdir}/glib-2.0/include \\"}' ${S}/libmedia/Makefile.am > ${S}/libmedia/Makefile.am.tmp
	mv ${S}/libmedia/Makefile.am.tmp ${S}/libmedia/Makefile.am

	awk '{print}/libgnashmedia_la_CPPFLAGS =/{X=NR}NR==X{print "\t-I${STAGING_DIR_TARGET}${includedir}/libxml2 \\"}' ${S}/libmedia/Makefile.am > ${S}/libmedia/Makefile.am.tmp
	mv ${S}/libmedia/Makefile.am.tmp ${S}/libmedia/Makefile.am

	awk '{print}/AM_CPPFLAGS =/{X=NR}NR==X{print "\t-I${STAGING_DIR_TARGET}${includedir}/freetype2 \\"}' ${S}/libcore/Makefile.am > ${S}/libcore/Makefile.am.tmp
	mv ${S}/libcore/Makefile.am.tmp ${S}/libcore/Makefile.am
}

PACKAGES =+ "libgnashcore libgnashrender libgnashsound"

FILES_libgnashcore = "${libdir}/gnash/libgnashcore-*.so"
FILES_libgnashrender = "${libdir}/gnash/libgnashrender-*.so"
FILES_libgnashsound = "${libdir}/gnash/libgnashsound-*.so"

# OE
#--enable-gui=gtk \ X
#--enable-renderer=agg \ O
#--enable-agg \ X
#--enable-gui=fb \ O
#--enable-z \ X
#--enable-jpeg \ X
#--disable-klash \ X
#--disable-glext \ X
#--disable-Xft \ X
#--disable-expat \ X
#${@base_conditional('ENTERPRISE_DISTRO', '1', '', '--enable-mad', d)} \ X
#--disable-gstreamer \ X
#${@base_conditional('ENTERPRISE_DISTRO', '1', '', '--enable-media=ffmpeg', d)} \ ? => none
#--enable-media=ffmpeg \ ? => none
#--disable-plugin \ X
#--disable-cygnal \ ? => OE
#--disable-testsuite \ O
#--enable-maintainer-mode \ X
#--enable-fps-debug \ X
#--enable-allstatic \ X
#--enable-static \ X
#--with-top-level=${STAGING_DIR_HOST}/usr \ X
#--disable-ltdl-install \ X
#--without-included-ltdl \ O

# Built on Debian
#--enable-input='ps2keyboard' \ => delete (default)
#--disable-klash \ => remove (unrecognized)
#--disable-Xft \ => remove (unrecognized)
#--disable-expat \ => remove (unrecognized)
#--disable-gstreamer \ => remove (unrecognized)
#--disable-plugin \ => remove (unrecognized)
