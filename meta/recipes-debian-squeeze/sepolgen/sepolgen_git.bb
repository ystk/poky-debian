DESCRIPTION = "A Python module used in SELinux policy generation"
SECTION = "python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

inherit autotools debian-squeeze

DEPENDS += " python"

do_install() {
	install -d ${D}${datadir}/python-support/python-sepolgen/sepolgen
	mkdir -p ${D}${localstatedir}/sepolgen
	install -m 644 ${S}/src/sepolgen/*.py ${D}${datadir}/python-support/python-sepolgen/sepolgen
	install -m 644 ${S}/src/share/perm_map ${D}${localstatedir}/sepolgen 
}

FILES_${PN} = " \
${datadir}/* \
${localstatedir}/* \
"
