#
# debian-squeeze
#

LICENSE = "IPA_FONT"
LIC_FILES_CHKSUM = "file://IPA_Font_License_Agreement_v1.0.txt;md5=6cd3351ba979cf9db1fad644e8221276"

PR = "r0"

inherit debian-squeeze

do_configure() {
	:
}

do_compile() {
	:
}

do_install() {
	install -d ${D}${datadir}/fonts/truetype/takao
	install -m 0644 ${S}/*Gothic.ttf ${D}${datadir}/fonts/truetype/takao
	install -m 0644 ${S}/*Mincho.ttf ${D}${datadir}/fonts/truetype/takao
}

PACKAGES = "${PN}"

FILES_${PN} = "${datadir}/fonts/truetype/takao/*"
