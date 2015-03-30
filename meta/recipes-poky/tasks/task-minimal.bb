#
# Task for minimal bootable image.
# This task DOES NOT includes any pre-defined non-essential packages.
#

require task.inc

DESCRIPTION = "Task for minimal bootable image"

LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY = "1"
PR = "r0"

PACKAGES = "task-minimal"

# Required to automatically generate module information
DEPENDS += "virtual/kernel"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""

RDEPENDS_task-minimal = " \
kernel-modules \
busybox \
extra-minimal \
${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
"
