#
# debian-squeeze
#

SUMMARY = "ack is a grep-like tool tailored to working with large trees of \
	   source code"

inherit debian-squeeze cpan
RDEPENDS += "libfile-next-perl perl-module-term-ansicolor"
SECTION = "utils"
PR = "r0"
LICENSE = "Artistic-2.0"
LIC_FILES_CHKSUM = "file://README;md5=6fa3490365fac57fe43767fe4da94cfb"

# Overwrite do_configure() to avoid finding the Makefile in .pc.debian directory.
do_configure() {
	export PERL5LIB="${PERL_ARCHLIB}"
	yes '' | perl ${EXTRA_PERLFLAGS} Makefile.PL ${EXTRA_CPANFLAGS}
	if [ "${BUILD_SYS}" != "${HOST_SYS}" ]; then
		. ${STAGING_LIBDIR}${PERL_OWN_DIR}/perl/config.sh
		# Use find since there can be a Makefile generated for each Makefile.PL
		for f in `find -name Makefile.PL | grep -v ".pc.debian"`; do
			f2=`echo $f | sed -e 's/.PL//'`
			sed -i -e "s:\(PERL_ARCHLIB = \).*:\1${PERL_ARCHLIB}:" \
				-e 's/perl.real/perl/' \
				$f2
		done
	fi
}

do_compile_prepend () {
	export PERL5LIB="${PERL_ARCHLIB}"
}

do_install_append () {
	mv ${D}${bindir}/ack-base ${D}${bindir}/ack-grep
}

DEPENDS += "libfile-next-perl-native"
