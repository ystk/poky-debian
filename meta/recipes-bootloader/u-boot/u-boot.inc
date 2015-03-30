DESCRIPTION = "U-boot bootloader"
HOMEPAGE = "http://u-boot.sf.net"
SECTION = "bootloaders"
PROVIDES = "virtual/bootloader"

inherit deploy

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"

python () {
	if not d.getVar("UBOOT_MACHINE", True):
		PN = d.getVar("PN", True)
		FILE = os.path.basename(d.getVar("FILE", True))
		bb.debug(1, "To build %s, see %s for instructions on \
			     setting up your machine config" % (PN, FILE))
		raise bb.parse.SkipPackage("because UBOOT_MACHINE is not set")
}

UBOOT_IMAGE ?= "u-boot-${MACHINE}-${PV}-${PR}.bin"
UBOOT_BINARY ?= "u-boot.bin"
UBOOT_SYMLINK ?= "u-boot-${MACHINE}.bin"
UBOOT_MAKE_TARGET ?= "all"

do_compile () {
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS
	oe_runmake ${UBOOT_MACHINE}
	oe_runmake ${UBOOT_MAKE_TARGET}
}

do_install () {
    install -d ${D}/boot
    install ${S}/${UBOOT_BINARY} ${D}/boot/${UBOOT_IMAGE}
    ln -sf ${UBOOT_IMAGE} ${D}/boot/${UBOOT_BINARY}
}

FILES_${PN} = "/boot"

do_deploy () {
	install ${S}/u-boot.bin ${DEPLOYDIR}/${UBOOT_IMAGE}

	cd ${DEPLOYDIR}
	rm -f ${UBOOT_SYMLINK}
	ln -sf ${UBOOT_IMAGE} ${UBOOT_SYMLINK}
}
addtask deploy before do_build after do_compile
