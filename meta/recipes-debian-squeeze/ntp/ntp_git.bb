#
# copied from OpenEmbedded
#

require ntp.inc

PR = "r0"

#SRC_URI = "http://www.eecis.udel.edu/~ntp/ntp_spool/ntp4/ntp-4.2/${P}.tar.gz \
#	file://tickadj.c.patch;patch=1 \
#    file://ntp-4.2.4_p6-nano.patch;patch=1 \
#	file://ntpd \
#	file://ntp.conf \
#	file://ntpdate"

# ntp originally includes tickadj. It's split off for inclusion in small firmware images on platforms
# with wonky clocks (e.g. OpenSlug)
RDEPENDS_${PN} = "${PN}-tickadj"
FILES_${PN}-bin = "${bindir}/ntp-wait ${bindir}/ntpdc ${bindir}/ntpq ${bindir}/ntptime ${bindir}/ntptrace"
FILES_${PN} = "${bindir}/ntpd ${sysconfdir}/ntp.conf ${sysconfdir}/init.d/ntpd"
FILES_${PN}-tickadj = "${bindir}/tickadj"
FILES_ntp-utils = "${bindir}/*"
FILES_ntpdate = "${bindir}/ntpdate ${sysconfdir}/network/if-up.d/ntpdate"

# do_configure_prepend() {
# 	sed -i -e 's:dist_man_MANS=	sntp.1::g' sntp/Makefile.am
# }

do_install_append() {
	install -d ${D}/${sysconfdir}/init.d
	install -m 644 ${WORKDIR}/ntp.conf ${D}/${sysconfdir}
	install -m 755 ${WORKDIR}/ntpd ${D}/${sysconfdir}/init.d
	install -d ${D}/${sysconfdir}/network/if-up.d
	install -m 755 ${WORKDIR}/ntpdate ${D}/${sysconfdir}/network/if-up.d
}

#pkg_postinst_ntpdate() {
#if test "x$D" != "x"; then
#	exit 1
#else
#	if ! grep -q -s ntpdate /etc/cron/crontabs/root; then
#		echo "adding crontab"
#		test -d /etc/cron/crontabs || mkdir -p /etc/cron/crontabs
#		echo "30 * * * *    /usr/bin/ntpdate -s -u pool.ntp.org" >> /etc/cron/crontabs/root
#	fi
#fi
#}

# SRC_URI[md5sum] = "8c19ff62ed4f7d64f8e2aa59cb11f364"
# SRC_URI[sha256sum] = "5681883ce5cd0666d73c1b907e284653964a25ad4c02a308ab11b54aca8f01c3"

#
# debian-squeeze
#

inherit debian-squeeze

SRC_URI = " \
file://ntp-4.2.4_p6-nano.patch;patch=1 \
file://tickadj.c.patch;patch=1 \
file://ntpd \
file://ntp.conf \
file://ntpdate \
"

# NOTE: ntp (>= 4.2.6.p2+dfsg-1+deb6u1) uses openssl library instead of
# ntp internal random number generator because of its vulnerability
DEPENDS += "openssl"
EXTRA_OECONF = " \
--with-openssl-libdir=${STAGING_LIBDIR} \
--with-openssl-incdir=${STAGING_INCDIR} \
ac_cv_header_readline_history_h=no \
"

# /tmp is required when ntpd boot up
FILES_${PN} += "/tmp"
do_install_append() {
	install -d ${D}/tmp
}

# ntpsweep: This script should not be included in other package
# because it depends on perl at the runtime
PACKAGES =+ "ntpsweep"
FILES_ntpsweep = "${bindir}/ntpsweep"
RDEPENDS_ntpsweep = "ntpdate perl-base"
do_install_append() {
	install -d ${D}/${bindir}
	install -m 0755 ${S}/scripts/ntpsweep ${D}/${bindir}
}

# git fetch: Avoid re-generating some files
# keyword-gen (target binary) need to be run to build
# keyword-gen-utd but it cannot be run on host.
do_configure_prepend() {
	touch ${S}/ntpd/keyword-gen-utd
	touch ${S}/ntpd/ntpd.1
	touch ${S}/ntpd/ntpd-opts.texi
}
