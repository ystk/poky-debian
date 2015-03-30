#
# debian-squeeze 
#

DESCRIPTION = "Japanese OpenType font set, IPAfont"
SECTION = "fonts"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://IPA_Font_License_Agreement_v1.0.txt;md5=6cd3351ba979cf9db1fad644e8221276"
PR = "r0"

TTFs = " ipag.ttf ipagp.ttf ipam.ttf ipamp.ttf"

inherit debian-squeeze

do_install() {
	cd ${S}
	for file in ${TTFs}; do
		install -m 644 -D ${S}/${file} ${D}${datadir}/fonts/opentype/ipafont/${file}
	done
	install -m 644 -D ./debian/copyright ${D}${datadir}/doc/${BPN}/copyright
	tar cvzf ${D}${datadir}/doc/${BPN}/changelog.Debian.gz ./debian/changelog
	
}


do_install_append() {

	mkdir -p ${D}${datadir}/fonts/truetype
        ln -sf ${D}${datadir}/fonts/opentype/ipafont/ipag.ttf ${D}${datadir}/fonts/truetype/ttf-japanese-gothic.ttf
        ln -sf ${D}${datadir}/fonts/opentype/ipafont/ipam.ttf ${D}${datadir}/fonts/truetype/ttf-japanese-mincho.ttf
}

