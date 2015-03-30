#
# debian-squeeze 
#
DESCRIPTION = "File Alteration Monitor" 
SECTION = "admin"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4f853631897f24e52cb37121edc5eaf3"
PR = "r0"
DEPENDS = "portmap"

inherit autotools
inherit debian-squeeze

do_patch_srcpkg() {
	cd ${S}
	tar xzvf ${DEBIAN_SQUEEZE_UNPACKDIR}/${BPN}-2.7.0.tar.gz
	cp -r ${BPN}-2.7.0/* .	

	for patch in $(ls ${S}/debian/patches/*); do
		patch -p1 < ${patch}
	done	
}

