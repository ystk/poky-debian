#
# debian-squeeze
#
DESCRIPTION = "HA cluster resource manager"
HOMEPAGE = "http://clusterlabs.org/"
SECTION = "admin"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

inherit debian-squeeze autotools

DEPENDS += "net-snmp glib-2.0 libxml2 bzip2 zlib libtool gnutls openais \
	python libpam ncurses corosync heartbeat cluster-glue libxslt libesmtp"

LDFLAGS += "-lncurses -L${STAGING_LIBDIR}"

do_configure_prepend() {
	sed -i -e "s@-Werror\"@\"@g" configure.ac
	sed -i -e "s@-I$"{prefix}"@-I${STAGING_EXECPREFIXDIR}@g" configure.ac
	sed -i -e "61s@^@libpengine_la_LIBADD = \$(top_builddir)/lib/pengine/libpe_status.la@" pengine/Makefile.am
	sed -i -e "/help2man/d" pengine/Makefile.am
	sed -i -e "/help2man/d" tools/Makefile.am
	sed -i -e "s@help2man@help2man_@" configure.ac
	sed -i -e "s@$"CC" $"CFLAGS"@gcc $"CFLAGS"@g" configure.ac
}

FILES_${PN} += "${libdir}"
FILES_${PN}-dbg += "${libdir}/heartbeat/.debug/ ${libdir}/heartbeat/plugins/RAExec/.debug/"
INSANE_SKIP_${PN} = "dev-so"
