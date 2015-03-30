#
# debian-squeeze
#

DESCRIPTION = "SGML infrastructure and SGML catalog file support"
LICENSE = "GPL"
SECTION = "text"
LIC_FILES_CHKSUM = "file://README;md5=1242304ffc492d13dc8469c28d0558a5"
PR = "r0"

inherit debian-squeeze autotools native
do_configure_prepend() {
	sed -i 's:= /usr:= ${D}/${STAGING_DIR_NATIVE}/usr:' ${S}/MAKE/include
}
