#
# libdatetime-locale-perl_0.45.bb
#
DESCRIPTION = "DateTime::Locale - Localization support for DateTime.pm"
#SECTION = "libs"
#LICENSE = "Artistic|GPLv1"
HOMEPAGE = "http://datetime.perl.org/"
DEPENDS = "liblist-moreutils-perl-native libparams-validate-perl-native"
RDEPENDS_${PN} = "liblist-moreutils-perl libparams-validate-perl"
#PR = "r1"

#SRC_URI = "http://search.cpan.org/CPAN/authors/id/D/DR/DROLSKY/DateTime-Locale-${PV}.tar.gz"
#SRC_URI[md5sum] = "8ba6a4b70f8fa7d987529c2e2c708862"
#SRC_URI[sha256sum] = "8aa1b8db0baccc26ed88f8976a228d2cdf4f6ed4e10fc88c1501ecd8f3ccaf9c"

#S = "${WORKDIR}/DateTime-Locale-${PV}"

inherit cpan

BBCLASSEXTEND="native"

#
# debian-squeeze
#

inherit debian-squeeze cpan_build
SECTION = "perl"
PR = "r0"

LICENSE = "GPLv2 & GPL-1+ | Artistic"
LIC_FILES_CHKSUM = "\
file://LICENSE;md5=a89fc6431f978476bd49e3f7a26a1a1e \
file://debian/copyright;md5=810bb963efa552c9fe92b7534d7fba14 \
"

# Overwrite the cpan_build_do_configure to correct the install module directory.

cpan_build_do_configure () {
        if [ ${@is_target(d)} == "yes" ]; then
                # build for target
                . ${STAGING_LIBDIR}/perl/config.sh

                        perl Build.PL --installdirs vendor \
                                --destdir ${D} \
                                --install_path lib="${libdir}/perl/${PERL_PV}" \
                                --install_path arch="${libdir}/perl/${PERL_PV}" \
                                --install_path script=${bindir} \
                                --install_path bin=${bindir} \
                                --install_path bindoc=${mandir}/man1 \
                                --install_path libdoc=${mandir}/man3
        else
                # build for host
                perl Build.PL --installdirs site --destdir ${D}
        fi
}

FILES_${PN} += "${libdir}"
