#
# debian-squeeze
#
# busybox only for initramfs
#

FILESPATH = "\
${FILE_DIRNAME}/files:\
${FILE_DIRNAME}/busybox-${PV}:\
${FILE_DIRNAME}/${PN}-${PV}\
"

# define default value before requiring main recipe
BUSYBOX_CONFIG ?= "defconfig.initramfs"

require busybox_${PV}.bb
DEBIAN_SQUEEZE_SRCPKG_NAME = "busybox"

PR = "r0"

#
# only busybox binary and symlinks are needed
#

PACKAGES = "${PN} ${PN}-dbg"
INITSCRIPT_PACKAGES = ""

do_install_append() {
	mv ${D}${sysconfdir}/busybox.links ${D}
	rm -rf ${D}${sysconfdir}/*
	mv ${D}/busybox.links ${D}${sysconfdir}

	rm -rf ${D}/var
}
