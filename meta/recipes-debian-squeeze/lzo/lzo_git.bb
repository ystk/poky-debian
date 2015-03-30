#
# lzo_2.05.bb
#

DESCRIPTION = "Lossless data compression library"
HOMEPAGE = "http://www.oberhumer.com/opensource/lzo/"
SECTION = "libs"
LICENSE = "GPLv2+"
#LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
#                    file://src/lzo_init.c;beginline=23;endline=40;md5=924a0f71f5394f6d404be3b458474769"
#PR = "r0"

#SRC_URI = "http://www.oberhumer.com/opensource/lzo/download/lzo-${PV}.tar.gz \
#           file://autoconf.patch \
#           file://acinclude.m4 \
#           "

#SRC_URI[md5sum] = "c67cda5fa191bab761c7cb06fe091e36"
#SRC_URI[sha256sum] = "449f98186d76ba252cd17ff1241ca2a96b7f62e0d3e4766f88730dab0ea5f333"

inherit autotools

EXTRA_OECONF = "--enable-shared"

do_configure_prepend () {
	cp ${WORKDIR}/acinclude.m4 ${S}/
}

BBCLASSEXTEND = "native"

#
# debian-squeeze
#

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "lzo2"

PR = "r0"

SRC_URI = " \
file://acinclude.m4 \
file://configure.patch \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=8cad52263e636e25377bc18420118101 \
                    file://src/lzo_init.c;beginline=5;endline=36;md5=640e72f3d820b71a02dd55d52ad89c57"
