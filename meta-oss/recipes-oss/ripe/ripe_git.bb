SUMMARY = "runtime intrusion prevention evaluator"
DESCRIPTION = "RIPE is a runtime intrusion prevention \
presented in a conference"
HOMEPAGE = "https://github.com/johnwilander/RIPE"
SECTION = "security"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f63904de85fc69196ea36b5ec71c2f45"

PR = "r0"

inherit debian-squeeze-misc

DEBIAN_SQUEEZE_MISC_GIT = "${DEBIAN_SQUEEZE_GIT_OSS}"
DEBIAN_SQUEEZE_MISC_COMMIT = "master"

SRC_URI = ""

RDEPENDS = "python"

FILES_${PN} = " \
/root/ripe/ripe_tester.py \
/root/ripe/build/ripe_attack_generator \
"

do_compile() {
	oe_runmake
}

do_install() {
	install -d ${D}/root/ripe/build
	install -m 755 ${S}/build/ripe_attack_generator ${D}/root/ripe/build/
	install -m 755 ${S}/ripe_tester.py ${D}/root/ripe/
}

PKG_SRC_CATEGORY = "oss"
