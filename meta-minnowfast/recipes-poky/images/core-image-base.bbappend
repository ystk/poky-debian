# additional device files
IMAGE_DEVICE_TABLES += " \
files/device-table/mmcblk0.txt \
files/device-table/ttyPCH.txt \
files/device-table/pts.txt \
"

# Setup misc files after package install
do_setup() {
	rm -f ${D}/../rootfs/${sysconfdir}/init.d/busybox-cron 
	rm -f ${D}/../rootfs/${sysconfdir}/init.d/busybox-udhcpc
	rm -f ${D}/../rootfs/${sysconfdir}/init.d/hwclock.sh
	rm -f ${D}/../rootfs/${sysconfdir}/init.d/load-modules
	rm -f ${D}/../rootfs/${sysconfdir}/init.d/mountkernfs
	rm -f ${D}/../rootfs/${sysconfdir}/init.d/networking
	rm -f ${D}/../rootfs/${sysconfdir}/init.d/remount-rootfs

	rm -f ${D}/../rootfs/${sysconfdir}/rcS.d/S01mountkernfs
	rm -f ${D}/../rootfs/${sysconfdir}/rcS.d/S03remount-rootfs
	rm -f ${D}/../rootfs/${sysconfdir}/rcS.d/S06load-modules
	rm -f ${D}/../rootfs/${sysconfdir}/rcS.d/S20busybox-udhcpc
	rm -f ${D}/../rootfs/${sysconfdir}/rcS.d/S20networking

	rm -f ${D}/../rootfs/${sysconfdir}/rcE.d/K20busybox-udhcpc
	rm -f ${D}/../rootfs/${sysconfdir}/rcE.d/K70networking

	rm -R -f ${D}/../rootfs/${sysconfdir}/udhcpc.d/
	ln -s /sbin/init ${D}/../rootfs/
	sed -i 's/echo \"Mounting filesystem...\"//g' ${D}/../rootfs/${sysconfdir}/init.d/mountall
}

ROOTFS_POSTPROCESS_COMMAND += "do_setup;"
