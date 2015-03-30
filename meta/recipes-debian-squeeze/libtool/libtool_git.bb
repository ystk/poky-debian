require libtool-${PV}.inc

PR = "r2"

#
# We want the results of libtool-cross preserved - don't stage anything ourselves.
#
SYSROOT_PREPROCESS_FUNCS += "libtool_sysroot_preprocess"

libtool_sysroot_preprocess () {
	rm -rf ${SYSROOT_DESTDIR}${bindir}/*
	rm -rf ${SYSROOT_DESTDIR}${datadir}/aclocal/*
	rm -rf ${SYSROOT_DESTDIR}${datadir}/libtool/config/*
}

#
# debian-squeeze
#

do_configure_prepend() {
	# Remove any existing libtool m4 since old stale versions
	# would break any upgrade
	rm -f ${STAGING_DATADIR}/aclocal/libtool.m4
	rm -f ${STAGING_DATADIR}/aclocal/lt*.m4
}
