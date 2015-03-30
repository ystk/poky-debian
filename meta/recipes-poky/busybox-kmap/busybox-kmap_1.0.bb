#
# busybox-kmap
#
# This is a special recipe to install pre-compiled kmap binaries
# for busybox loadkmap. The only way to generate kmap data is
# running dumpkmap in busybox on a system keymap data has been
# already loaded on.
#   ex) # dumpkmap > us.kmap
#
# So they cannot be compiled and converted from keymap text data
# at build time. Please let me know if you have an idea to do it.
# <hayashi@swc.toshiba.co.jp>
#

LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

inherit allarch

PR = "r0"

SRC_URI = " \
file://us.kmap \
file://jp106.kmap \
"

S = "${WORKDIR}"

do_configure() {
	:
}

do_compile() {
	:
}

do_install() {
	install -d ${D}${datadir}/kmap
	install -m 0644 us.kmap ${D}${datadir}/kmap
	install -m 0644 jp106.kmap ${D}${datadir}/kmap
}

PACKAGES = "${PN}"
FILES_${PN} = "${datadir}/kmap"

PKG_SRC_CATEGORY ?= "poky-debian"
