LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

PR = "r0"

inherit debian-squeeze autotools
DEBIAN_SQUEEZE_SRCPKG_NAME = "${BPN}-3"

EXTRA_OEMAKE += " \
user \
PREFIX=/usr \
MANDIR=${datadir}/man \
EXLDFLAGS= \
"

# TODO: Add "sensord" package (See comments of "FILES_sensord").
#EXTRA_OEMAKE += "PROG_EXTRA=sensord"

PACKAGES =+ "fancontrol ${PN}-bin"
#PACKAGES =+ "sensord"

FILES_fancontrol = " \
${sbindir}/fancontrol \
${sbindir}/pwmconfig \
"

FILES_${PN}-bin = " \
${bindir}/* \
${sbindir}/* \
"

RDEPENDS_${PN}-bin = " ${PN} perl-module-constant"

# TODO: cannot build without "librrd2-dev"
#FILES_sensord = " \
#usr/sbin/sensord \
#"

# A patch in debian/patches modifies lm-sensors/trunk/CHANGES,
# but "lm-sensors" directory doesn't exist
# NOTE: we cannot modify the patch file by a patch because
# the patch includes CRLF that is changed by format-patch
MOD_PATCH = "${S}/debian/patches/09-sensord-multiple-chips.diff"
DEL_START_SYM = "Index: /lm-sensors/trunk/CHANGES"
DEL_START = 52
DEL_END = 61
do_unpack_append() {
	bb.build.exec_func("modify_debian_patch", d)
}
modify_debian_patch() {
	START=$(grep -nh "^${DEL_START_SYM}" ${MOD_PATCH} | sed "s@:.*@@")
	if [ "${DEL_START}" = "${START}" ]; then
		sed -i "${DEL_START},${DEL_END}d" ${MOD_PATCH}
	else
		bbfatal "failed to modify ${MOD_PATCH}"
	fi
}

python do_patch_append() {
	bb.build.exec_func('modify_makefile', d)
}
modify_makefile() {
	# replace hard coded CC by poky internal one
	sed -i "s|^CC := .*|CC := ${CC}|" ${S}/Makefile
	# set correct target architecture
	sed -i "s|^MACHINE.*|MACHINE := ${TARGET_ARCH}|" ${S}/Makefile
}

RDEPENDS += "perl perl-module-file-basename perl-module-exporter"
