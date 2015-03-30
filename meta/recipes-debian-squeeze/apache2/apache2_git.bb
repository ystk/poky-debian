#
# apache2_2.2.17.bb
# This recipe is imported from OpenEmbedded
# Commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

SECTION = "net"
DESCRIPTION = "The apache v2 web server"
DEPENDS = "libtool-native apache2-native openssl expat pcre apr apr-util"
RDEPENDS_${PN} += "openssl"

#PR = "r0"

#SRC_URI = "http://apache.mirrors.tds.net/httpd/httpd-${PV}.tar.bz2 \
#	   file://apr-sockets-patch;apply=yes \
#	   file://configure-patch;apply=yes \
#	   file://server-makefile-patch;apply=yes \
#	   file://configure.in.patch \
#	   file://apr.h.in.patch \
#           file://init"

#
# over-ride needed since apache unpacks into httpd
#
#S = "${WORKDIR}/httpd-${PV}"

#
# implications - autotools defines suitable do_configure, do_install, etc.
# update-rc.d adds hooks for rc-update.
#
#
inherit autotools update-rc.d

#
# implications - used by update-rc.d scripts
#
INITSCRIPT_NAME = "apache2"
INITSCRIPT_PARAMS = "defaults 91 20"
LEAD_SONAME = "libapr-1.so.0"

CONFFILES_${PN} = "${sysconfdir}/${PN}/httpd.conf \
		   ${sysconfdir}/${PN}/magic \
		   ${sysconfdir}/${PN}/mime.types \
		   ${sysconfdir}/init.d/${PN} "

PACKAGES = "${PN}-doc ${PN}-dev ${PN}-dbg ${PN}"

# we override here rather than append so that .so links are
# included in the runtime package rather than here (-dev)
# and to get build, icons, error into the -dev package
FILES_${PN}-dev = "${datadir}/${PN}/build \
		${datadir}/${PN}/icons \
		${datadir}/${PN}/error \
		${bindir}/apr-config ${bindir}/apu-config \
		${libdir}/apr*.exp \
		${includedir}/${PN} \
		${libdir}/*.la \
		${libdir}/*.a"

# manual to manual
FILES_${PN}-doc += " ${datadir}/${PN}/manual"

#
# override this too - here is the default, less datadir
#
FILES_${PN} =  "${bindir} ${sbindir} ${libexecdir} ${libdir}/lib*.so.* ${sysconfdir} \
		${sharedstatedir} ${localstatedir} /bin /sbin /lib/*.so* \
		${libdir}/${PN}"

# we want htdocs and cgi-bin to go with the binary
FILES_${PN} += "${datadir}/${PN}/htdocs ${datadir}/${PN}/cgi-bin"

#make sure the lone .so links also get wrapped in the base package
FILES_${PN} += " ${libdir}/lib*.so ${libdir}/pkgconfig/*"

CFLAGS_append = " -DPATH_MAX=4096"
CFLAGS_prepend = "-I${STAGING_INCDIR}/openssl "
#EXTRA_OECONF = "--enable-ssl \
#		--with-ssl=${STAGING_LIBDIR}/.. \
#		--with-expat=${STAGING_LIBDIR}/.. \
#		--with-pcre=${STAGING_LIBDIR}/.. \
#		--with-apr=${STAGING_BINDIR_CROSS}/apr-1-config \
#		--with-apr-util=${STAGING_BINDIR_CROSS}/apu-1-config \
#		--enable-info \
#		--enable-rewrite \
#		--with-dbm=sdbm \
#		--with-berkeley-db=no \
#		--localstatedir=/var/${PN} \
#		--with-gdbm=no \
#		--with-ndbm=no \
#		--includedir=${includedir}/${PN} \
#		--datadir=${datadir}/${PN} \
#		--sysconfdir=${sysconfdir}/${PN} \
#		ap_cv_void_ptr_lt_long=no \
#		"

#
# here we over-ride the autotools provided do_configure.
#

do_configure_prepend() {
	sed -e 's,libtool libtool15,${HOST_SYS}-libtool libtool115,' -i ${S}/srclib/apr/build/buildcheck.sh
	echo "[AKI] ${STAGING_BINDIR_CROSS}"
}

do_compile_prepend() {
	ln -sf ${S}/srclib/apr/${HOST_SYS}-libtool ${S}/srclib/apr/libtool
	cp ${STAGING_BINDIR}/crossscripts/${HOST_SYS}-libtool ${S}/shlibtool
}	

do_install_append() {
	install -d ${D}/${sysconfdir}/init.d
	cat ${WORKDIR}/init | \
		sed -e 's,/usr/sbin/,${sbindir}/,g' \
		    -e 's,/usr/bin/,${bindir}/,g' \
		    -e 's,/usr/lib,${libdir}/,g' \
		    -e 's,/etc/,${sysconfdir}/,g' \
		    -e 's,/usr/,${prefix}/,g' > ${D}/${sysconfdir}/init.d/${PN}
	chmod 755 ${D}/${sysconfdir}/init.d/${PN}
# remove the goofy original files...
	rm -rf ${D}/${sysconfdir}/${PN}/original
# Expat should be found in the staging area via DEPENDS...
	rm -f ${D}/${libdir}/libexpat.*
}

#SRC_URI[md5sum] = "16eadc59ea6b38af33874d300973202e"
#SRC_URI[sha256sum] = "868af11e3ed8fa9aade15241ea4f51971b3ef71104292ca2625ef2065e61fb04"

#
# debian-squeeze
#

inherit debian-squeeze
inherit daemon

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0ca4f2e9750d1c2b144d78be04833ff2"

PR = "r0"

DEPENDS += " openldap apr apr-util"

SRC_URI = " \
file://init \
file://server-makefile-patch;apply=yes \
file://httpd.conf \
"

# Fix "--with-pcre" (path to pcre-config)
EXTRA_OECONF = " \
--with-program-name=httpd \
--enable-so \
--enable-ssl \
--enable-mods-shared=all \
--enable-authn-alias=shared \
--enable-authnz-ldap=shared \
--enable-isapi=shared \
--enable-file-cache=shared \
--enable-cache=shared \
--enable-disk-cache=shared \
--enable-mem-cache=shared \
--enable-bucketeer=shared \
--enable-echo=shared \
--enable-case-filter=shared \
--enable-case-filter-in=shared \
--enable-charset-lite=shared \
--enable-ldap=shared \
--enable-proxy=shared \
--enable-proxy-connect=shared \
--enable-proxy-ftp=shared \
--enable-proxy-http=shared \
--enable-proxy-scgi=shared \
--enable-proxy_ajp=shared \
--enable-proxy-balancer=shared \
--enable-optional-hook-export=shared \
--enable-optional-hook-import=shared \
--enable-optional-fn-export=shared \
--enable-optional-fn-import=shared \
--enable-suexec=shared \
--enable-cgid=shared \
--enable-dav-lock=shared \
--with-ssl=${STAGING_LIBDIR}/.. \
--with-expat=${STAGING_LIBDIR}/.. \
--with-pcre=${STAGING_BINDIR_CROSS}/pcre-config \
--with-apr=${STAGING_BINDIR_CROSS}/apr-1-config \
--with-apr-util=${STAGING_BINDIR_CROSS}/apu-1-config \
--enable-info \
--enable-rewrite \
--with-dbm=sdbm \
--with-berkeley-db=no \
--localstatedir=/var/${PN} \
--with-gdbm=no \
--with-ndbm=no \
--with-ldap=yes \
--with-ldap-include=${STAGING_INCDIR} \
--with-ldap-lib=${STAGING_LIBDIR} \
--includedir=${includedir}/${PN} \
--datadir=${datadir}/${PN} \
--sysconfdir=${sysconfdir}/${PN} \
--libexecdir=${libdir}/${PN}/modules \
ap_cv_void_ptr_lt_long=no \
"

# Override libtool to fix libtool-related error in do_compile
EXTRA_OEMAKE += "LIBTOOL=${HOST_SYS}-libtool"

do_install_append() {
	install -m 0755 ${S}/debian/a2enmod ${D}${sbindir}
	cd ${D}${sbindir}
	ln -s a2enmod a2dismod
	ln -s a2enmod a2dissite
	ln -s a2enmod a2ensite
	for i in a2enmod a2dismod a2ensite a2dissite; do
		install -m 0644 ${S}/debian/$i.8 ${D}${datadir}/man/man8
	done
	mv ${D}${sbindir}/envvars-std ${D}${datadir}/apache2/build
	mv ${D}${sbindir}/envvars ${D}${sysconfdir}/apache2

	# install debian configuration
	install -d ${D}${sysconfdir}/default
	install -m 0644 ${S}/debian/apache2.2-common.apache2.default ${D}${sysconfdir}/default/apache2
	install -d ${D}${datadir}/doc/apache2.2-common/examples
	for i in apache2.monit secondary-init-script setup-instance; do
		install -m 0644 ${S}/debian/$i ${D}${datadir}/doc/apache2.2-common/examples
	done

	for i in conf.d mods-available sites-available ports.conf; do
		cp -r ${S}/debian/config-dir/$i ${D}${sysconfdir}/${PN}
	done
	install -d ${D}${sysconfdir}/${PN}/mods-enabled
	install -d ${D}${sysconfdir}/${PN}/sites-enabled

	install -d ${D}${sysconfdir}/cron.daily
	install -m 0755 ${S}/debian/apache2.2-common.apache2.cron.daily ${D}${sysconfdir}/cron.daily/apache2.2-common
	install -d ${D}${sysconfdir}/logrotate.d
	install -m 0644 ${S}/debian/logrotate ${D}${sysconfdir}/logrotate.d/apache2

	install -d ${D}${libdir}/cgi-bin
	install -d ${D}${datadir}/bug/apache2.2-common
	install -m 0644 ${S}/debian/apache2.2-common.bug-control ${D}${datadir}/bug/apache2.2-common/control
	install -m 0755 ${S}/debian/apache2.2-common.bug-script ${D}${datadir}/bug/apache2.2-common/script

	install -m 0644 ${WORKDIR}/httpd.conf ${D}${sysconfdir}/apache2
}

FILES_${PN} += "${datadir}/*"
FILES_${PN}-dbg += "${libdir}/${PN}/modules/.debug"
