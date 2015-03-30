#
# HIDDEN/recipes-extended/minicom/minicom_2.4.bb
#
SUMMARY = "Text-based modem control and terminal emulation program"
DESCRIPTION = "Minicom is a text-based modem control and terminal emulation program for Unix-like operating systems"
SECTION = "console/network"
DEPENDS = "ncurses"
LICENSE = "GPLv2+"

#SRC_URI = "http://alioth.debian.org/frs/download.php/3195/minicom-${PV}.tar.gz \
#	file://rename-conflicting-functions.patch \
#	"
#	file://gcc4-scope.patch

#SRC_URI[md5sum] = "700976a3c2dcc8bbd50ab9bb1c08837b"
#SRC_URI[sha256sum] = "6b7af240b073ba847b091fd75aed4bf720eb94d30e188d23bb098d016bf40a48"

inherit autotools gettext

do_install() {
	for d in doc extras man lib src; do make -C $d DESTDIR=${D} install; done
}

#
# debian-squeeze
#

inherit debian-squeeze

LIC_FILES_CHKSUM = "file://COPYING;md5=420477abc567404debca0a2a1cb6b645"

do_patch_srcpkg() {
	cd ${S}
	for patch in $( ls ${S}/debian/patches/* ) ; do
		patch -p1 < ${patch}
	done
                

}
