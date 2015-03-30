FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://defconfig_with_taskset"

BUSYBOX_CONFIG = "defconfig_with_taskset"

BUSYBOX_INITTAB_RESPAWN = " \
${base_sbindir}/getty 38400 tty1:\
${base_sbindir}/getty ${SERIAL_CONSOLE}\
"

BUSYBOX_INITTAB_ONCE = "${sysconfdir}/init.d/load-keys"
