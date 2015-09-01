#
# libparams-validate-perl_0.95.bb 
#
DESCRIPTION = "Params::Validate - Validate method/function parameters"
#SECTION = "libs"
#LICENSE = "Artistic|GPL"
PR = "r0"

#SRC_URI = "http://search.cpan.org/CPAN/authors/id/D/DR/DROLSKY/Params-Validate-${PV}.tar.gz"

#S = "${WORKDIR}/Params-Validate-${PV}"

inherit cpan_build

FILES_${PN} = "${PERLLIBDIRS}/auto/Params/Validate/* \
                ${PERLLIBDIRS}/Params \
                ${PERLLIBDIRS}/Attribute"

BBCLASSEXTEND="native"

SRC_URI[md5sum] = "f544f12357ae4ba44044cd8cb2b83a9f"
SRC_URI[sha256sum] = "0739ccd0e7c7c0ffc0e2ad797d78e42c050e6297ab58d56f90a0e4de623f8942"

#
# debian-squeeze
#

inherit debian-squeeze cpan

SECTION = "perl"

LICENSE = "GPLv2 & Artistic | GPL-1+"

LIC_FILES_CHKSUM = "\
file://debian/copyright;md5=17d44dc646897117ef1660fce5f448ef \
file://LICENSE;md5=a89fc6431f978476bd49e3f7a26a1a1e \
"
FILES_${PN} += "${libdir}"

do_compile_prepend () {
	sed -i -e "s@/vendor_perl@@g" ${S}/Makefile
}
