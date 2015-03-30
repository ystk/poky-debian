#
# debian-squeeze
#
DESCRIPTION = "Red Hat cluster suite"
SECTION = "admin"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://doc/COPYING.libraries;md5=2d5025d4aa3495befef8f17206a5b0a1"

inherit debian-squeeze autotools

DEPENDS += "libxml2 slang openldap corosync cluster-glue openais glib-2.0 \
		bzip2 pacemaker libvirt libnl nspr nss pexpect-native"

SRC_URI += "file://fence_ipmilan.8"

CONFIGUREOPTS = " \
--prefix=${prefix} \
--sbindir=${sbindir} \
--libexecdir=${libexecdir} \
--libdir=${libdir} \
--mandir=${mandir} \
"

EXTRA_OECONF = " \
--incdir=${STAGING_INCDIR} \
--corosynclibdir=${STAGING_LIBDIR} \
--slanglibdir=${STAGING_LIBDIR} \
--ncurseslibdir=${STAGING_LIBDIR} \
--openaislibdir=${STAGING_LIBDIR} \
--nsslibdir=${STAGING_LIBDIR} \
--nsprincdir=${STAGING_INCDIR} \
--nsprlibdir=${STAGING_LIBDIR} \
--ldaplibdir=${STAGING_LIBDIR} \
--virtlibdir=${STAGING_LIBDIR} \
--zliblibdir=${STAGING_LIBDIR} \
--enable_gfs \
--enable_pacemaker \
--disable_kernel_check \
--without_kernel_modules \
--without_bindings \
"

do_configure_append() {
	sed -i -e "s@^CC.*@CC = ${CC}@" make/defines.mk
	sed -i -e "s@^AR.*@AR = ${AR}@" make/defines.mk
	sed -i -e "s@^RANLIB.*@RANLIB = ${RANLIB}@" make/defines.mk
	sed -i -e "/{libdir}/d" $(find -name Makefile)
	sed -i -e "s@$"{libdir}"@${D}$"{libdir}"@g" make/install.mk
	sed -i -e "s@$"{incdir}"@${D}$"{incdir}"@g" make/install.mk
	sed -i -e "s@$"{sbindir}"@${D}$"{sbindir}"@g" make/install.mk
	sed -i -e "s@$"{libexecdir}"@${D}$"{libexecdir}"@g" make/install.mk
	sed -i -e "s@$"{docdir}"@${D}$"{docdir}"@g" make/install.mk
	sed -i -e "s@$"{mandir}"@${D}$"{mandir}"@g" make/install.mk
	sed -i -e "s@$"{sbindir}"@${D}$"{sbindir}"@g" gfs/Makefile
	# Don't generate fence_ipmilan man page
	sed -i -e "s@$"{MANTARGET}"@@" fence/agents/ipmilan/Makefile
	cp ${WORKDIR}/fence_ipmilan.8 fence/agents/ipmilan/
}

do_install_prepend() {
	sed -i -e "s@^incdir .*@incdir ?= ${includedir}@" make/defines.mk
}

do_install_append() {
	install -m 0644 ${S}/debian/cman.logrotate ${D}${sysconfdir}/logrotate.d
	install -d ${D}${sysconfdir}/default
	install -m 0644 ${S}/debian/cman.default ${D}${sysconfdir}/default
	install -d ${D}${base_libdir}/udev/rules.d
	mv ${D}${sysconfdir}/udev/rules.d/51-dlm.rules ${D}${base_libdir}/udev/rules.d
	rm -r ${D}${sysconfdir}/udev/rules.d/
	mv ${D}${sysconfdir}/init.d/gfs ${D}${sysconfdir}/init.d/gfs-tools
	mv ${D}${sysconfdir}/init.d/gfs2 ${D}${sysconfdir}/init.d/gfs2-tools
}

INITSCRIPT_PARAMS = "defaults"

pkg_postinst() {
	if test "x$D" != "x"; then
		OPT="-r $D"
	else
		OPT="-s"
	fi
	for i in cman gfs-tools gfs2-tools rgmanager; do
		update-rc.d $OPT $i ${INITSCRIPT_PARAMS}
	done
}

FILES_${PN} += "${prefix} ${base_libdir}"
INSANE_SKIP_${PN} = "dev-so"
