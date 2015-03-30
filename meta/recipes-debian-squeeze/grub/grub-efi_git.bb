require grub_${PV}.bb

FILESEXTRAPATHS_prepend := "${THISDIR}/grub-${PV}:"

# This is a temporal patch to fix kernel console problem
# load_video that calls efi_gop & efi_uga need to be called
# if grub is booted from EFI and GRUB_GFXPAYLOAD_LINUX not set
SRC_URI += "file://10_linux.patch"

# specifiy EFI format instead of "pc"
EXTRA_OECONF += "--with-platform=efi"

RCONFLICTS_${PN} = "grub"

FILES_${PN} += "${libdir}"
