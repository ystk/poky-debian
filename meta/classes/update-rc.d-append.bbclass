setup_rcdirs() {
	if [ "${INIT_PROVIDER}" = "sysvinit" ]; then
		echo "setup_rcdirs: Nothing to do"
	elif [ "${INIT_PROVIDER}" = "busybox" ]; then
		if [ -d ${IMAGE_ROOTFS}/${sysconfdir}/rcE.d ]; then
			echo "setup_rcdirs: ${sysconfdir}/rcE.d already exists"
			exit 1
		fi

		# update-rc.d install start script to rc 2,3,4,5 and stop script to rc 0,1,6.
		# Integrate the above start/stop scripts to rcS.d/rcE.d here.
		# NOTE: All scripts in rc1.d are ignored here
		install -d ${IMAGE_ROOTFS}/${sysconfdir}/rcE.d
		for rcdir in rc2.d rc3.d rc4.d rc5.d; do
			if [ -d ${IMAGE_ROOTFS}/${sysconfdir}/$rcdir ]; then
				mv ${IMAGE_ROOTFS}/${sysconfdir}/$rcdir/* ${IMAGE_ROOTFS}/${sysconfdir}/rcS.d
			fi
		done
		for rcdir in rc0.d rc6.d; do
			if [ -d ${IMAGE_ROOTFS}/${sysconfdir}/$rcdir ]; then
				mv ${IMAGE_ROOTFS}/${sysconfdir}/$rcdir/* ${IMAGE_ROOTFS}/${sysconfdir}/rcE.d
			fi
		done
		for rcdir in rc0.d rc1.d rc2.d rc3.d rc4.d rc5.d rc6.d; do
			rm -rf ${IMAGE_ROOTFS}/${sysconfdir}/$rcdir
		done
	fi
}

ROOTFS_POSTPROCESS_COMMAND += "setup_rcdirs ; "
