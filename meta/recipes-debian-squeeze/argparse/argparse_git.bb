DESCRIPTION = "optparse-inspired command-line parsing library"
SECTION = "python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=87de148eca7d6d0b2f4f53698e028323"

inherit debian-squeeze
inherit distutils

RDEPENDS ?= "python"

do_install_append() {
	install -d ${D}${prefix}/test/${PN}
	cp -a ${S}/test/* ${D}${prefix}/test/${PN}
}

PACKAGES += "${PN}-test"
FILES_${PN}-test = "${prefix}/test/*"
