#
# debian-squeeze 
#
DESCRIPTION = "libiconv-hook"
HOMEPAGE = "http://webdav.todo.gr.jp/download"
SECTION = "net"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6136ce925a14a2b9ce64f189efb66f2f"
PR = "r0"
DEPENDS = ""

inherit autotools
inherit debian-squeeze
LIBTOOL = ${HOST_SYS}-libtool
EXTRA_OEMAKE = "'LIBTOOL = ${LIBTOOL}'"
do_configure() {
        cd lib
        autotools_do_configure
}
do_compile() {
        cd lib
        oe_runmake
}
do_install() {
        cd lib
	autotools_do_install
}

DEBIAN_SQUEEZE_SRCPKG_NAME = "libapache-mod-encoding" 
