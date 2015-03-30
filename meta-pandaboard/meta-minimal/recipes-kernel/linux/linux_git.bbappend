FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# rename bzImage to vmlinuz
inherit debian-squeeze-linux-vmlinuz

# use a local configuration file
SRC_URI += "file://pandaboard.defconfig"
LINUX_CONF = "${WORKDIR}/pandaboard.defconfig"

