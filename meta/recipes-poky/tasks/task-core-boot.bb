#
# Task for bootable image
# Copyright (C) 2012 TOSHIBA CORPORATION
#

require task.inc

LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

DESCRIPTION = "Task for bootable image"

PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY = "1"
PR = "r0"

# Automatically generate module information
DEPENDS += "virtual/kernel"

# Machine configuration can override the following package lists
MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""
MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS ?= ""

PACKAGES = " \
task-core-boot \
task-core-boot-dbg \
task-core-boot-dev \
"

RDEPENDS_task-core-boot = " \
${TASK_BOOT_PROVIDERS} \
debian-squeeze-files \
${@base_contains('MACHINE_FEATURES', 'keyboard', 'keymaps', '', d)} \
${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
"

RRECOMMENDS_task-core-boot = "${MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS}"

#
# essential providers
#

# busybox, sysvinit
INIT_PROVIDER ?= "busybox"
# busybox, util-linux-agetty
GETTY_PROVIDER ?= "busybox"
# busybox, tinylogin, shadow
LOGIN_PROVIDER ?= "busybox"
# busybox, bash
SHELL_PROVIDER ?= "busybox"
# busybox, util-linux
MOUNT_PROVIDER ?= "busybox"

TASK_BOOT_PROVIDERS = " \
${INIT_PROVIDER} \
${GETTY_PROVIDER} \
${LOGIN_PROVIDER} \
${SHELL_PROVIDER} \
${MOUNT_PROVIDER} \
"
