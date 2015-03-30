#
# debian-squeeze
#

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

PR = "r0"

inherit debian-squeeze
inherit autotools gettext
inherit native

# intltool: required to proper Makefile for system-config-audit
DEPENDS = " \
krb5 \
tcp-wrappers \
perl-native \
libxml-parser-perl-native \
openldap-native \
python-native \
swig1.3-native \
intltool-native \
"

SRC_URI += " \
file://fix-src.patch \
file://fix-make.patch \
file://fix_makebin.patch \
file://add_missing_headers.diff \
"

EXTRA_OEMAKE = ""

do_configure_prepend() {
	export INTLTOOL_PERL="${STAGING_BINDIR_NATIVE}/perl-native/perl"
}

do_install_prepend() {
        sed -i -e 's#. /etc/init.d/functions#if test -f /etc/init.d/fucntions; then\n. /etc/init.d/functions\nfi\n. /lib/lsb/init-functions#' ${S}/init.d/auditd.init
}
