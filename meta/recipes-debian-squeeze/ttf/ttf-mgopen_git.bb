#
# debian-squeeze 
#

DESCRIPTION = "Magenta MgOpen TrueType fonts" 
SECTION = "fonts"
LICENSE = "MgOpen"
LIC_FILES_CHKSUM = "file://TTFs/MgOpenCanonicaRegular.ttf;md5=23bf81b09c9bd224f17586a5bc0071f2"
PR = "r0"

inherit debian-squeeze
DOC = " README.Debian copyright"

do_install() {
	cd ${S}
	for file in $(ls ${S}/TTFs); do
		install -m 644 -D ./TTFs/${file} ${D}${datadir}/fonts/truetype/${BPN}/${file}
	done
	for file in ${DOC}; do
		install -m 644 -D ./debian/${file} ${D}${datadir}/doc/${BPN}/${file}
	done
	tar cvzf ${D}${datadir}/doc/${BPN}/changelog.Debian.gz ./debian/changelog
}

FILES_${PN} = "${datadir}/fonts/truetype/${BPN}/*"
