FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_setup() {
	cd ${WORKDIR}/rootfs
	install -m 0644 ${WORKDIR}/misc/network/interfaces etc/network
}

ROOTFS_POSTPROCESS_COMMAND += "do_setup;"
