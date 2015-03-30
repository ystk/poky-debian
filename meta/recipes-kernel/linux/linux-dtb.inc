#
# linux-dtb.inc
#
# Generate kernel-device tree automatically.
# This recipe does nothing if KERNEL_DTS is null.
#

# set a path to dts in kernel source only if needed
# ex) ${S}/arch/powerpc/boot/dts/mpc8308rdb.dts
KERNEL_DTS ?= ""

KERNEL_DTB ?= "/boot/devicetree-${KERNEL_VERSION}.dtb"

KERNEL_DTC ?= "${B}/scripts/dtc/dtc"
KERNEL_DTC_FLAGS ?= ""

PACKAGES =+ "${@base_conditional('KERNEL_DTS', '', '', 'kernel-devicetree', d)}"
FILES_kernel-devicetree = "${KERNEL_DTB}"

do_compile_append() {
	if [ -n "${KERNEL_DTS}" ]; then
		# dtc must be built after do_compile
		if [ ! -x ${KERNEL_DTC} ]; then
			echo "${KERNEL_DTC} not found, cannot build dtb"
			exit 1
		fi

		${KERNEL_DTC} -I dts -O dtb ${KERNEL_DTC_FLAGS} -o ${B}/devicetree.dtb ${KERNEL_DTS}
	fi
}

do_install_append() {
	if [ -n "${KERNEL_DTS}" ]; then
		install -m 0644 ${B}/devicetree.dtb ${D}/${KERNEL_DTB}
	fi
}

KERNEL_DTB_BASE_NAME ?= "devicetree-${PV}-${PR}-${MACHINE}-${DATETIME}"

do_deploy_append() {
	if [ -n "${KERNEL_DTS}" ]; then
		install -m 0644 ${B}/devicetree.dtb ${DEPLOYDIR}/${KERNEL_DTB_BASE_NAME}.dtb
		KERNEL_DTB_SYMLINK_NAME=$(basename ${KERNEL_DTB})
		rm -f ${DEPLOYDIR}/$KERNEL_DTB_SYMLINK_NAME
		ln -sf ${KERNEL_DTB_BASE_NAME}.dtb ${DEPLOYDIR}/$KERNEL_DTB_SYMLINK_NAME
	fi
}
