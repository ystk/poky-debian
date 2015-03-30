#
# debian-squeeze 
#

DESCRIPTION = "process scheduling daemon"
SECTION = "admin" 
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = " \
file://README;md5=4c97da33dcaed14965b7b2a6171ad812 \
file://cron.c;endline=25;md5=509aa184034869736075d05ee88b2bdd \
"

inherit debian-squeeze
inherit autotools

PR = "r0"

# fixMakefile.patch: remove strip option from native install command
SRC_URI = " \
file://init \
file://fixMakefile.patch \
"

# modification in debian/patches/*.diff are already applied
do_patch_srcpkg() {
	:
}

do_install_prepend() {
	install -d ${D}${sbindir}
	install -d ${D}${bindir}
	install -d ${D}${mandir}
}

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/cron
}
