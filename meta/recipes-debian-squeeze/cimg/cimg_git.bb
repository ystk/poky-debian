#
# debian-squeeze 
#

DESCRIPTION = "powerful image processing library"
SECTION = "math" 
LICENSE = "CeCILLv2.0"
LIC_FILES_CHKSUM = "file://README.txt;md5=b4d82afb8acdf583e8e0508b577c4cc9"
PR = "r0"

inherit debian-squeeze
inherit autotools 

DEPENDS = "libx11 libxrandr"

do_install() {
	install -d ${D}${includedir}
	install -m 0644 CImg.h ${D}${includedir}
	install -d ${D}${includedir}/plugins
	for file in $(ls ${S}/plugins/*); do
		install -m 0644 ${file} ${D}${includedir}/plugins
	done
	install -d ${D}${docdir}/cimg-dev
	install -m 0644 debian/copyright ${D}${docdir}/cimg-dev
	tar cvzf ${D}${docdir}/cimg-dev/changelog.gz resources/debian/changelog
	tar cvzf ${D}${docdir}/cimg-dev/README.txt.gz README.txt
}
