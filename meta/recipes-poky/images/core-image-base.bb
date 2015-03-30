inherit core-image
inherit update-rc.d-append passwd set-perms

LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

IMAGE_INSTALL += "task-base-debian-squeeze"

IMAGE_DEVICE_TABLES = " \
files/device-table/minimal.txt \
${@base_contains('DEBIAN_SQUEEZE_FEATURES', 'devpts', 'files/device-table/pts.txt', '', d)} \
"

IMAGE_LINGUAS = ""

ROOTFS_POSTPROCESS_COMMAND += " \
${@base_contains('IMAGE_FEATURES', 'package-management', '', 'remove_packaging_data_files ; ',d)} \
set_perms ; \
"

SRC_URI = ""
