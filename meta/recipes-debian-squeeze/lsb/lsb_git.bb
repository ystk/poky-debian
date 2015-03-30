#
# debian-squeeze
#
DESCRIPTION = "Linux Standard Base 3.2 core support package"
SECTION = "misc"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://lsb_release.1;md5=a49fc4cc872f0c469a3924e19964858e"

inherit debian-squeeze
inherit autotools
DEPENDS += ""

PACKAGES += "${PN}-base"
ALLOW_EMPTY_${PN} = "1"
FILES_${PN}-base += "/lib/lsb/*"
RDEPENDS += "${PN}-base"
RPROVIDES_${PN}-base = "${PN}-base"

SRC_URI += "file://init-functions"

do_compile() {
}

do_install() {
	install -d ${D}/lib/lsb
	install -m 755 ${WORKDIR}/init-functions ${D}/lib/lsb/
}
