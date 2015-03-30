#
# debian-squeeze
#

DESCRIPTION = "scalable PostScript and OpenType fonts based on URW Fonts"
LICENSE = "GFL & GPLv2"
LIC_FILES_CHKSUM = "file://doc/fonts/tex-gyre/GUST-FONT-LICENSE.txt;md5=5a4927a17baba16dcab8b3a6a3bb0165"
SECTION = "tex"
PR = "r0"

inherit debian-squeeze

do_install() {
	mkdir -p ${D}/usr/share/texmf/fonts/tfm/public
	cp ${S}/fonts/tfm/public/tex-gyre/* ${D}/usr/share/texmf/fonts/tfm/public
	mkdir -p ${D}/usr/share/texmf/fonts/enc/dvips/tex-gyre
	cp ${S}/fonts/enc/dvips/tex-gyre/* ${D}/usr/share/texmf/fonts/enc/dvips/tex-gyre
	mkdir -p ${D}/usr/share/texmf/fonts/opentype/public/tex-gyre
	cp ${S}/fonts/opentype/public/tex-gyre/* ${D}/usr/share/texmf/fonts/opentype/public/tex-gyre
	mkdir -p ${D}/usr/share/texmf/fonts/map/dvips/tex-gyre
	cp ${S}/fonts/map/dvips/tex-gyre/* ${D}/usr/share/texmf/fonts/map/dvips/tex-gyre
	mkdir -p ${D}/usr/share/texmf/fonts/afm/public/tex-gyre
	cp ${S}/fonts/afm/public/tex-gyre/* ${D}/usr/share/texmf/fonts/afm/public/tex-gyre
	mkdir -p ${D}/usr/share/texmf/fonts/type1/public/tex-gyre
	cp ${S}/fonts/type1/public/tex-gyre/* ${D}/usr/share/texmf/fonts/type1/public/tex-gyre
	mkdir -p ${D}/usr/share/texmf/tex/latex/tex-gyre
	cp ${S}/tex/latex/tex-gyre/* ${D}/usr/share/texmf/tex/latex/tex-gyre
	mkdir -p ${D}/usr/share/lintian/overrides/tex-gyre
	mkdir -p ${D}/usr/share/fonts/X11/Type1
	mkdir -p ${D}/usr/share/doc/texmf/fonts/tex-gyre
	cp ${S}/doc/fonts/tex-gyre/* ${D}/usr/share/doc/texmf/fonts/tex-gyre
	mkdir -p ${D}/usr/share/doc/tex-gyre
	cp ${S}/debian/copyright.in ${D}/usr/share/doc/tex-gyre/copyright
	cp ${S}/debian/README.Debian ${D}/usr/share/doc/tex-gyre/README
	tar cvzf ${D}/usr/share/doc/tex-gyre/changelog.gz ${S}/debian/changelog
	mkdir -p ${D}/etc/X11/fonts/Type1
	mkdir -p ${D}/etc/texmf/updmap.d	
	mkdir -p ${D}/etc/defoma/hints
	mkdir -p ${D}/var/lib/tex-common/fontmap-cfg
	cp ${S}/fonts/type1/public/tex-gyre/* ${D}/usr/share/fonts/X11/Type1
	cp ${S}/fonts/afm/public/tex-gyre/* ${D}/usr/share/fonts/X11/Type1
	cp ${S}/doc/fonts/tex-gyre/*.pdf ${D}/usr/share/doc/tex-gyre
}
