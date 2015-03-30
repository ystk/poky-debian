require task.inc

LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY = "1"
PR = "r0"

PACKAGES = "task-net-net-tools"

# FIXME: isc-dhcp should be added
RDEPENDS_task-net-net-tools = " \
net-tools \
"
