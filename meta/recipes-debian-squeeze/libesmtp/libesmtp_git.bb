#
# debian-squeeze
#
DESCRIPTION = "LibESMTP SMTP client library"
HOMEPAGE = "http://www.stafford.uklinux.net/libesmtp/"
SECTION = "libs"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit debian-squeeze autotools

DEPENDS += "openssl libtool"

do_patch_srcpkg_prepend() {
	tar -xzf ${S}/upstream/tarballs/libesmtp-1.0.4.tar.gz -C ${S}
	cp -a ${S}/libesmtp-1.0.4/* ${S}
	rm -rf ${S}/libesmtp-1.0.4
	
	ls ${S}/debian/patches > ${WORKDIR}/series
	mv series ${S}/debian/patches
}

FILES_${PN} += "${libdir}"
FILES_${PN}-dbg += "${libdir}/esmtp-plugins/.debug"
