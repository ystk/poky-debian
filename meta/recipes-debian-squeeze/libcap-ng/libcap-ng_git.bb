#
# debian-squeeze
#
DESCRIPTION = "An alternate posix capabilities library"
HOMEPAGE = "http://people.redhat.com/sgrubb/libcap-ng"
SECTION = "libs"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit debian-squeeze autotools

DEPENDS += "attr python"
SRC_URI += "file://xattr_name_cap.patch"

do_configure_prepend() {
	sed -i -e "s@-I/usr/include/\$(PYLIBVER)@-I${STAGING_INCDIR}/\$(PYLIBVER)@g" \
			bindings/python/Makefile.am
	sed -i -e "s@-I/usr/include/\$(PYLIBVER)@-I${STAGING_INCDIR}/\$(PYLIBVER)@g" \
			bindings/python/Makefile.in
}

FILES_${PN} += "${libdir}"
