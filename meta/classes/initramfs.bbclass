INITRAMFS_NAME ?= "core-image-initramfs"

DEPENDS += "virtual/kernel"

do_rootfs[depends] += "initramfs-tools-native:do_populate_sysroot"
do_rootfs[depends] += "${INITRAMFS_NAME}:do_rootfs"

KERNEL_VERSION = "${@base_read_file('${STAGING_KERNEL_DIR}/kernel-abiversion')}"

INITRAMFS_UNPACKDIR = "${WORKDIR}/initramfs"
INITRAMFS_ARCHIVE = \
	"${DEPLOY_DIR_IMAGE}/${INITRAMFS_NAME}-${MACHINE}.tar.gz"

ROOTFS_POSTPROCESS_COMMAND += "generate_initramfs ; "

generate_initramfs() {
	echo "generating initramfs..."

	if [ ! -f ${INITRAMFS_ARCHIVE} ]; then
		echo "initramfs archive ${INITRAMFS_ARCHIVE} not found"
		exit 1
	fi

	rm -rf ${INITRAMFS_UNPACKDIR}
	mkdir ${INITRAMFS_UNPACKDIR}
	tar xzf ${INITRAMFS_ARCHIVE} -C ${INITRAMFS_UNPACKDIR}

	# generate initramfs by native mkinitramfs command
	install -d ${IMAGE_ROOTFS}/boot
	mkinitramfs \
		-o ${IMAGE_ROOTFS}/boot/initrd-${KERNEL_VERSION} \
		-R ${IMAGE_ROOTFS} \
		-V ${KERNEL_VERSION} \
		-D ${INITRAMFS_UNPACKDIR} \
}
