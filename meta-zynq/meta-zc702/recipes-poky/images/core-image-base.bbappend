FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

#
# Misc
#
SRC_URI += "file://misc"
setup_misc() {
	# Install misc files under /etc
	install -m 0644 ${WORKDIR}/misc/network/interfaces etc/network
}

# Setup misc files after package install
do_setup() {
	cd ${WORKDIR}/rootfs
	setup_misc
}

ROOTFS_POSTPROCESS_COMMAND += "do_setup;"
