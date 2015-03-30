#
# debian-squeeze
#
DESCRIPTION = "utility functions from BSD systems - development files"
SECTION = "libs"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;md5=79396297b1986b88c6cd1de4b4e1371b"
PR = "r0"

inherit debian-squeeze autotools
SRC_URI += "file://fix-using-compiler.patch"
DEBIAN_NOAUTONAME_${PN}-dev = "1"
