# debian-squeeze
#

DESCRIPTION = "tools for generating an initramfs"
SECTION = "utils"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://debian/README;md5=8275b9b1ee3b6b460ccf3de9e1b20639"

inherit debian-squeeze
inherit autotools
do_install() {
	install -d ${D}/var/lib/initramfs-tools
	install -d ${D}/usr/sbin
        install -m 0755 ${S}/mkinitramfs-kpkg ${D}/usr/sbin
	install -m 0755 ${WORKDIR}/initramfs-tools-git/mkinitramfs ${D}/usr/sbin
	install -m 0755 ${WORKDIR}/initramfs-tools-git/update-initramfs ${D}/usr/sbin

	install -d ${D}/usr/share/bug/initramfs-tools
	install -d ${S}debian/script/ ${D}/usr/share/bug/initramfs-tools

	install -d ${D}/usr/share/man/man8
	install -m 0755 ${WORKDIR}/initramfs-tools-git/mkinitramfs-kpkg.8 ${D}/usr/share/man/man8
	install -m 0755 ${WORKDIR}/initramfs-tools-git/mkinitramfs.8 ${D}/usr/share/man/man8
	install -m 0755 ${WORKDIR}/initramfs-tools-git/update-initramfs.8 ${D}/usr/share/man/man8
	install -m 0755 ${WORKDIR}/initramfs-tools-git/lsinitramfs.8 ${D}/usr/share/man/man8
	install -m 0755 ${WORKDIR}/initramfs-tools-git/initramfs-tools.8 ${D}/usr/share/man/man8

	install -d ${D}/usr/share/man/man5
        install -m 0755 ${WORKDIR}/initramfs-tools-git/update-initramfs.conf.5 ${D}/usr/share/man/man5
        install -m 0755 ${WORKDIR}/initramfs-tools-git/initramfs.conf.5 ${D}/usr/share/man/man5

	install -d ${D}/usr/share/initramfs-tools
	install -m 0755 ${WORKDIR}/initramfs-tools-git/conf/modules ${D}/usr/share/initramfs-tools
	install -m 0755 ${WORKDIR}/initramfs-tools-git/init ${D}/usr/share/initramfs-tools
	
	install -d ${D}/usr/share/initramfs-tools/hooks
	install -m 0755 ${WORKDIR}/initramfs-tools-git/hooks/klibc ${D}/usr/share/initramfs-tools/hooks
	install -m 0755 ${WORKDIR}/initramfs-tools-git/hooks/keymap ${D}/usr/share/initramfs-tools/hooks
	install -m 0755 ${WORKDIR}/initramfs-tools-git/hooks/busybox ${D}/usr/share/initramfs-tools/hooks
	install -m 0755 ${WORKDIR}/initramfs-tools-git/hooks/thermal ${D}/usr/share/initramfs-tools/hooks

	install -d ${D}/usr/share/initramfs-tools/scripts/init-top
	install -m 0755 ${WORKDIR}/initramfs-tools-git/scripts/init-top/keymap ${D}/usr/share/initramfs-tools/scripts/init-top
	install -m 0755 ${WORKDIR}/initramfs-tools-git/scripts/init-top/all_generic_ide ${D}/usr/share/initramfs-tools/scripts/init-top
	install -m 0755 ${WORKDIR}/initramfs-tools-git/scripts/init-top/blacklist ${D}/usr/share/initramfs-tools/scripts/init-top
	install -m 0755 ${WORKDIR}/initramfs-tools-git/scripts/nfs ${D}/usr/share/initramfs-tools/scripts
	install -m 0755 ${WORKDIR}/initramfs-tools-git/scripts/local ${D}/usr/share/initramfs-tools/scripts/
	install -m 0755 ${WORKDIR}/initramfs-tools-git/scripts/functions ${D}/usr/share/initramfs-tools/scripts/
	install -d ${D}/usr/share/initramfs-tools/scripts/local-premount
	install -m 0755 ${WORKDIR}/initramfs-tools-git/scripts/local-premount/resume ${D}/usr/share/initramfs-tools/scripts/local-premount
	install -m 0755 ${WORKDIR}/initramfs-tools-git/hook-functions ${D}/usr/share/initramfs-tools

	install -d ${D}/usr/share/lintian/overrides
	install -m 0755 ${WORKDIR}/initramfs-tools-git/debian/lintian/initramfs-tools ${D}/usr/share/lintian/overrides 

	install -d ${D}/usr/bin/
	install -m 0755 ${WORKDIR}/initramfs-tools-git/lsinitramfs ${D}/usr/bin/

	install -d ${D}/etc/kernel/postinst.d/
	install -m 0755 ${WORKDIR}/initramfs-tools-git/kernel/postinst.d/initramfs-tools ${D}/etc/kernel/postinst.d/
        install -d ${D}/etc/kernel/postrm.d/
	install -m 0755 ${WORKDIR}/initramfs-tools-git/kernel/postrm.d/initramfs-tools ${D}/etc/kernel/postrm.d	
	install -d ${D}/etc/bash_completion.d
	install -m 0755 ${WORKDIR}/initramfs-tools-git/bash_completion.d/initramfs-tools ${D}/etc/bash_completion.d

	install -d ${D}/etc/initramfs-tools
	install -m 0755 ${WORKDIR}/initramfs-tools-git/conf/update-initramfs.conf ${D}/etc/initramfs-tools
	install -d ${D}/etc/initramfs-tools/hooks
	install -d ${D}/etc/initramfs-tools/scripts
	install -d ${D}/etc/initramfs-tools/scripts/init-top
	install -d ${D}/etc/initramfs-tools/scripts/init-bottom
	install -d ${D}/etc/initramfs-tools/scripts/nfs-top
	install -d ${D}/etc/initramfs-tools/scripts/local-top
	install -d ${D}/etc/initramfs-tools/scripts/nfs-premount
	install -d ${D}/etc/initramfs-tools/scripts/local/bottom
	install -d ${D}/etc/initramfs-tools/scripts/init-premount
	install -d ${D}/etc/initramfs-tools/scripts/local-premount
	install -d ${D}/etc/initramfs-tools/scripts/nfs-bottom
	install -m 0755 ${WORKDIR}/initramfs-tools-git/conf/initramfs.conf ${D}/etc/initramfs-tools/


}
