#
# lemon_3.5.4.bb
#
require lemon.inc

LIC_FILES_CHKSUM = "file://lemon.c;endline=8;md5=c7551a78fa3fdecd96d1ad6761d205ee"

SRC_URI[md5sum] = "f17da840eed792e896c3408d0ce97718"
SRC_URI[sha256sum] = "47daba209bd3bcffa1c5fcd5fdfc4f524eae619b4fa855aeeb1bbbc8bd2bb04f"
#
# debian-squeeze
#
inherit debian-squeeze
SRC_URI = "file://lemon.1"
PR = "r0"
DEBIAN_SQUEEZE_SRCPKG_NAME = "sqlite3"
S = "${DEBIAN_SQUEEZE_UNPACKDIR}/tool"
