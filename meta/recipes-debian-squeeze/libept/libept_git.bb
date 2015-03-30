#
# debian-squeeze
#
DESCRIPTION = "High-level library for managing Debian package information"
LICENSE = "GPL"
SECTION = "lib/devel"
LIC_FILES_CHKSUM = "file://COPYING;md5=2d5025d4aa3495befef8f17206a5b0a1"
PR = "r0"
inherit debian-squeeze autotools cmake
DEPENDS += "libwibble xapian-core apt tagcoll2"
do_compile_prepend() {
	sed -i 's:PERL_EXECUTABLE-NOTFOUND:${STAGING_BINDIR_NATIVE}/perl-native/perl:' $(find -name "*.txt")
}
do_compile() {
	oe_runmake ept
	oe_runmake ept-static
}
do_install() {
	install -d ${D}/${libdir}/pkgconfig
	install -m 0755 ${S}/ept/libept.so* ${D}/${libdir}
	install -m 0644 ${S}/ept/libept.a ${D}/${libdir}
	install -d ${D}/${includedir}/ept/apt
	install -d ${D}/${includedir}/ept/axi
	install -d ${D}/${includedir}/ept/popcon/maint
	install -d ${D}/${includedir}/ept/debtags/maint
	install -m 0644 ${S}/ept/axi/*.h ${D}/${includedir}/ept/axi
	install -m 0644 ${S}/ept/apt/*.h ${D}/${includedir}/ept/axi
	install -m 0644 ${S}/ept/popcon/*.h ${D}/${includedir}/ept/popcon
	install -m 0644 ${S}/ept/popcon/maint/*.h ${D}/${includedir}/ept/popcon/maint
	install -m 0644 ${S}/ept/debtags/*.h ${D}/${includedir}/ept/debtags
	install -m 0644 ${S}/ept/debtags/maint/*.h ${D}/${includedir}/ept/debtags/maint
	install -m 0644 ${S}/ept/*.h ${D}/${includedir}/ept
	install -m 0644 ${S}/ept/debtags/debtags.tcc ${D}/${includedir}/ept/debtags
	install -m 0644 ${S}/ept/debtags/maint/sourcedir.tcc ${D}/${includedir}/ept/debtags/maint
	install -m 0644 ${S}/ept/libept.pc ${D}/${libdir}/pkgconfig
	install -d ${D}/${datadir}/aclocal
	install -m 0644 ${S}/ept/libept.m4 ${D}/${datadir}/aclocal
}
