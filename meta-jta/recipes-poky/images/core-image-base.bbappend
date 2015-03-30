# additional device files
#IMAGE_DEVICE_TABLES += ""

ROOTFS_POSTPROCESS_COMMAND += "jta_setup ; "

jta_setup() {
	mkdir -p ${IMAGE_ROOTFS}/var/log
	echo "THIS IS A DUMMY /var/log/messages" > ${IMAGE_ROOTFS}/var/log/messages
}
