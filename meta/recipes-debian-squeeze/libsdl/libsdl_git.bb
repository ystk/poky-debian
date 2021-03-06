#
# libsdl_1.2.11.bb
#

SUMMARY = "Simple DirectMedia Layer"
DESCRIPTION = "Simple DirectMedia Layer is a cross-platform multimedia \
library designed to provide low level access to audio, keyboard, mouse, \
joystick, 3D hardware via OpenGL, and 2D video framebuffer."
HOMEPAGE = "http://www.libsdl.org"
BUGTRACKER = "http://bugzilla.libsdl.org/"

SECTION = "libs"

LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=27818cd7fd83877a8e3ef82b82798ef4"

DEPENDS = " \
${@base_contains('DISTRO_FEATURES', 'opengl', 'virtual/libgl', '', d)} \
${@base_contains('DISTRO_FEATURES', 'x11', 'virtual/libx11 libxext libxrandr libxrender', '', d)} \
tslib \
"
DEPENDS_virtclass-nativesdk = \
"${@base_contains('DISTRO_FEATURES', 'x11', 'libx11-nativesdk libxrandr-nativesdk libxrender-nativesdk libxext-nativesdk', '', d)}"

#PR = "r8"

#SRC_URI = "http://www.libsdl.org/release/SDL-${PV}.tar.gz \
#	   file://acinclude.m4 \
#	   file://configure_tweak.patch \
#	   file://kernel-asm-page.patch "

S = "${WORKDIR}/SDL-${PV}"

#SRC_URI[md5sum] = "418b42956b7cd103bfab1b9077ccc149"
#SRC_URI[sha256sum] = "6985823287b224b57390b1c1b6cbc54cc9a7d7757fbf9934ed20754b4cd23730"

inherit autotools binconfig pkgconfig

EXTRA_OECONF = "--disable-static --disable-debug --disable-cdrom --enable-threads --enable-timers --enable-endian \
                --enable-file --disable-oss --disable-alsa --disable-esd --disable-arts \
                --disable-diskaudio --disable-nas --disable-esd-shared --disable-esdtest \
                --disable-mintaudio --disable-nasm --disable-video-dga \
                --disable-video-fbcon --disable-video-directfb --disable-video-ps2gs \
                --disable-video-xbios --disable-video-gem --disable-video-dummy \
                --enable-input-events --enable-pthreads \
		${@base_contains('DISTRO_FEATURES', 'opengl', '--enable-video-opengl', '--disable-video-opengl', d)} \
		${@base_contains('DISTRO_FEATURES', 'x11', '--enable-video-x11', '--disable-video-x11', d)} \
		--disable-video-svga \
                --disable-video-picogui --disable-video-qtopia --enable-dlopen"

PARALLEL_MAKE = ""

#do_configure_prepend() {
#	cp ${WORKDIR}/acinclude.m4 ${S}/acinclude.m4
#}

BBCLASSEXTEND = "nativesdk"

#
# debian-squeeze
#

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "libsdl1.2"

PR = "r0"

SRC_URI = " \
file://acinclude.m4 \
file://configure_tweak.patch \
"

# don't use PlayStation 3 Cell driver on powerpc (see debian/rules)
EXTRA_OECONF += "--enable-video-ps3=no --disable-altivec"

do_configure_prepend() {
	cp ${WORKDIR}/acinclude.m4 ${S}/acinclude.m4

	# Remove to libtool-related .m4 files to avoid libtool version mismatch error
	rm ${S}/acinclude/ltversion.m4
	rm ${S}/acinclude/libtool.m4
}
