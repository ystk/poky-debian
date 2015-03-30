#
# debian-squeeze
#

DESCRIPTION = "AppleTalk user binaries"
SECTION = "net"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

PR = "r0"

inherit debian-squeeze
inherit autotools

DEPENDS = " \
e2fsprogs \
cracklib \
db \
libgcrypt11 \
gnutls \
krb5 \
libpam \
tcp-wrappers \
perl \
"

LIBTOOL = "${HOST_SYS}-libtool"
EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'"

do_configure() {
	autoreconf
	ac_cv_header_rpcsvc_rquota_h=no LDFLAGS="-lpthread -L${STAGING_DIR}/${TARGET_SYS}/lib" ./configure \
		--build=${BUILD_SYS} \
		--host=${HOST_SYS} \
		--target=${TARGET_SYS} \
		--prefix=${prefix} \
		--with-bdb=${STAGING_DIR_HOST}/usr \
		--with-ssl-dir=${STAGING_DIR_HOST}/usr \
		--without-shadow \
		--sysconfdir=${sysconfdir} \
		--disable-nls \
		--disable-static \
		--with-pam \
		--mandir=${mandir}
}

