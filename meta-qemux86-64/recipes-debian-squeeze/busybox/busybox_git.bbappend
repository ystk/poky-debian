FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

BUSYBOX_INITTAB_RESPAWN = " \
${base_sbindir}/getty ${SERIAL_CONSOLE}\
"

