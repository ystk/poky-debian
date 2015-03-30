DESCRYPTION = "converts RPC program numbers into universal addresses"
SECTION = "net"
LICENSE = "GPL-3"
LIC_FILES_CHKSUM = "file://COPYING;md5=b46486e4c4a416602693a711bb5bfa39"

inherit autotools debian-squeeze update-rc.d

INITSCRIPT_NAME = "rpcbind"
INITSCRIPT_PARAMS = "defaults"
DEPENDS += " libtirpc tcp-wrappers"

EXTRA_OECONF += " --enable-warmstarts --enable-libwrap --bindir=${base_sbindir}"     

do_compile_prepend() {
	sed -i -e "/^CFLAGS =/ s:$: -I${STAGING_INCDIR}/tirpc:" \
			$(find -name Makefile)
}

do_install_append() {
	sed -i -e "s/^OPTIONS=.*/OPTIONS=\"-w -i\"/" ${S}/debian/init.d
        sed -i -e '25 i\if [ ! -f /tmp/rpcbind.xdr ]\nthen\n    touch /tmp/rpcbind.xdr\nfi\nif [ ! -f /tmp/portmap.xdr ]\nthen\n            touch /tmp/portmap.xdr\nfi' ${S}/debian/init.d
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/debian/init.d ${D}${sysconfdir}/init.d/rpcbind
}

FILES_${PN} += " ${sysconfdir}/*"
