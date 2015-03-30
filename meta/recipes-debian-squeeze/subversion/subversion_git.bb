DESCRIPTION = "Advanced version control system"
SECTION = "vcs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=2a69fef414e2cb907b4544298569300b"

inherit debian-squeeze autotools gettext

DEPENDS += " apr-util neon27"

EXTRA_OECONF = " \
--enable-shared=yes \
--with-apr=${STAGING_BINDIR_CROSS} \
--with-apr-util=${STAGING_BINDIR_CROSS} \
--with-neon=${STAGING_EXECPREFIXDIR} \
--without-berkeley-db --without-apxs --without-apache \
--without-swig \
"

CC += " -fPIC -D_LARGEFILE64_SOURCE -DLINUX -pthread"

acpaths = "-I build/ -I build/ac-macros/"

do_configure () {
	sed -i -e 's:with_sasl="/usr/local":with_sasl="${STAGING_DIR}":' ${S}/build/ac-macros/sasl.m4
	oe_runconf
}

export LIBTOOL="${STAGING_BINDIR}/crossscripts/${HOST_SYS}-libtool"
EXTRA_OEMAKE += " 'LIBTOOL=${LIBTOOL}'"

do_install() {
	mkdir -p ${D}${libdir}
	install -m 644 subversion/libsvn_subr/.libs/libsvn_subr-1.a ${D}${libdir}/libsvn_subr-1.a
	install -m 644 subversion/libsvn_subr/.libs/libsvn_subr-1.la ${D}${libdir}/libsvn_subr-1.la
	install -m 644 subversion/libsvn_delta/.libs/libsvn_delta-1.a ${D}${libdir}/libsvn_delta-1.a
	install -m 644 subversion/libsvn_delta/.libs/libsvn_delta-1.la ${D}${libdir}/libsvn_delta-1.la
	install -m 644 subversion/libsvn_fs_util/.libs/libsvn_fs_util-1.a ${D}${libdir}/libsvn_fs_util-1.a
	install -m 644 subversion/libsvn_fs_util/.libs/libsvn_fs_util-1.la ${D}${libdir}/libsvn_fs_util-1.la
	install -m 644 subversion/libsvn_fs_fs/.libs/libsvn_fs_fs-1.a ${D}${libdir}/libsvn_fs_fs-1.a
	install -m 644 subversion/libsvn_fs_fs/.libs/libsvn_fs_fs-1.la ${D}${libdir}/libsvn_fs_fs-1.la
	install -m 644 subversion/libsvn_fs/.libs/libsvn_fs-1.a ${D}${libdir}/libsvn_fs-1.a
	install -m 644 subversion/libsvn_fs/.libs/libsvn_fs-1.la ${D}${libdir}/libsvn_fs-1.la
	install -m 644 subversion/libsvn_ra_svn/.libs/libsvn_ra_svn-1.a ${D}${libdir}/libsvn_ra_svn-1.a
	install -m 644 subversion/libsvn_ra_svn/.libs/libsvn_ra_svn-1.la ${D}${libdir}/libsvn_ra_svn-1.la
	install -m 644 subversion/libsvn_repos/.libs/libsvn_repos-1.a ${D}${libdir}/
	install -m 644 subversion/libsvn_repos/.libs/libsvn_repos-1.la ${D}${libdir}
	install -m 644 subversion/libsvn_ra_local/.libs/libsvn_ra_local-1.a ${D}${libdir}
	install -m 644 subversion/libsvn_ra_local/.libs/libsvn_ra_local-1.la ${D}${libdir}
	install -m 644 subversion/libsvn_diff/.libs/libsvn_diff-1.a ${D}${libdir}
	install -m 644 subversion/libsvn_diff/.libs/libsvn_diff-1.la ${D}${libdir}
	install -m 644 subversion/libsvn_ra/.libs/libsvn_ra-1.a ${D}${libdir}
	install -m 644 subversion/libsvn_ra/.libs/libsvn_ra-1.la ${D}${libdir}
	install -m 644 subversion/libsvn_wc/.libs/libsvn_wc-1.a ${D}${libdir}
	install -m 644 subversion/libsvn_wc/.libs/libsvn_wc-1.la ${D}${libdir}
	install -m 644 subversion/libsvn_client/.libs/libsvn_client-1.a ${D}${libdir}
	install -m 644 subversion/libsvn_client/.libs/libsvn_client-1.la ${D}${libdir}

	mkdir -p ${D}${bindir}
	install -m 755 subversion/svn/.libs/svn ${D}${bindir}/svn
	install -m 755 subversion/svnadmin/.libs/svnadmin ${D}${bindir}/svnadmin
	install -m 755 subversion/svndumpfilter/.libs/svndumpfilter ${D}${bindir}/svndumpfilter
	install -m 755 subversion/svnlook/.libs/svnlook ${D}${bindir}/svnlook
	install -m 755 subversion/svnserve/.libs/svnserve ${D}${bindir}/svnserve
	install -m 755 subversion/svnsync/.libs/svnsync ${D}${bindir}/svnsync
	install -m 755 subversion/svnversion/.libs/svnversion ${D}${bindir}/svnversion

	mkdir -p ${D}${includedir}/subversion-1
	cp subversion/include/*.h ${D}${includedir}/subversion-1
	chmod 644 ${D}${includedir}/subversion-1/*.h
}

FILES_${PN} = "${bindir}/*"
