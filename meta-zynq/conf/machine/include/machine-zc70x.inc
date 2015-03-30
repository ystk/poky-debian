KERNEL_IMAGETYPE = "uImage"

# Set default load address.
# Override with KERNEL_EXTRA_ARGS_<board> += "..." in machine file if required
KERNEL_EXTRA_ARGS += "UIMAGE_LOADADDR=0x8000"
#UBOOT_ENTRYPOINT ?= "0x8000"

SERIAL_CONSOLE = "115200 ttyPS0"

#MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "grub"

IMAGE_FSTYPES ?= "tar.gz"
