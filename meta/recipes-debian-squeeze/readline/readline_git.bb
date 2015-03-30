#
# readline_6.2.bb
#

require readline.inc

#PR = "r0"

#SRC_URI[md5sum] = "67948acb2ca081f23359d0256e9a271c"
#SRC_URI[sha256sum] = "79a696070a058c233c72dd6ac697021cc64abd5ed51e59db867d66d196a89381"

#
# debian-squeeze
#

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "readline6"

PR = "r0"

# configure-fix.patch: Required to avoid compile error
SRC_URI = " \
file://configure-fix.patch \
file://acinclude.m4 \
"

do_install_append() {
	install -d ${D}${sysconfdir}
	install -m 0644 ${S}/debian/inputrc ${D}${sysconfdir}
}
