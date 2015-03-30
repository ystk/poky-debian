DESCRIPTION = "High-performance mail transport agent"
#SECTION = "console/network"
SECTION = "mail"
DEPENDS += "virtual/db libpcre"
#postfix-native"

DEPENDS_virtclass-native = "virtual/db-native libpcre-native"
LICENSE = "IPL-1.0"

INC_PR = "r13"

#
#SRC_URI = "ftp://ftp.porcupine.org/mirrors/postfix-release/official/postfix-${PV}.tar.gz;name=postfix-${PV} \
#	   file://makedefs.patch \
#	   file://install.patch \
#	   file://main.cf_2.0 \
#	   file://volatiles \
#	   file://postfix \
#	   file://internal_recipient \
#	   "
#
#S = "${WORKDIR}/postfix-${PV}"

#BBCLASSEXTEND = "native"

inherit update-rc.d

INITSCRIPT_NAME = "postfix"
INITSCRIPT_PARAMS = "start 58 3 4 5 . stop 13 0 1 6 ."

#FIXME: this is broken because the native build won't work on systems where
# native bdb does not require libpthread.  ARM doesn't require libpthread
# because it uses an assembler mutex implementation.
LIBBDB_EXTRA = "-lpthread"
LIBBDB_EXTRA_arm = ""
LIBBDB_EXTRA_armeb = ""

export SYSLIBS = "-lpcre -ldb ${LIBBDB_EXTRA} -lnsl -lresolv ${LDFLAGS}"
export EXPORT = "AUXLIBS='-lpcre' CCARGS='-DHAS_PCRE ${CFLAGS}' OPT='' DEBUG='-g'"
export CC_append = " -DHAS_PCRE ${CFLAGS}"
export EXTRA_OEMAKE = "-e"
#export POSTCONF = "${STAGING_SBINDIR_NATIVE}/postconf"

do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS
	oe_runmake makefiles
	oe_runmake
}

#do_install_prepend_virtclass-native() {
#	export POSTCONF="bin/postconf"
#}

do_install () {
	sh ./postfix-install 'install_root=${D}' \
		'config_directory=${sysconfdir}/postfix' \
		'daemon_directory=${libexecdir}/postfix' \
		'command_directory=${sbindir}' \
		'queue_directory=${localstatedir}/spool/postfix' \
		'sendmail_path=${sbindir}/sendmail.postfix' \
		'newaliases_path=${bindir}/newaliases' \
		'mailq_path=${bindir}/mailq' \
		'manpage_directory=${mandir}' \
		'readme_directory=${datadir}/doc/postfix' \
		'data_directory=${localstatedir}/lib/postfix' \
		'html_directory=no' \
		-non-interactive
	rm -rf ${D}${localstatedir}/spool/postfix
        mv ${D}${sysconfdir}/postfix/main.cf ${D}${sysconfdir}/postfix/sample-main.cf
	install -d ${D}${localstatedir}/tmp
	install -d ${D}${sysconfdir}/default/volatiles
	install -d ${D}${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/main.cf_2.0 ${D}${localstatedir}/tmp/main_cf.sh
        install -m 644 ${WORKDIR}/volatiles ${D}${sysconfdir}/default/volatiles/01_postfix
        install -m 755 ${WORKDIR}/postfix ${D}${sysconfdir}/init.d/postfix
        install -m 644 ${WORKDIR}/internal_recipient ${D}${sysconfdir}/postfix/internal_recipient
}

NATIVE_INSTALL_WORKS = "1"

pkg_postinst () {
	update-alternatives --install ${sbindir}/sendmail sendmail sendmail.${PN} 40
        grep postfix /etc/group || addgroup postfix
        grep postdrop /etc/group || addgroup postdrop
        grep vmail /etc/group || addgroup vmail
        grep postfix /etc/passwd || adduser --disabled-password --home=/var/spool/postfix --ingroup postfix -g "Postfix" postfix
        grep vmail /etc/passwd || adduser --disabled-password --home=/var/spool/vmail --ingroup vmail -g "Postfix" vmail
	chgrp postdrop /usr/sbin/postqueue
	chgrp postdrop /usr/sbin/postdrop
	chmod g+s /usr/sbin/postqueue
	chmod g+s /usr/sbin/postdrop
	/var/tmp/main_cf.sh >/etc/postfix/main.cf
	rm -f /var/tmp/main_cf.sh
	chmod 644 /etc/postfix/main.cf
	[ -d /var/spool/postfix ] && rmdir /var/spool/postfix
	/etc/init.d/populate-volatile.sh update
	touch /etc/aliases
	newaliases
}

pkg_postrm () {
	update-alternatives --remove sendmail sendmail.${PN}
}

# Exclude .debug directories from the main package
FILES_${PN} = "${sysconfdir} ${localstatedir} ${bindir}/* ${sbindir}/* \
               ${libexecdir}/postfix/*"
FILES_${PN}-dbg += "${libexecdir}/postfix/.debug"

inherit debian-squeeze

LIC_FILES_CHKSUM = "file://LICENSE;md5=64375f37431336ea1b1b3005fe3fa354"

SRC_URI = " \
          file://makedefs.patch \
          file://install.patch \
          file://main.cf_2.0 \
          file://volatiles \
          file://postfix \
          file://internal_recipient \
          "
do_install_prepend() {
        sed -i "/dict_sdbm/d" $(find ${S} -name postfix-files)
        sed -i "/dynamicmaps/d" $(find ${S} -name postfix-files)

        ln -s ${S}/lib/libglobal.a ${S}/lib/libpostfix-global.so.1
        ln -s ${S}/lib/libutil.a ${S}/lib/libpostfix-util.so.1
        ln -s ${S}/lib/libmaster.a ${S}/lib/libpostfix-master.so.1
        ln -s ${S}/lib/libtls.a ${S}/lib/libpostfix-tls.so.1
        ln -s ${S}/lib/libdns.a ${S}/lib/libpostfix-dns.so.1

        ln -s ${S}/lib/dict_cdb.so ${S}/libexec/dict_cdb.so
        ln -s ${S}/lib/dict_ldap.so ${S}/libexec/dict_ldap.so
        ln -s ${S}/lib/dict_mysql.so ${S}/libexec/dict_mysql.so
        ln -s ${S}/lib/dict_pcre.so ${S}/libexec/dict_pcre.so
        ln -s ${S}/lib/dict_pgsql.so ${S}/libexec/dict_pgsql.so
        ln -s ${S}/lib/dict_tcp.so ${S}/libexec/dict_tcp.so
	export_library_path
}

do_install_append() {
#       install library
	mkdir -p ${LIBDIR}
	install -m 755 ${S}/lib/libutil.a ${LIBDIR}/libpostfix-util.so.1
	install -m 755 ${S}/lib/libglobal.a ${LIBDIR}/libpostfix-global.so.1
	install -m 755 ${S}/lib/libdns.a ${LIBDIR}/libpostfix-dns.so.1
	install -m 755 ${S}/lib/libtls.a ${LIBDIR}/libpostfix-tls.so.1
	install -m 755 ${S}/lib/libmaster.a ${LIBDIR}/libpostfix-master.so.1
}
