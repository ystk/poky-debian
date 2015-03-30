#
# task for initramfs image
#

require task.inc

DESCRIPTION = "Task for initramfs image"

LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY = "1"
PR = "r0"

PACKAGES = "task-initramfs"

# kernel-modules package (ALL modules) should not be included.
# Partial modules are installed automatically by mkinitramfs.
RDEPENDS_task-initramfs = " \
busybox-initramfs \
udev-initramfs \
klibc-initramfs \
util-linux-blkid \
util-linux-libblkid \
util-linux-libuuid \
"
