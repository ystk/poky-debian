#
# liblist-moreutils-perl_0.22.bb
#

DESCRIPTION = "List::MoreUtils - Provide the stuff missing in List::Util"
#SECTION = "libs"
#LICENSE = "Artistic|GPL"
HOMEPAGE = "http://datetime.perl.org/"
PR = "r0"

SRC_URI = "http://search.cpan.org/CPAN/authors/id/V/VP/VPARSEVAL/List-MoreUtils-${PV}.tar.gz"
SRC_URI[md5sum] = "3a6ec506f40662ab1296c48c5eb72016"
SRC_URI[sha256sum] = "b4948b26851d9d9ac611eb487ecb92815dc3c5ee64e414bc67211b48590f62b7"

S = "${WORKDIR}/List-MoreUtils-${PV}"

inherit cpan

BBCLASSEXTEND="native"

#
# debian-squeeze
#

inherit debian-squeeze
SECTION = "perl"
LICENSE = "Artistic | GPL-1+"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=9df681092e0274b48d53583f82b3461c"

FILES_${PN} += "${libdir}"

do_compile_prepend () {
	sed -i -e "s@/vendor_perl@@g" ${S}/Makefile
}
