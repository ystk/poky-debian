#
# debian-squeeze
#

LICENSE = "Hanazono Font License"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=8d04c57586115509c58ae76dd556a0f8"

PR = "r0"

inherit debian-squeeze

do_configure() {
	:
}

do_compile() {
	:
}

do_install() {
	install -d ${D}${datadir}/fonts/truetype/hanazono
	install -m 0644 ${S}/hanazono.ttf ${D}${datadir}/fonts/truetype/hanazono
}

PACKAGES = "${PN}"

FILES_${PN} = "${datadir}/fonts/truetype/hanazono/*"
