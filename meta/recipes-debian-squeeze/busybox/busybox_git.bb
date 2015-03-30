#
# busybox_1.18.5.bb
#

require busybox.inc
#PR = "r1"

#SRC_URI = "http://www.busybox.net/downloads/busybox-${PV}.tar.bz2;name=tarball \
#           file://udhcpscript.patch \
#           file://udhcpc-fix-nfsroot.patch \
#           file://B921600.patch \
#           file://get_header_tar.patch \
#           file://busybox-appletlib-dependency.patch \
#           file://run-parts.in.usr-bin.patch \
#           file://busybox-udhcpc-no_deconfig.patch \
#           file://find-touchscreen.sh \
#           file://busybox-cron \
#           file://busybox-httpd \
#           file://busybox-udhcpd \
#           file://busybox-udhcpc \
#           file://default.script \
#           file://simple.script \
#           file://hwclock.sh \
#           file://mount.busybox \
#           file://syslog \
#           file://syslog.conf \
#           file://mdev \
#           file://mdev.conf \
#           file://umount.busybox \
#           file://defconfig"

#SRC_URI[tarball.md5sum] = "96dd43cc7cee4017a6bf31b7da82a1f5"
#SRC_URI[tarball.sha256sum] = "10954fcd5c48d8a262a3497b16227bf983a05658bf2bf661af2fdeca773f2fc0"

EXTRA_OEMAKE += "V=1 ARCH=${TARGET_ARCH} CROSS_COMPILE=${TARGET_PREFIX} SKIP_STRIP=y"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

BUSYBOX_CONFIG ?= "defconfig"

BUSYBOX_SYSLOG_INIT ?= "syslog"
BUSYBOX_SYSLOG_CONF ?= "syslog.conf"

SRC_URI = " \
file://udhcpscript.patch \
file://udhcpc-fix-nfsroot.patch \
file://B921600.patch \
file://get_header_tar.patch \
file://busybox-appletlib-dependency.patch \
file://run-parts.in.usr-bin.patch \
file://busybox-udhcpc-no_deconfig.patch \
file://sysinfo.patch \
file://find-touchscreen.sh \
file://busybox-cron \
file://busybox-httpd \
file://busybox-udhcpd \
file://busybox-udhcpc \
file://default.script \
file://simple.script \
file://hwclock.sh \
file://mount.busybox \
file://${BUSYBOX_SYSLOG_INIT} \
file://${BUSYBOX_SYSLOG_CONF} \
file://mdev \
file://mdev.conf \
file://umount.busybox \
file://${BUSYBOX_CONFIG} \
file://rc \
"

rename_local_file() {
	LOCAL="$1"
	DEFAULT="$2"
	[ "$LOCAL" = "$DEFAULT" ] || cp ${WORKDIR}/$LOCAL ${WORKDIR}/$DEFAULT
}

rename_local_files() {
	rename_local_file ${BUSYBOX_CONFIG} defconfig
	rename_local_file ${BUSYBOX_SYSLOG_CONF} syslog.conf
}

python do_unpack_append() {
	bb.build.exec_func('rename_local_files', d)
}

# Remove strip patch in debian source.
# This patch removes "busybox_unstripped" action used in do_compile.
do_patch_srcpkg_prepend() {
	sed -i "/^strip.patch$/d" ${S}/debian/patches/series
}

# apply some patches only if kernel version is 3.4 or later
#
# The following patches are needed only if kernel version is
# 3.4 or later because <linux/ext2_fs.h> is removed.
# exclude_ext2_fs_header.patch:
#  1.use busybox internal header file instead of linux/ext2_fs.h
#  2.move mkfs_ext2.c from util-linux/ to e2fsprogs/ (config)
# move_mkfs_ext2_source.patch
#  move mkfs_ext2.c from util-linux/ to e2fsprogs/ (src)
# match_e2fs_defs_header_up_with_kernel.patch
#  fix a macro name according to busybox internal header

SRC_URI += " \
file://exclude_ext2_fs_header.patch;apply=no \
file://move_mkfs_ext2_source.patch;apply=no \
file://match_e2fs_defs_header_up_with_kernel.patch;apply=no \
"

BUSYBOX_EXT2_PATCHES = " \
exclude_ext2_fs_header.patch \
move_mkfs_ext2_source.patch \
match_e2fs_defs_header_up_with_kernel.patch \
"

DEPENDS += "virtual/kernel"
do_patch[depends] += "virtual/kernel:do_populate_sysroot"
do_patch_append() {
	bb.build.exec_func('apply_ext2_patches', d)
}

apply_ext2_patches() {
	if [ ! -f ${STAGING_KERNEL_DIR}/kernel-abiversion ]; then
		echo "ERROR: kernel-abiversion not found"
		exit 1
	fi
	KERNELVER=$(cat ${STAGING_KERNEL_DIR}/kernel-abiversion)

	KERNELVER_STRIPPED=$(echo $KERNELVER | sed "s|^\([0-9]*\.[0-9]*\.[0-9]*\).*|\1|")
	KV_VER=$(echo $KERNELVER_STRIPPED | cut -d "." -f 1)
	KV_PATCHLV=$(echo $KERNELVER_STRIPPED | cut -d "." -f 2)
	KV_SUBLV=$(echo $KERNELVER_STRIPPED | cut -d "." -f 3)

	if [ "$KV_VER" -gt "3" -o \
		\( "$KV_VER" = "3" -a "$KV_PATCHLV" -ge "4" \) ]; then
		echo "NOTE: Kernel version is 3.4 or later, applying ext2 related patches..."
		for p in ${BUSYBOX_EXT2_PATCHES}; do
			echo "NOTE: applying $p"
			patch -p1 < ${WORKDIR}/$p
		done
	fi
}
