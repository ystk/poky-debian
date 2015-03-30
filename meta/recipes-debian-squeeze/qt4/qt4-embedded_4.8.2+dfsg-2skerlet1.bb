#
# qt4-embedded_4.6.3.bb
#

require qt-git.inc
require qt4-embedded.inc

#SRC_URI += "file://qthelp-lib-qtclucene.patch"

#PR = "${INC_PR}.1"

QT_CONFIG_FLAGS_append_armv6 = " -no-neon "

#
# debian-squeeze
#

require qt4-libs.inc

PR = "r0"

#QT_CONFIG_FLAGS = " \
#-v \
#-embedded ${QT_ARCH} \
#-release \
#-opensource \
#-make libs \
#-nomake tools \
#-nomake examples \
#-nomake demos \
#-nomake docs \
#-nomake translations \
#-no-fast \
#-no-largefile \
#-no-exceptions \
#-no-accessibility \
#-stl \
#-no-sql-sqlite \
#-no-sql-db2 \
#-no-sql-ibase \
#-no-sql-mysql \
#-no-sql-oci \
#-no-sql-odbc \
#-no-sql-psql \
#-no-sql-sqlite2 \
#-no-sql-tds \
#-system-sqlite \
#-no-qt3support \
#-no-xmlpatterns \
#-no-declarative \
#-no-multimedia \
#-no-audio-backend \
#-no-phonon \
#-no-phonon-backend \
#-no-svg \
#-no-webkit \
#-no-javascript-jit \
#-no-script \
#-no-scripttools \
#-system-zlib \
#-no-gif \
#-no-libtiff \
#${QT_CONFIG_LIBS} \
#-no-libmng \
#-system-libjpeg \
#-openssl \
#-no-nis \
#-no-cups \
#-no-iconv \
#-no-pch \
#-no-dbus \
#-system-freetype \
#-depths 16,24,32 \
#-no-opengl \
#-qt-decoration-default \
#-no-decoration-styled \
#-no-decoration-windows \
#-qt-gfx-linuxfb \
#-no-gfx-transformed \
#-no-gfx-qvfb \
#-no-gfx-vnc \
#-no-gfx-multiscreen \
#-no-gfx-directfb \
#-no-gfx-qnx \
#-no-mouse-tslib \
#-qt-mouse-pc \
#-qt-mouse-linuxinput \
#-qt-mouse-qvfb \
#-no-mouse-qnx \
#-qt-mouse-linuxtp \
#-glib \
#-no-gstreamer \
#-no-nas-sound \
#-continue \
#"
#-no-kbd-tty \
#-no-kbd-linuxinput \
#-no-kbd-qvfb \
#-no-kbd-qnx \

QT_LIBINFIX = ""

do_configure_prepend() {
	# Avoid problems with Qt 4.8.0 configure setting QMAKE_LINK from LD (since we want the linker to be g++)
	unset LD
}
# remove -no-script
# add -xmlpatterns
# remove -no-exceptions
# add -exceptions
# remove -no-webkit
# -no-javascript-jit -> -javascript-jit
# -no-scripttools -> -scripttools
# QT_CONFIG_LIBS -> -qt-libpng
# -no-svg -> -svg

QT_CONFIG_FLAGS = " \
-v \
-embedded ${QT_ARCH} \
-release \
-opensource \
-make libs \
-nomake tools \
-nomake examples \
-nomake demos \
-nomake docs \
-nomake translations \
-no-fast \
-no-largefile \
-no-accessibility \
-stl \
-no-sql-sqlite \
-no-sql-db2 \
-no-sql-ibase \
-no-sql-mysql \
-no-sql-oci \
-no-sql-odbc \
-no-sql-psql \
-no-sql-sqlite2 \
-no-sql-tds \
-system-sqlite \
-no-qt3support \
-xmlpatterns \
-exceptions \
-no-declarative \
-no-multimedia \
-no-audio-backend \
-no-phonon \
-no-phonon-backend \
-svg \
-no-webkit \
-javascript-jit \
-scripttools \
-system-zlib \
-no-gif \
-no-libtiff \
-qt-libpng \
-no-libmng \
-qt-libjpeg \
-openssl \
-no-nis \
-no-cups \
-no-iconv \
-no-pch \
-no-dbus \
-system-freetype \
-depths 16,24,32 \
-no-opengl \
-qt-decoration-default \
-no-decoration-styled \
-no-decoration-windows \
-qt-gfx-linuxfb \
-no-gfx-transformed \
-no-gfx-qvfb \
-no-gfx-vnc \
-no-gfx-multiscreen \
-no-gfx-directfb \
-no-gfx-qnx \
-no-mouse-tslib \
-qt-mouse-pc \
-qt-mouse-linuxinput \
-qt-mouse-qvfb \
-no-mouse-qnx \
-qt-mouse-linuxtp \
-glib \
-no-gstreamer \
-no-nas-sound \
-continue \
"
FILES_${PN} += "${libdir}/libQtScript* \
	       ${libdir}/libQtSvg* \
	       ${libdir}/libphonon* \
	       ${libdir}/qtopia/plugins/* \
	       ${prefix}/imports/Qt/labs/*"
