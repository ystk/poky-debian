FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# use a local configuration file
SRC_URI += "file://pandaboard.defconfig"
LINUX_CONF = "${WORKDIR}/pandaboard.defconfig"
