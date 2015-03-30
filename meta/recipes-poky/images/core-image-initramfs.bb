inherit image

LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

PR = "r0"

IMAGE_INSTALL = "task-initramfs"

IMAGE_DEVICE_TABLES = "files/device-table/empty.txt"

IMAGE_LINGUAS = ""

ROOTFS_POSTPROCESS_COMMAND += "remove_packaging_data_files ; "

SRC_URI = ""

IMAGE_FSTYPES = "tar.gz"
