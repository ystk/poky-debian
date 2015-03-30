#
# debian-squeeze
#
DESCRIPTION = "library for asyncronous name resolves "
SECTION = "libs"
HOMEPAGE = "http://daniel.haxx.se/projects/c-ares"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README.cares;md5=98226d8a111fb58560334ba4c37259a4"
PR = "r0"


inherit debian-squeeze autotools

# backport from 1.8.0-1
SRC_URI += " \
file://0001-Add-ares_parse_naptr_reply.patch \
"

BBCLASSEXTEND = "native"
