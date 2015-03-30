#
# debian-squeeze 
#
DESCRIPTION = "JavaScript library for dynamic web applications"
SECTION = "web" 
LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://src/ajax.js;beginline=2;endline=25;md5=c4d308d36b2b6716aaebc6467f54dc43 \
		    file://src/css.js;beginline=2;endline=25;md5=9b1210cdf472aa8f09be1efbfd47978d"
PR = "r0"

inherit debian-squeeze
do_install() {
	install -d ${D}${datadir}/javascript/jquery
	install -d ${D}${docdir}/libjs-jquery
	install -m 0644 debian/README.Debian ${D}${docdir}/libjs-jquery
	install -m 0644 debian/copyright ${D}${docdir}/libjs-jquery
	tar cvzf ${D}${docdir}/libjs-jquery/changelog.gz debian/changelog
	install -m 0644 dist/jquery.js ${D}${datadir}/javascript/jquery
}

PACKAGES = "${PN} ${PN}-doc ${PN}-java"

ALLOW_EMPTY_${PN} = "1"
RDEPENDS_${PN} += "${PN}-doc ${PN}-java"

FILES_${PN}-java = "${datadir}/javascript/jquery/*"
RPROVIDES_${PN}-java = "${PN}-java"
