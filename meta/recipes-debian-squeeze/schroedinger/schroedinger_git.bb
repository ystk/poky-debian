#
# debian-squeeze
#
DESCRIPTION = "library for encoding/decoding of Dirac video streams"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING.GPL;md5=e181e3b7c66f5f96921d813c1074f833"
inherit debian-squeeze
inherit autotools
do_compile_prepend() {
	sed -i 's:ORCC = /usr/bin/orcc:ORCC = orcc:' $(find -name "Makefile")
}
DEPENDS += "orc-native"
