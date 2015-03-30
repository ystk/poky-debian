#
# debian-squeeze 
#

DESCRIPTION = "lightweight, efficient FTP server written for security"
SECTION = "net" 
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6067ad950b28336613aed9dd47b1271"
PR = "r0"

DEPENDS = "libcap openssl tcp-wrappers adduser"
DEPENDS += "${@base_contains('DISTRO_FEATURES', 'pam', 'libpam', '', d)}"

inherit autotools 
inherit debian-squeeze
inherit update-rc.d

INITSCRIPT_NAME = "vsftpd"
INITSCRIPT_PARAMS = "defaults"

SRC_URI += "file://fixMakefile.patch"

# Statically define libraries linked to vsftpd instead of
# using vsf_findlibs.sh. The value of VSFTPD_LIBS comes from
# debian/rules. Only libpam is dynamically linked according to
# DISTRO_FEATURES. If PAM is disabled, libcrypt provided by libc
# must be used instead (see vsf_findlibs.sh).
VSFTPD_LIBS_PAM = \
"${@base_contains('DISTRO_FEATURES', 'pam', '-lpam', '-lcrypt', d)}"
VSFTPD_LIBS = "-lwrap ${VSFTPD_LIBS_PAM} -lcap -lssl -lcrypto"
EXTRA_OEMAKE += "'LIBS=${VSFTPD_LIBS}'"

do_compile_prepend() {
	sed -i -e "s/root:adm/root:root/" ${S}/debian/vsftpd.init

	if [ "${@base_contains('DISTRO_FEATURES', 'pam', 1, 0, d)}" = "0" ]
	then
		sed -i -e "s|#define VSF_BUILD_PAM||" ${S}/builddefs.h
	fi
}

inherit passwd

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -m 644 ${S}/debian/local/ftpusers ${D}${sysconfdir}
	install -m 755 ${S}/debian/vsftpd.init ${D}${sysconfdir}/init.d/vsftpd
	install -m 644 ${S}/vsftpd.conf ${D}${sysconfdir}
	for i in ${DISTRO_FEATURES};
	do
		if [ ${i} = "pam" ];  then
			install -d ${D}${sysconfdir}/pam.d
			install -m 0755 ${S}/debian/vsftpd.pam ${D}${sysconfdir}/pam.d/vsftpd
		fi
	done

	# group and password
	install -d ${D}/${GROUP_DIR}
	echo "ftp:x:121:" > ${D}/${GROUP_DIR}/${PN}
        install -d ${D}/${PASSWD_DIR}
	echo "ftp:x:121:121:ftp:/var/lib/ftp:/bin/false" > ${D}/${PASSWD_DIR}/${PN}
}

FILES_${PN} += " ${sysconfdir}/*"

pkg_postinst() {
	# for a secure chroot() jail
	install -d ${D}${localstatedir}/run/vsftpd/empty
}
