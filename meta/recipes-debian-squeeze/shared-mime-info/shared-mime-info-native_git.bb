require shared-mime-info.inc

DESCRIPTION = "FreeDesktop.org shared MIME database and spec"
SECTION = "misc"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"
S = "${WORKDIR}/shared-mime-info-${PV}"

#
# debian-squeeze
#
inherit debian-squeeze
inherit autotools native
DEPENDS += "glib-2.0-native libxml2-native intltool-native"
do_compile_prepend() {
	echo ".pc.debian/060_pdf_priority.patch/freedesktop.org.xml.in" >> po/POTFILES.skip
	echo ".pc.debian/061_tex-matlab.patch/freedesktop.org.xml.in" >> po/POTFILES.skip
	echo ".pc.debian/150_mps-mime.patch/freedesktop.org.xml.in" >> po/POTFILES.skip
	echo ".pc.debian/151_webm.patch/freedesktop.org.xml.in" >> po/POTFILES.skip
	echo ".pc.debian/152_qt-designer.patch/freedesktop.org.xml.in" >> po/POTFILES.skip
	echo ".pc.debian/153_mpegts.patch/freedesktop.org.xml.in" >> po/POTFILES.skip
	echo ".pc.debian/154_ogg-mkv-webm-media-icons.patch/freedesktop.org.xml.in" >> po/POTFILES.skip
	echo ".pc.debian/155_audio-webm.patch/freedesktop.org.xml.in" >> po/POTFILES.skip
	echo ".pc.debian/156_mkv-webm-magic.patch/freedesktop.org.xml.in" >> po/POTFILES.skip
	echo ".pc.debian/158_no_directory_alias.patch/freedesktop.org.xml.in" >> po/POTFILES.skip
	
}
