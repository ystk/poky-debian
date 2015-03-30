#
# debian-squeeze
#
DESCRIPTION = "system installation report"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://report-hw.1;md5=860a2ba06dbab1432fe7253ef0bb1a86 \
		    file://install-report.template;md5=273ff84a9a713f18cfc88b6e955693ea"
SECTION = "debian-installer"
PR = "r0"

inherit autotools
inherit debian-squeeze

do_install() {
	mkdir -p ${D}/usr/share
	mkdir -p ${D}/usr/bin
	cp ${S}/install-report.template ${D}/usr/share
	cp -r ${S}/bug/installation-report ${D}/usr/share
	cp -r ${S}/bug/installation-reports ${D}/usr/share
	cp ${S}/report-hw ${D}/usr/bin
}
