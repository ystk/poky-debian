FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# rename bzImage to vmlinuz
inherit debian-squeeze-linux-vmlinuz

# use a local configuration file
SRC_URI += "file://minnowfast.defconfig"
LINUX_CONF = "${WORKDIR}/minnowfast.defconfig"

# UTF string in MODULE_DESCRIPTION field of drivers/mtd/nand/cafe_nand.c
# causes an error on do_package_write_deb.
SRC_URI += "file://remove-utf-from-cafe-nand-description.patch"
