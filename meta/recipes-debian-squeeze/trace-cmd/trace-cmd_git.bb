#
# trace-cmd_git.bb
#

DESCRIPTION = "User interface to Ftrace"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe \
                    file://trace-cmd.c;beginline=6;endline=8;md5=2c22c965a649ddd7973d7913c5634a5e"

#SRCREV = "6c696cec3f264a9399241b6e648f58bc97117d49"
#PR = "r2"
#PV = "1.0.5+git${SRCPV}"

inherit pkgconfig

#SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/rostedt/trace-cmd.git;protocol=git \
#           file://addldflags.patch"
#S = "${WORKDIR}/git"

EXTRA_OEMAKE = "'prefix=${prefix}'"

FILES_${PN}-dbg += "${datadir}/trace-cmd/plugins/.debug/"

do_install() {
	oe_runmake prefix="${prefix}" DESTDIR="${D}" install
}

#
# debian-squeeze
#

inherit debian-squeeze

# addldflags.patch: fix include directories and LDFLAGS (modified)
# remove-doc.patch: building & installing documents depend on asciidoc command
SRC_URI = " \
file://addldflags.patch \
file://remove-doc.patch \
"

PR = "r0"

SRC_URI += "file://blktrace-api-compatibility.patch;apply=no"
DEPENDS += "virtual/kernel"
do_patch[depends] += "virtual/kernel:do_populate_sysroot"
do_patch_append() {
	bb.build.exec_func('apply_api_patch', d)
}
apply_api_patch() {
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
		\( "$KV_VER" = "3" -a "$KV_PATCHLV" -ge "0" -a "$KV_SUBLV" -ge "0" \) ]; then
		echo "NOTE: Kernel version is newer than 3.0.0, applying API patch..."
		patch -p1 < ${WORKDIR}/blktrace-api-compatibility.patch
	fi
}

