#
# debian-squeeze
#
DESCRIPTION = "Perl extensions for IPv6"
SECTION = "perl"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;md5=cfb4f6bcee0aa76f27c8b641ba40c4cc"
PR = "r0"

inherit debian-squeeze autotools cpan

S = "${WORKDIR}/${PN}-${PV}/Socket6-0.23"

do_unpack_append() {
	bb.build.exec_func('extract_archive', d)
}

extract_archive() {
	cd ${WORKDIR}/${PN}-${PV}
	tar xzf Socket6-0.23.tar.gz
}

do_configure_prepend() {
	sed -i -e "s:./configure:./configure --host=${HOST_SYS}:" Makefile.PL
}

FILES_${PN}-dbg += "${libdir}/perl/vendor_perl/5.10.1-17/auto/Socket6/.debug"
