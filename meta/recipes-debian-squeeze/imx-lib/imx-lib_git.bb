LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = " \
file://ipu/mxc_ipu_lib.c;beginline=7;endline=12;md5=0a7b39bf814232d023456544e797abda \
file://vpu/vpu_lib.c;beginline=8;endline=13;md5=0a7b39bf814232d023456544e797abda \
file://sahara2/fsl_shw_rand.c;endline=19;md5=a099fd3f86cdd8d8a287b0a86897ecf5 \
"

PR = "r0"

inherit debian-squeeze

DEPENDS += "linux-libc-headers"

# install some sahara libraries as symlinks
SRC_URI = "file://copy-as-symlink.patch"

# FIXME: "ipu" and "vpu" are not tested, to be implemented
#IMX_LIB_TARGETS ?= "ipu vpu sahara2"
IMX_LIB_TARGETS ?= "sahara2"
IMX_LIB_PLATFORM ?= "IMX51"
IMX_LIB_VERSION_MAJOR ?= "2"
IMX_LIB_VERSION_MINOR ?= "0"

EXTRA_OEMAKE += " \
DESTDIR=${D} \
DIRS='${IMX_LIB_TARGETS}' \
CROSS_COMPILE=${TARGET_PREFIX} \
PLATFORM=${IMX_LIB_PLATFORM} \
LIB_VERSION_MAJOR=${IMX_LIB_VERSION_MAJOR} \
LIB_VERSION_MINOR=${IMX_LIB_VERSION_MINOR} \
"

do_configure() {
	:
}

do_compile() {
	unset CFLAGS
	# remove hardcorded standard include paths
	sed -i "s| -I/usr/src/linux/include/||" ${S}/Makefile
	oe_runmake
}

do_install() {
	oe_runmake install
}

PACKAGES =+ "sahara"
RDEPENDS_${PN}-dev += "sahara"
FILES_sahara = " \
${libdir}/libsahara.so.2.0 \
${libdir}/libsahara.so.2 \
"
