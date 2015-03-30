#
# debian-squeeze-files
#
# Includes core scripts, configs and data for Debian system.
# All files are distributed under MIT license and
# can be overwritten if you want.
#
# Copyright (C) 2014 TOSHIBA CORPORATION
#

LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

PR = "r0"

SRC_URI = ""

S = "${WORKDIR}"

do_configure() {
	:
}

do_compile() {
	:
}

PKG_SRC_CATEGORY ?= "poky-debian"

#
# fstab
#

DEBIAN_SQUEEZE_FILES_FSTAB ?= "fstab.base"

DEBIAN_SQUEEZE_FILES_FSTAB_DEVSHM ?= "tmpfs           /dev/shm      tmpfs  defaults  0      0"
DEBIAN_SQUEEZE_FILES_FSTAB_DEVPTS ?= "devpts          /dev/pts      devpts defaults  0      0"

SRC_URI += "file://${DEBIAN_SQUEEZE_FILES_FSTAB}"

install_fstab() {
	echo "install_fstab"

	install -d ${D}/${sysconfdir}
	install -m 0644 ${WORKDIR}/${DEBIAN_SQUEEZE_FILES_FSTAB} ${D}/${sysconfdir}/fstab

	if [ "${@base_contains('DEBIAN_SQUEEZE_FEATURES', 'devshm', 1, 0, d)}" = "1" ]; then
		echo "${DEBIAN_SQUEEZE_FILES_FSTAB_DEVSHM}" >> ${D}/${sysconfdir}/fstab
	fi
	if [ "${@base_contains('DEBIAN_SQUEEZE_FEATURES', 'devpts', 1, 0, d)}" = "1" ]; then
		echo "${DEBIAN_SQUEEZE_FILES_FSTAB_DEVPTS}" >> ${D}/${sysconfdir}/fstab
	fi

	# Essential directories
	install -d ${D}/proc ${D}/sys
}

FILES_${PN} += "/proc /sys"

#
# initscripts
#

inherit update-rc.d

SRC_URI += " \
file://mountkernfs \
file://fsck \
file://remount-rootfs \
file://load-modules \
file://networking \
file://umountall \
"

INITSCRIPT_PACKAGES = " \
${PN}-mountkernfs \
${@base_contains('DEBIAN_SQUEEZE_FEATURES', 'fsck', '${PN}-fsck', '', d)} \
${PN}-remount-rootfs \
${PN}-mountall \
${PN}-load-modules \
${@base_contains('DEBIAN_SQUEEZE_FEATURES', 'net', '${PN}-networking', '', d)} \
${PN}-umountall \
"
PACKAGES = "${INITSCRIPT_PACKAGES} ${PN}"
FILES_${PN}-mountkernfs = "${sysconfdir}/init.d/mountkernfs"
FILES_${PN}-fsck = "${sysconfdir}/init.d/fsck"
FILES_${PN}-remount-rootfs = "${sysconfdir}/init.d/remount-rootfs"
FILES_${PN}-mountall = "${sysconfdir}/init.d/mountall"
FILES_${PN}-load-modules = "${sysconfdir}/init.d/load-modules"
FILES_${PN}-networking = "${sysconfdir}/init.d/networking"
FILES_${PN}-umountall = "${sysconfdir}/init.d/umountall"
RDEPENDS_${PN} += "${INITSCRIPT_PACKAGES}"

# FIXME: Add other runlevels for sysvinit based system
INITSCRIPT_NAME_${PN}-mountkernfs = "mountkernfs"
INITSCRIPT_PARAMS_${PN}-mountkernfs = "start 01 S ."
INITSCRIPT_NAME_${PN}-fsck = "fsck"
INITSCRIPT_PARAMS_${PN}-fsck = "start 02 S ."
INITSCRIPT_NAME_${PN}-remount-rootfs = "remount-rootfs"
INITSCRIPT_PARAMS_${PN}-remount-rootfs = "start 03 S ."
# S04udev may be added
INITSCRIPT_NAME_${PN}-mountall = "mountall"
INITSCRIPT_PARAMS_${PN}-mountall = "start 05 S ."
INITSCRIPT_NAME_${PN}-load-modules = "load-modules"
INITSCRIPT_PARAMS_${PN}-load-modules = "start 06 S ."
INITSCRIPT_NAME_${PN}-networking = "networking"
INITSCRIPT_PARAMS_${PN}-networking = "start 20 S . stop 70 0 ."
INITSCRIPT_NAME_${PN}-umountall = "umountall"
INITSCRIPT_PARAMS_${PN}-umountall = "stop 90 0 6 ."

install_initscripts() {
	echo "install_initscripts"

	# Generate mountall
	cat <<EOF > ${WORKDIR}/mountall
#!/bin/sh

### BEGIN INIT INFO
# Provides:          mountall
# Required-Start:    mountkernfs
# Required-Stop:
# Should-Start:      udev bootlogd
# Should-Stop:
# Default-Start:     S
# Default-Stop:
# Short-Description: Mount all filesystems.
# Description:
### END INIT INFO

# Mount according to fstab
echo "Mounting filesystem..."
EOF
	if [ "${@base_contains('DEBIAN_SQUEEZE_FEATURES', 'devshm', 1, 0, d)}" = "1" ]; then
		echo "if [ ! -d /dev/shm ]; then mkdir -p /dev/shm; fi" >> ${WORKDIR}/mountall
	fi
	if [ "${@base_contains('DEBIAN_SQUEEZE_FEATURES', 'devpts', 1, 0, d)}" = "1" ]; then
		echo "if [ ! -d /dev/pts ]; then mkdir -p /dev/pts; fi" >> ${WORKDIR}/mountall
	fi
	echo "mount -a" >> ${WORKDIR}/mountall

	install -d ${D}/${sysconfdir}/init.d
	for script in $(echo ${INITSCRIPT_PACKAGES} | sed "s|${PN}-||g"); do
		install -m 0755 $script ${D}/${sysconfdir}/init.d
	done
}

#
# Network scripts
#

DEBIAN_SQUEEZE_FILES_INTERFACES ?= "interfaces.default"

SRC_URI += "file://${DEBIAN_SQUEEZE_FILES_INTERFACES}"

install_network_scripts() {
	[ "${@base_contains('DEBIAN_SQUEEZE_FEATURES', 'net', 1, 0, d)}" = "1" ] || return 0

	echo "install_network_scripts"

	# Essential directories
	install -d ${D}/${sysconfdir}/network
	for dir in if-down.d if-post-down.d if-pre-up.d if-up.d; do
		install -d ${D}/${sysconfdir}/network/$dir
	done
	install -d ${D}/var
	install -d ${D}/var/run

	install -m 0644 ${WORKDIR}/${DEBIAN_SQUEEZE_FILES_INTERFACES} ${D}/${sysconfdir}/network/interfaces
}

FILES_${PN} += "/var"

#
# User information
#

inherit passwd

DEBIAN_SQUEEZE_FILES_PASSWD ?= "passwd.base"
DEBIAN_SQUEEZE_FILES_GROUP ?= "group.base"

SRC_URI += " \
file://${DEBIAN_SQUEEZE_FILES_PASSWD} \
file://${DEBIAN_SQUEEZE_FILES_GROUP} \
"

install_user_info() {
	echo "install_user_info"

	install -d ${D}/${PASSWD_DIR} ${D}/${GROUP_DIR}
	install -m 0644 ${WORKDIR}/${DEBIAN_SQUEEZE_FILES_PASSWD} ${D}/${PASSWD_DIR}/00.${PN}
	install -m 0644 ${WORKDIR}/${DEBIAN_SQUEEZE_FILES_GROUP} ${D}/${GROUP_DIR}/00.${PN}

	# Essential directories
	install -d ${D}/root
}

FILES_${PN} += "/root"

#
# Release information
#

RELEASE_NAME ?= "debian-squeeze"
RELEASE_VERSION ?= "${DEBIAN_SQUEEZE_VERSION}"

install_debian_squeeze_release_info() {
	[ "${@base_contains('DEBIAN_SQUEEZE_FEATURES', 'release-info', 1, 0, d)}" = "1" ] || return 0

	echo "install_debian_squeeze_release_info"

	install -d ${D}/${sysconfdir}
	echo "Installing ${sysconfdir}/debian_squeeze_name..."
	echo "${RELEASE_NAME}" > ${D}/${sysconfdir}/debian_squeeze_name
	echo "Installing ${sysconfdir}/debian_squeeze_version..."
	echo "${RELEASE_VERSION}" > ${D}/${sysconfdir}/debian_squeeze_version
}

#
# Installation summary
#

do_install() {
	install_fstab
	install_initscripts
	install_network_scripts
	install_user_info
	install_debian_squeeze_release_info
}
