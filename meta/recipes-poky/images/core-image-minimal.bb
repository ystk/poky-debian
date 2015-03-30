inherit core-image

LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

IMAGE_INSTALL = "task-minimal"

IMAGE_DEVICE_TABLES = "files/device-table/minimal.txt"

IMAGE_LINGUAS = " "

IMAGE_ROOTFS_SIZE ?= "8192"

ROOTFS_POSTPROCESS_COMMAND += "remove_packaging_data_files ; "

SRC_URI = ""
