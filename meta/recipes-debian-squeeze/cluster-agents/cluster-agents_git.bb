#
# debian-squeeze
#
DESCRIPTION = "The reusable cluster components for Linux HA"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
SECTION = "admin"
PR = "r0"

inherit autotools
inherit debian-squeeze
do_configure() {
	./autogen.sh
	oe_runconf --disable-fatal-warnings
}

# Use native compiler to extract define from header, because
# it requires to excute binary files while configuring
# so apply fix_extract_header_define.patch
SRC_URI = " \
file://fix_make.patch \
file://fix_extract_header_define.patch \
"
DEPENDS += "glib-2.0 libnet python cluster-glue"

# Export flag so native compiler can find header for extraction
do_configure_prepend() {
	export CFLAGS="-I${STAGING_INCDIR}"
	sed -i -e "s:\\\\1:\\\$1:" ${S}/ldirectord/ldirectord.in
}

# Install ldirectord.cf, because daemon requires it
do_install_append() {
	install -d ${D}${sysconfdir}/ha.d
	install -m 0644 ${S}/ldirectord/ldirectord.cf ${D}${sysconfdir}/ha.d
}

FILES_${PN} += "${datadir} ${libdir}"
FILES_${PN}-dbg += " \
${libdir}/heartbeat/.debug \
${libdir}/ocf/resource.d/heartbeat/.debug \
"

# Runtime dependencies of ldirecotrd
RDEPENDS += " \
libsocket6-perl \
perl-module-pod-usage \
perl-module-sys-hostname \
perl-module-sys-syslog \
perl-module-pod-text \
perl-module-pod-simple \
perl-module-pod-escapes \
perl-module-pod-simple-linksection \
perl-module-pod-simple-blackbox \
perl-module-universal \
perl-module-pod-parser \
perl-module-pod-inputobjects \
perl-module-sys \
perl-module-cpan \
libwww-perl \
perl-module-net-ping \
perl-module-digest-md5 \
perl-module-digest \
perl-module-net-smtp \
perl-module-net-cmd \
perl-module-net-config \
libmailtools-perl \
liburi-perl \
perl-module-time-local \
perl-module-config-git \
perl-module-errno \
perl-module-file-spec \
perl-module-getopt-long \
perl-module-integer \
perl-module-io-seekable \
perl-module-io-select \
perl-module-io-socket \
perl-module-io-socket-inet \
perl-module-io-socket-unix \
perl-module-pod-select \
perl-module-socket \
ipvsadm \
"
