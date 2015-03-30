#
# debian-squeeze
#

DESCRIPTION = "User space tools for security auditing"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
SECTION = "admin"

PR = "r0"

inherit debian-squeeze
inherit autotools gettext update-rc.d

INITSCRIPT_NAME = "auditd"
INITSCRIPT_PARAMS = "defaults"

DEPENDS = "audit-native openldap python sysvinit"
PYTHONVER = "2.6"

LIBTOOL = "${HOST_SYS}-libtool"
EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'"
EXTRA_OECONF = " \
--enable-gssapi-krb5 \
--enable-shared=audit \
"
# /etc/rc.d/init.d/auditd expects /sbin/auditctl, etc.
sbindir = "/sbin"

SRC_URI += " \
file://fix-src.patch;apply=no \
file://fix-make.patch;apply=no \
file://use-native-binary.patch \
file://auditd.init;apply=no \
"

do_configure_prepend() {
	export setlib="${STAGING_LIBDIR}"
	export INTLTOOL_PERL="${STAGING_BINDIR_NATIVE}/perl-native/perl"
}

do_compile() {

        sed -i -e "s:^INCLUDES = .*:INCLUDES = -I. -I\$(top_builddir) -I\${top_srcdir}/lib -I${STAGING_INCDIR}/python${PYTHONVER}:" swig/Makefile
        sed -i -e "s:-I/usr/include/python\S*:-I${STAGING_INCDIR}/python${PYTHONVER}:" bindings/python/Makefile
	# Install init script to /etc/init.d
	sed -i -e "s@rc\.d/init\.d@init\.d@g" $(grep -nr "rc.d/init.d" . | cut -f1 -d: | sort | uniq)
	install -m 0755 ${WORKDIR}/auditd.init ${S}/init.d/

	# oom_adj is deprecated on newer kernel
	sed -i -e "s:oom_adj:oom_score_adj:g" ${S}/src/auditd.c

	export setlib="${STAGING_LIBDIR}"
	cd ${S}
	automake
	sed -i 's/CFLAGS\ =\ \-isystem/CFLAGS\ =\ \-I/' $(find $pwd -name "Makefile")
	sed -i 's/CPPFLAGS\ =\ \-isystem/CPPFLAGS\ =\ \-I/' $(find $pwd -name "Makefile")

	oe_runmake
}


PACKAGES += "${PN}-python-audit"

ALLOW_EMPTY_${PN} = "1"
RDEPENDS_${PN} += "${PN}-python-audit lsb"

FILES_${PN}-python-audit = "${libdir}/python${PYTHONVER}/* ${datadir}/*"
RPROVIDES_${PN}-python-audit = "${PN}-python-audit"

FILES_${PN}-dbg += "${libdir}/python${PYTHONVER}/site-packages/.debug"
