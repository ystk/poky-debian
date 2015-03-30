FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

IMAGE_DEVICE_TABLES += " \
files/device-table/sda.txt \
files/device-table/tty.txt \
files/device-table/ttyS.txt \
"

SRC_URI += "file://runqemu.sh"

copy_runqemu() {
	install -m 0755 ${WORKDIR}/runqemu.sh ${DEPLOY_DIR_IMAGE}
}

ROOTFS_POSTPROCESS_COMMAND += "copy_runqemu;"
