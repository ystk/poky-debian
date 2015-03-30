#
# install directories shared by all recipes
#

PASSWD_DIR ?= "${sysconfdir}/passwd.d"
GROUP_DIR ?= "${sysconfdir}/group.d"

#
# finalizer done after do_rootfs (all packages are installed)
#

PASSWD_DEFAULT_SHELL ?= \
"${@base_conditional('SHELL_PROVIDER', 'busybox', '/bin/sh', '/bin/${SHELL_PROVIDER}', d)}"

setup_passwd() {
	if [ -d ${IMAGE_ROOTFS}/${PASSWD_DIR} ]; then
		cat ${IMAGE_ROOTFS}/${PASSWD_DIR}/* >> ${IMAGE_ROOTFS}/${sysconfdir}/passwd
		rm -rf ${IMAGE_ROOTFS}/${PASSWD_DIR}
	fi
	if [ -d ${IMAGE_ROOTFS}/${GROUP_DIR} ]; then
		cat ${IMAGE_ROOTFS}/${GROUP_DIR}/* >> ${IMAGE_ROOTFS}/${sysconfdir}/group
		rm -rf ${IMAGE_ROOTFS}/${GROUP_DIR}
	fi

	sed -i "s|defaultshell|${PASSWD_DEFAULT_SHELL}|g" ${IMAGE_ROOTFS}/${sysconfdir}/passwd
}

ROOTFS_POSTPROCESS_COMMAND += "setup_passwd ; "
