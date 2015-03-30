#
# debian-squeeze
#

DESCRIPTION = "VMcore extraction tool"
SECTION = "devel"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

inherit debian-squeeze autotools update-rc.d

INITSCRIPT_NAME = "kdump-tools"
INITSCRIPT_PARAMS = "defaults"

DEPENDS += "zlib elfutils"
# kdump-config depends on bash, kdump-tools initscript depends on lsb-base
RDEPENDS_${PN} += "lsb-base bash file"
SRC_URI += " \
	   file://fix_arch_makefile.patch \
	   file://fix-KEXEC-variable.patch \
	   "

export host_arch=${HOST_ARCH}

CFLAGS += " -I${STAGING_INCDIR}"

do_compile_prepend () {
        sed -i -e "s@^CC\s*=.*@CC = ${CC} ${CFLAGS}@" Makefile
}

# implement kdump-tools
do_install_append() {
        install -d ${D}${sbindir}
        install -m 0755 ${S}/debian/kdump-config ${D}${sbindir}
        sed -i -e "/\/lib\/init\/vars.sh/ s@^@#@" ${D}${sbindir}/kdump-config

        install -d ${D}${sysconfdir}/default
        install -m 0644 ${S}/debian/kdump-tools.default ${D}${sysconfdir}/default/kdump-tools
        sed -i -e "s@^USE_KDUMP=.*@USE_KDUMP=1@" ${D}${sysconfdir}/default/kdump-tools

        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${S}/debian/kdump-tools.init ${D}${sysconfdir}/init.d/kdump-tools
        sed -i -e "/\/lib\/init\/vars.sh/ s@^@#@" ${D}${sysconfdir}/init.d/kdump-tools
}
