#
# Automatically add packages according to DEBIAN_SQUEEZE_FEATURES
# Copyright (C) 2012 TOSHIBA CORPORATION
#

require task.inc

LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

DESCRIPTION = "Automatically add packages according to DEBIAN_SQUEEZE_FEATURES"

PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY = "1"
PR = "r0"

PACKAGES = "task-base-debian-squeeze"

MACHINE_FEATURES += " \
${@base_contains('DEBIAN_SQUEEZE_FEATURES', 'keyboard', 'keyboard', '', d)} \
"

DEBIAN_SQUEEZE_EXTRA_RDEPENDS ?= ""

RDEPENDS_task-base-debian-squeeze = " \
${@base_contains('DEBIAN_SQUEEZE_FEATURES', 'net', 'task-net-${NET_PROVIDER}', '', d)} \
${DEBIAN_SQUEEZE_EXTRA_RDEPENDS} \
"
