SUMMARY = "bash script to check executables security"
DESCRIPTION = "Checksec.sh is a bash scrip to check \
executable properties like (PIE, RELRO, PaX, Canaries, \
ASLR, Fortify Source)."
HOMEPAGE = "https://github.com/slimm609/checksec.sh"
SECTION = "security"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://checksec;md5=a2513cd750091664a7bbf3ac370bc58f"

PR = "r0"

inherit debian-squeeze-misc

DEBIAN_SQUEEZE_MISC_GIT = "${DEBIAN_SQUEEZE_GIT_OSS}"
DEBIAN_SQUEEZE_MISC_COMMIT = "patched-for-debian-squeeze"

SRC_URI = ""

RDEPENDS = "bash binutils binutils-symlinks findutils file kernel-dev"

FILES_${PN} = " \
/root/checksec/checksec \
"

do_install() {
	install -d ${D}/root/checksec
	install -m 755 ${S}/checksec ${D}/root/checksec/
}

PKG_SRC_CATEGORY = "oss"
