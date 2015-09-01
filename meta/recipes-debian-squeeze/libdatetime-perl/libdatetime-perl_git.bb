#
# debian-squeeze
#
SUMMARY = "Module for manipulating dates, times and timestamps"

DESCRIPTION = "DateTime is a Perl module which aims to provide a complete, \
correct, and easy to use date/time object implementation. It provides an \
easy way to manipulate dates and times, including date calculations (even \
addition and subtraction) and provides convenient methods for extracting \
or modifying portions of any date or time."

inherit debian-squeeze cpan_build
SECTION = "perl"
PR = "r0"
LICENSE = "Artistic-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c01366393c3618381962db900e070db5"

RDEPENDS += "libdatetime-timezone-perl libdatetime-locale-perl libparams-validate-perl"

# Overwrite the cpan_build_do_configure to correct the install module directory.
cpan_build_do_configure () {
        if [ ${@is_target(d)} == "yes" ]; then
                # build for target
                . ${STAGING_LIBDIR}/perl/config.sh

                        perl Build.PL --installdirs vendor \
                                --destdir ${D} \   
                                --install_path lib="${datadir}/perl/${PERL_PV}" \
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
