#
# debian-squeeze
#
DESCRIPTION = "Fuse implementation of unionfs. \
This is another unionfs implementation using filesystem in \
userspace (fuse)."

SECTION = "misc"
LICENSE = "BSD & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7e5a37fce17307066eec6b23546da3b3"
PR = "r0"

inherit autotools debian-squeeze

DEPENDS = "fuse"
RDEPENDS += "fuse"

# Install to /usr instead of /usr/local
do_compile_prepend() {
	sed -i -e "s:PREFIX=.*:PREFIX=${prefix}:" ${S}/Makefile
}

# Install mandir
do_install_prepend() {
	install -d ${D}${datadir}/man/man8
}
