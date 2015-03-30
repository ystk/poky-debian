#
# debian-squeeze
#
DESCRIPTION = "tools for managing OCFS2 cluster filesystems"
HOMEPAGE = "http://oss.oracle.com/projects/ocfs2-tools/"
SECTION = "admin"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit debian-squeeze autotools

DEBIAN_SQUEEZE_SRCPKG_NAME = "ocfs2-tools"
BACKPORT_SRCREV = "ashitaka-backports-master"

DEPENDS += "ncurses readline glib-2.0 bzip2 redhat-cluster openais \
		libxml2 pacemaker python e2fsprogs e2fsprogs-native"


SRC_URI += "file://remove_while_loop_backports.patch"

PARALLEL_MAKE = ""

do_patch_srcpkg_prepend() {
	cd ${S}
	git checkout ${BACKPORT_SRCREV}
}

do_compile_prepend() {
	sed -i -e "s@^root_sbindir =.*@root_sbindir = ${base_sbindir}@" Config.make
	sed -i -e "s@^AR =.*@AR = ${AR}@" Config.make
	sed -i -e "s@^HAVE_BLKID =.*@HAVE_BLKID = yes@" Config.make
	sed -i -e "s@BLKID_CFLAGS =.*@BLKID_CFLAGS = -I${STAGING_INCDIR}/blkid@" Config.make
	sed -i -e "s@BLKID_LIBS =.*@BLKID_LIBS = -L${STAGING_LIBDIR} -lblkid@" Config.make
	sed -i -e "s@^PYTHON_INCLUDES =.*@PYTHON_INCLUDES = -I${STAGING_INCDIR}/python2.6@" Config.make
	sed -i -e "/BUILD_/ s@=.*@= yes@" Config.make
	sed -i -e "/OCFS2_DYNAMIC_/ s@=.*@= yes@" Config.make
	sed -i -e "s@/usr/include@${STAGING_INCDIR}@g" ${S}/ocfs2_controld/Makefile
}

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/vendor/common/o2cb.init ${D}${sysconfdir}/init.d/o2cb
	install -m 0755 ${S}/vendor/common/ocfs2.init ${D}${sysconfdir}/init.d/ocfs2
	install -d ${D}${base_libdir}/udev/rules.d
	install -m 0644 ${S}/vendor/common/51-ocfs2.rules ${D}${base_libdir}/udev/rules.d
}

pkg_postinst () {
	if test "x$D" != "x"; then
		OPT="-r $D"
	else
		OPT="-s"
	fi
	update-rc.d $OPT o2cb defaults 25
	update-rc.d $OPT ocfs2 defaults 26
}

FILES_${PN} += "${libdir} ${base_libdir}"
INSANE_SKIP_${PN} = "dev-deps"
