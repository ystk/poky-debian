#
# debian-squeeze
#

DESCRIPTION = "MIT Kerberos"
SECTION = "net"

LICENSE = "GPLv2 & MIT"
LIC_FILES_CHKSUM = "file://${DEBIAN_SQUEEZE_UNPACKDIR}/NOTICE;md5=74519d504e5d29d35c705fc065d7ffa2"

PR = "r0"

inherit debian-squeeze
inherit autotools

S = "${DEBIAN_SQUEEZE_UNPACKDIR}/src"

# debian/patches is no longer needed
do_patch_srcpkg() {
	:
}

# use cross nm instead of native nm
do_patch_append() {
	bb.build.exec_func('replace_nm', d)
}
replace_nm() {
	sed -i "s@^open NM, \"nm @open NM, \"${TARGET_SYS}-nm @" ${S}/util/export-check.pl
}

# The following tests in configure are not supported on cross compiling.
# They are also set in openembedded recipes.
EXTRA_OECONF += " \
krb5_cv_attr_constructor_destructor=yes,yes \
ac_cv_printf_positional=yes \
"

# debian/rules says "we touch each configure and Autoconf-related file
# so that we do not attempt to use Autoconf"
do_configure_prepend() {
	find ${S} -name "configure" -print | xargs touch
	find ${S} \( -name \*hin -o -name \*.h.in -o -name \*.stmp \) -print | xargs touch

	# autotools.bbclass runs autoreconf if the following files exist
	rm -f ${S}/configure.in ${S}/configure.ac
}
