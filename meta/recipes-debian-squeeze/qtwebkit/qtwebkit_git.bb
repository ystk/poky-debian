inherit qt4e
require qtwebkit.inc

PR = "${INC_PR}.0"

#SRC_URI = " \
#        http://pkgs.fedoraproject.org/repo/pkgs/qtwebkit/qtwebkit-2.2.0-rc1.tar.xz/5c1581052ad5bb7aed07a1798a340061/qtwebkit-2.2.0-rc1.tar.xz \
#        file://0001-Qt-Fix-build-with-QT_LIBINFIX.patch \
#"

#SRC_URI[md5sum] = "5c1581052ad5bb7aed07a1798a340061"
#SRC_URI[sha256sum] = "fd33bdf565dde335bf0cd686c2513c60305f95a1e4b9a82f6b305cb672985a0b"

#S = "${WORKDIR}/webkit-qtwebkit"

#
# debian-squeeze
#

inherit debian-squeeze

SECTION = "libs"
PR = "r0"

SRCREV = "ashitaka-update-master"
export QMAKESPEC = "${STAGING_DATADIR}/${QT_DIR_NAME}/mkspecs/qws/${TARGET_OS}-${TARGET_ARCH}-g++"
EXTRA_QMAKEVARS_PRE = "${QMAKEARG}"

DEPENDS += " gst-plugins-base libpng jpeg"

do_configure_prepend() {
	cd Source

        # disable netscape plugin api
        # enable mathml
        # enable data transfer item
        sed -i 's:ENABLE_NETSCAPE_PLUGIN_API=1:ENABLE_NETSCAPE_PLUGIN_API=0 ENABLE_MATHML=1 ENABLE_DATA_TRANSFER_ITEMS=1:' WebCore/features.pri

        sed -i 's:ENABLE_INPUT_SPEECH=0:ENABLE_INPUT_SPEECH=1:' WebCore/features.pri
        sed -i 's:ENABLE_MEDIA_STATISTICS=0:ENABLE_MEDIA_STATISTICS=1:' WebCore/features.pri
        sed -i 's:ENABLE_TOUCH_ICON_LOADING=0:ENABLE_TOUCH_ICON_LOADING=1:' WebCore/features.pri
        sed -i 's:ENABLE_ANIMATION_API=0:ENABLE_ANIMATION_API=1:' WebCore/features.pri

        # enable web timing
        sed -i 's:ENABLE_WEB_TIMING=0:ENABLE_WEB_TIMING=1:' WebCore/features.pri

        # enable xhtmlmp
        sed -i '0,/ENABLE_XHTMLMP=0/s/ENABLE_XHTMLMP=0/ENABLE_XHTMLMP=1/' WebCore/features.pri

        # enable jsc jit
        sed -i '0,/^$/s/^$/JAVASCRIPTCORE_JIT=yes/' common.pri

        # enable xslt
        # error: qabstractmessagehandler.h: No such file or directory when ENABLE_XSLT=1 --> enable xmlpatterns and exceptions in qt4-embedded
        sed -i 's:ENABLE_XSLT=0:ENABLE_XSLT=1:' WebCore/features.pri

        echo 'SOURCES += \' >> WebCore/WebCore.pro
        echo 'platform/image-encoders/JPEGImageEncoder.cpp \' >> WebCore/WebCore.pro
        echo 'platform/image-encoders/PNGImageEncoder.cpp \' >> WebCore/WebCore.pro
        echo 'platform/image-decoders/qt/ImageFrameQt.cpp' >> WebCore/WebCore.pro
        echo '' >> WebCore/WebCore.pro
        echo 'HEADERS += \' >> WebCore/WebCore.pro
        echo 'platform/image-encoders/JPEGImageEncoder.h \' >> WebCore/WebCore.pro
        echo 'platform/image-encoders/PNGImageEncoder.h \' >> WebCore/WebCore.pro
        echo 'platform/image-decoders/ImageDecoder.h' >> WebCore/WebCore.pro
}
do_compile_prepend() {
	cd Source
}
do_install() {
	cd Source
	oe_runmake 'INSTALL_ROOT=${D}' install
	# fix pkgconfig and prl files
	sed -i -e s#-L${S}/lib##g \
        	-e s#-L${STAGING_LIBDIR}##g \
		-e 's#STAGING_LIBDIR}#libdir}'#g \
        	-e s#-L${libdir}##g \
		-e s#'$(OE_QMAKE_LIBS_X11)'#"${OE_QMAKE_LIBS_X11}"#g \
       		-e s#" -Wl,-rpath-link,${S}/lib"##g \
		-e s#" -Wl,-rpath-link,${libdir}"##g \
		-e 's#I/usr/include#Iincludedir}#g' \
		-e 's#Iin#I${in#g' \
	        ${D}${PRL_FILE} ${D}${PKGCONFIG_FILE}
	 # fix pkgconfig files
	 sed -i -e 's:^prefix=.*:prefix=${prefix}:' \
        	-e 's:-L$(OE_QMAKE_LIBDIR_QT)::g' \
	        -e '/^Cflags:/s#{includedir}#{includedir} -I${includedir}/${QT_DIR_NAME} -DQT_SHARED#' \
        	${D}${PKGCONFIG_FILE}
}

