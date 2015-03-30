DESCRPTION = "PAM module for MIT Kerberos"
SECTION = "admin"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=78d118c35ffd4b18868bd3a74c839302"

inherit autotools debian-squeeze

PR = "r0"

DEPENDS += " libpam heimdal"

libdir = "/lib"

do_compile_prepend() {
	sed -i -e "s:/i686-linux/:/intel/:g" ${S}/Makefile
}

FILES_${PN}-dbg += " \
${base_libdir}/security/.debug/ \
"

FILES_${PN} += "${base_libdir}/security/*"
