#
# debian-squeeze
#

DESCRIPTION = "password strength checking and policy enforcement toolset"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://passwdqc/LICENSE;md5=1a44a6a6d93cba8bebd86214a8eec777"
SECTION = "admin"
PR = "r0"

inherit autotools
inherit debian-squeeze

DEPENDS = "libpam"

do_compile_prepend() {
	export syspath=${STAGING_INCDIR}
	export libpath=${STAGING_DIR_HOST}/lib
}
do_compile() {
	cd ${S}/passwdqc
	oe_runmake
}
do_install() {
	cd ${S}/passwdqc
	oe_runmake install DESTDIR=${D}
}

SRC_URI += "file://fix-make.patch "
