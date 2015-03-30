#
# debian-squeeze
#
# udev only for initramfs
#

FILESPATH = "\
${FILE_DIRNAME}/files:\
${FILE_DIRNAME}/udev-${PV}:\
${FILE_DIRNAME}/${PN}-${PV}\
"

include udev-new.inc
DEBIAN_SQUEEZE_SRCPKG_NAME = "udev"

PR = "r0"

SRC_URI += "file://local.rules.initramfs"

scriptsdir = "/scripts"

# scripts for init of initramfs are needed
do_install_append() {
	rm -r ${D}${sysconfdir}/init.d
	rm -r ${D}${sysconfdir}/udev/scripts

	# replace local.rules by more simple one
	rm ${D}${sysconfdir}/udev/rules.d/local.rules
	install -m 0644 ${WORKDIR}/local.rules.initramfs \
		${D}${sysconfdir}/udev/rules.d/local.rules

	# install scripts for init of initramfs
	install -d ${D}${scriptsdir}/init-top   
	install -d ${D}${scriptsdir}/init-bottom
	install -m 0755 ${S}/extra/initramfs.top \
		${D}${scriptsdir}/init-top/udev
	install -m 0755 ${S}/extra/initramfs.bottom \
		${D}${scriptsdir}/init-bottom/udev
}

PACKAGES = "${PN}-doc ${PN}-dbg ${PN}-dev ${PN}"
INITSCRIPT_PACKAGES = ""

FILES_${PN} = "/*"
FILES_${PN}-dev = " \
${includedir}/* \
${libdir}/libudev.so \
${libdir}/libudev.la \
${libdir}/libgudev*.so \
${libdir}/libgudev*.la \
${libdir}/pkgconfig \
${datadir}/pkgconfig \
"
