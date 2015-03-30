#
# debian-squeeze
#

DESCRIPTION = "remote file distribution client and server"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://Copyright;md5=3f47ec9f64b11c8192ee05a66b5c2755"
SECTION = "net"

inherit autotools
inherit debian-squeeze

SRC_URI += "file://fix_make.patch"
