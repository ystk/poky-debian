#
# HIDDEN/recipes-extended/newt/libnewt_0.52.13.bb
#

SUMMARY = "A library for text mode user interfaces"

DESCRIPTION = "Newt is a programming library for color text mode, widget based user \
interfaces.  Newt can be used to add stacked windows, entry widgets, \
checkboxes, radio buttons, labels, plain text fields, scrollbars, \
etc., to text mode user interfaces.  This package also contains the \
shared library needed by programs built with newt, as well as a \
/usr/bin/dialog replacement called whiptail.  Newt is based on the \
slang library."

HOMEPAGE = "https://fedorahosted.org/newt/"
SECTION = "libs"

LICENSE = "LGPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=55ca817ccb7d5b5b66355690e9abc605"

# slang needs to be >= 2.2
DEPENDS = "slang popt"

#PR = "r0"

#SRCREV = "c3c7be75f6ef1adfc2be5f99c1e8ef3f0ab58c38"

#SRC_URI = "https://fedorahosted.org/releases/n/e/newt/newt-${PV}.tar.gz \
#           file://remove_slang_include.patch \
#           file://fix_SHAREDDIR.patch"

#SRC_URI[md5sum] = "77de05b3f58540152a4ae32a1a64e5d0"
#SRC_URI[sha256sum] = "01c254b00a3d27e22f13240b6db74df04e721ccec4d1d4fc7b45fee009efc05f"

S = "${WORKDIR}/newt-${PV}"

EXTRA_OECONF = "--without-python --without-tcl"

inherit autotools

PACKAGES_prepend = "whiptail "

do_configure_prepend() {
    sh autogen.sh
}

FILES_whiptail = "${bindir}/whiptail"

#
# debian-squeeze
#

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "newt"

PR = "r0"

RDEPENDS += "whiptail"
RPROVIDES_whiptail = "whiptail"

# snackmodule, tcl, slang should be excluded because
# this recipe doesn't use python and tcl (see EXTRA_OECONF) by default
SRC_URI += "file://disable-snackmodule-tcl-slang.patch"

ORIG_VERSION = "0.52.11"
S = "${DEBIAN_SQUEEZE_UNPACKDIR}/${DEBIAN_SQUEEZE_SRCPKG_NAME}-${ORIG_VERSION}"

do_unpack_srcpkg_append() {
	cd ${DEBIAN_SQUEEZE_UNPACKDIR}
	tar xzf ${DEBIAN_SQUEEZE_SRCPKG_NAME}-${ORIG_VERSION}.tar.gz

	# instroot is used instead of DESTDIR
	sed -i "1i instroot = ${D}" ${S}/Makefile.in
}

do_patch_srcpkg() {
	cd ${S}
	for patch in $(find ${DEBIAN_SQUEEZE_UNPACKDIR}/debian/patches/*.patch | sort); do
		patch -p1 < $patch
	done
}


