inherit debian-squeeze-linux-checkout

SECTION = "devel"

DEPENDS = ""
RDEPENDS_${PN}-dev = ""
RRECOMMENDS_${PN}-dbg = "${PN}-dev (= ${EXTENDPKGV})"

B = "${S}"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "unifdef-native"
PROVIDES = "linux-libc-headers"

do_configure() {
	oe_runmake allnoconfig ARCH=${ARCH}
}

do_compile () {
	:
}

do_install() {
	oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix} ARCH=${ARCH}

	# The ..install.cmd conflicts between various configure runs
	find ${D}${includedir} -name ..install.cmd | xargs rm -f
}

BBCLASSEXTEND = "nativesdk"
