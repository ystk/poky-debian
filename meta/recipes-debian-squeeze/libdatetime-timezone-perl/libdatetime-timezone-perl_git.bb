#
# libdatetime-timezone-perl_1.21.bb
#
DESCRIPTION = "DateTime::TimeZone - Time zone object base class and factory"
#SECTION = "libs"
#LICENSE = "Artistic|GPLv1"
HOMEPAGE = "http://datetime.perl.org/"
DEPENDS = "libclass-singleton-perl-native libparams-validate-perl-native"
RDEPENDS_${PN} = "libclass-singleton-perl libparams-validate-perl \
	perl-module-test-more perl-module-cwd"
#PR = "r1"

#SRC_URI = "http://search.cpan.org/CPAN/authors/id/D/DR/DROLSKY/DateTime-TimeZone-${PV}.tar.gz"
#SRC_URI[md5sum] = "442c2754c061b61e30f04e57954abba3"
#SRC_URI[sha256sum] = "febec17fc7428e5ff897f1772d3e230f2b8e3afbc94558dc2a60b092608c90c6"

#S = "${WORKDIR}/DateTime-TimeZone-${PV}"

inherit cpan

BBCLASSEXTEND="native"

#
# debian-squeeze
#

inherit debian-squeeze
SECTION = "perl"
PR = "r0"

LICENSE = "GPLV2 & Artistic | GPL-1+"
LIC_FILES_CHKSUM = "\
file://LICENSE;md5=a89fc6431f978476bd49e3f7a26a1a1e \
file://debian/copyright;md5=6721dc9c6705e7e51042be1934912235 \
"
FILES_${PN} += "${libdir}"
do_compile_prepend () {
	sed -i -e "s@/vendor_perl@@g" ${S}/Makefile
}	
