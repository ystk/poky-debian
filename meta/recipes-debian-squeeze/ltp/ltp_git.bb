#
# This is based on the following OpenEmbedded recipe:
#   ltp-ddt/ltp-ddt_0.0.1.bb
#   Carlos Hernandez - April 13, 2011, 10:08 p.m.
#   http://patches.openembedded.org/patch/2213/
#

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"

PR = "r0"

inherit autotools debian-squeeze

# fix_io_xxx_makefiles.patch: fix link errors of $(AIO_LIBS)
#   see http://marc.info/?l=ltp-list&m=127498275812854
SRC_URI = " \
file://reverse_runltp.patch \
file://reverse_IDcheck_sh.patch \
file://no_use_mktemp.patch \
file://add_clocks.patch \
file://clock_settime03.patch \
file://ln_test.patch \
file://libevent01.patch \
file://readlink03.patch \
file://sockioctl01.patch \
file://sysctl03.patch \
file://rt_sigprocmask01.patch \
file://fallocate.patch \
file://ltp_clone.patch \
file://utimensat.patch \
file://quota.patch \
file://quota.img \
file://ltp-quota.m4 \
file://math.patch;apply=no \
file://rt_sigsuspend01.patch \
file://fix_io_xxx_makefiles.patch \
"

# LTP doesn't support parallel build
PARALLEL_MAKE = ""

EXTRA_OEMAKE += " \
prefix=/usr/ltp \
CROSS_COMPILE=${HOST_PREFIX} \
SKIP_IDCHECK=1 \
KERNEL_INC=${STAGING_KERNEL_DIR}/include \
"

python do_unpack_append() {
	bb.build.exec_func('quota_setup', d)
}

quota_setup() {
	echo "Copy quota.img to ${WORKDIR}/testcases/kernel/syscalls/quotactl"
	cp ${WORKDIR}/quota.img ${WORKDIR}/${PN}-${PV}/testcases/kernel/syscalls/quotactl

	echo "Override ltp-quota.m4"
	cp ${WORKDIR}/ltp-quota.m4 ${S}/m4
}

do_patch_srcpkg_append() {
	echo "Patch math test"
	cd ${WORKDIR}/${PN}-${PV}
	patch -p1 < ${WORKDIR}/math.patch
}

do_compile() {
	oe_runmake 'DESTDIR=${D}'
}

PACKAGES = "${PN}-dbg ${PN}-doc ${PN}"

FILES_${PN}-dbg = " \
/usr/ltp/.debug \
/usr/ltp/*/.debug \
/usr/ltp/*/*/.debug \
/usr/ltp/*/*/*/.debug \
/usr/ltp/*/*/*/*/.debug \
/usr/src/debug \
"

FILES_${PN}-doc = " \
/usr/share/* \
"

FILES_${PN} = " \
/usr/ltp/* \
"
