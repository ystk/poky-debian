#
# debian-squeeze-linux-vmlinuz.bbclass
#
# Automatically rename /boot/bzImage-* to /boot/vmlinuz-*
# after installing kernel-image package.
# This file is not inherited by default and must be inherited
# by .bbappend (after inherit kernel).
#

pkg_postinst_kernel-image () {
if [ ! -e "$D/lib/modules/${KERNEL_VERSION}" ]; then
	mkdir -p $D/lib/modules/${KERNEL_VERSION}
fi
if [ -n "$D" ]; then
	${HOST_PREFIX}depmod -A -b $D -F ${STAGING_KERNEL_DIR}/System.map-${KERNEL_VERSION} ${KERNEL_VERSION}
else
	depmod -a
fi

# rename bzImage to vmlinuz
if [ "${KERNEL_IMAGETYPE}" = "bzImage" ]; then
	if [ -e $D/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ]; then
		mv $D/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} \
			$D/${KERNEL_IMAGEDEST}/vmlinuz-${KERNEL_VERSION}
	fi
fi
}
