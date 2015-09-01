#
# Recipe of libwebsockets
#
# imported from upstream
#

LICENSE = "LGPL2.1"
LIC_FILES_CHKSUM = " \
file://LICENSE;md5=041a1dec49ec8a22e7f101350fd19550 \
"

inherit debian-squeeze-misc
inherit autotools
inherit cmake

DEBIAN_SQUEEZE_MISC_GIT = "${DEBIAN_SQUEEZE_GIT_OSS}"
DEBIAN_SQUEEZE_MISC_COMMIT = "master"

SRC_URI = ""

# openssl is optional, depends on configure options
DEPENDS += "zlib openssl"

#SRC_URI="file://libwebsockets-1.23-chrome32-firefox24.tar.gz"
#SRC_URI="http://git.libwebsockets.org/cgi-bin/cgit/libwebsockets/snapshot/libwebsockets-1.23-chrome32-firefox24.tar.gz"
#S="${WORKDIR}/libwebsockets-1.23-chrome32-firefox24"

# same as debian/rules
EXTRA_OECONF += "--enable-openssl --enable-libcrypto"

# add ${PN}-test-server package
PACKAGES =+ "${PN}-test-server"

FILES_${PN}-test-server += " \
${bindir}/libwebsockets-test-* \
${datadir}/libwebsockets-test-server \
"
RDEPENDS_${PN}-test-server += "${PN}"

PKG_SRC_CATEGORY = "oss"

do_compile_prepend() {
	export LD_LIBRARY_PATH=${STAGING_LIBDIR_NATIVE} 
	cmake .
}
