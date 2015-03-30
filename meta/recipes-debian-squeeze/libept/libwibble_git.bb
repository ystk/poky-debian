#
# debian-squeeze
#
DESCRIPTION = "Library of various useful C++ code"
LICENSE = "GPL"
SECTION = "lib/devel"
LIC_FILES_CHKSUM = "file://README;md5=8bd85aac78d0461652c58e718e45bac5"
PR = "r0"
inherit debian-squeeze autotools
DEPENDS += "doxygen-native"
EXTRA_OECONF += "--disable-docs \
		--enable-shared"
export PERL_EXECUTABLE="${STAGING_BINDIR_NATIVE}perl-native/perl"
do_install_append() {
	install -m 0644 ${S}/wibble/test.h ${D}/usr/include/wibble
}
