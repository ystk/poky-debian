FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# use a local configuration file
SRC_URI += "file://minnow.defconfig"
LINUX_CONF = "${WORKDIR}/minnow.defconfig"
