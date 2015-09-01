#
# php_5.3.6.bb
#
require php.inc

#LIC_FILES_CHKSUM = "file://LICENSE;md5=cb564efdf78cce8ea6e4b5a4f7c05d97"

#PR = "${INC_PR}.0"

#SRC_URI += "file://acinclude-xml2-config.patch \
#            file://php-m4-divert.patch \
#            file://0001-php-don-t-use-broken-wrapper-for-mkdir.patch"
#
#SRC_URI_append_pn-php += "file://iconv.patch \
#            file://imap-fix-autofoo.patch \
#            file://pear-makefile.patch \
#            file://phar-makefile.patch \
#            file://php_exec_native.patch \
#            "

SRC_URI[md5sum] = "2286f5a82a6e8397955a0025c1c2ad98"
SRC_URI[sha256sum] = "30ae880f22e3ee93eccc9b80e3c58b0d6364d139bb4366dcc27f2fab658d3198"
#
# debian-squeeze
#
inherit debian-squeeze
SRC_URI += "file://acinclude-xml2-config.patch \
            file://php-m4-divert.patch \
            file://0001-php-don-t-use-broken-wrapper-for-mkdir.patch \
	    file://fix_use_embedded_timezonedb.patch " 

SRC_URI_append_pn-php += "file://iconv.patch \
            file://imap-fix-autofoo.patch \
            file://pear-makefile.patch \
            file://phar-makefile.patch \
            file://php_exec_native.patch \
            "
PR = "r0"
DEBIAN_SQUEEZE_SRCPKG_NAME = "php5"
LIC_FILES_CHKSUM = "file://LICENSE;md5=120722ac328447294083a64f651c3ddc"
DEPENDS = "zlib libxml2 virtual/libiconv php-native lemon-native mysql gdbm \
           openssl bzip2 krb5 db pcre mhash tzdata libonig sqlite3 postgresql \
	   openldap net-snmp apache2-native apache2"

CFLAGS += " -I${STAGING_INCDIR}/apache2"

EXTRA_OECONF += " \
--without-imap \
--enable-shared \
--with-apxs2=${STAGING_BINDIR_NATIVE}/apxs \
"

export PHP_PEAR_DOWNLOAD_DIR = "${S}/pear-build-download"

do_configure_prepend() {
	sed -i -e "s#install-sapi##" configure
	sed -i -e "s#install-sapi##" configure.in
}

# dirty trick to redefine php_config.h
do_compile_prepend() {
	sed -i -e 's@PHP_MD5_CRYPT 0@PHP_MD5_CRYPT 1@' \
		-e 's@PHP_SHA256_CRYPT 0@PHP_SHA256_CRYPT 1@' \
		-e 's@PHP_SHA512_CRYPT 0@PHP_SHA512_CRYPT 1@' \
		${S}/main/php_config.h
}

# Fix me. This is not good way.
INSTALL_MODULES1="install -d ${D}${libdir}/apache2/modules"
INSTALL_MODULES2="install -m 0755 ${S}/libs/libphp5.so ${D}${libdir}/apache2/modules"
INSTALL_MODULES3="for i in mods-available mods-enabled; do"
INSTALL_MODULES4="    install -d ${D}${sysconfdir}/apache2/$i"
INSTALL_MODULES5="	  install -m 0644 ${S}/debian/libapache2-mod-php5.load ${D}${sysconfdir}/apache2/$i/php5.load"
INSTALL_MODULES6="	  install -m 0644 ${S}/debian/libapache2-mod-php5.conf ${D}${sysconfdir}/apache2/$i/php5.conf"
INSTALL_MODULES7="done"

INSTALL_MODULES1_virtclass-native=""
INSTALL_MODULES2_virtclass-native=""
INSTALL_MODULES3_virtclass-native=""
INSTALL_MODULES4_virtclass-native=""
INSTALL_MODULES5_virtclass-native=""
INSTALL_MODULES6_virtclass-native=""
INSTALL_MODULES7_virtclass-native=""

do_install_append() {
	${INSTALL_MODULES1}
	${INSTALL_MODULES2}
	${INSTALL_MODULES3}
	${INSTALL_MODULES4}
	${INSTALL_MODULES5}
	${INSTALL_MODULES6}
	${INSTALL_MODULES7}
}

FILES_${PN} += "${libdir} ${includedir} ${sysconfdir}"
FILES_${PN}-dbg += " \
${libdir}/extensions/no-debug-non-zts-20090626/.debug \
${libdir}/apache2/modules/.debug"
