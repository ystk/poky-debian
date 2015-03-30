FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://config-busybox-minnowfast"

PACKAGES = "${PN}"

BUSYBOX_CONFIG = "config-busybox-minnowfast"

BUSYBOX_INITTAB_RESPAWN = " \
-/bin/sh\
"

BUSYBOX_INITTAB_ONCE = "/bin/cat /proc/uptime"

INITSCRIPT_PACKAGES = ""
