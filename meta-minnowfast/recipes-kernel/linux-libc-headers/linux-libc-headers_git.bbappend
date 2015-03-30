FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# use a local configuration file
SRC_URI += "file://minnowfast.defconfig"
LINUX_CONF = "${WORKDIR}/minnowfast.defconfig"
