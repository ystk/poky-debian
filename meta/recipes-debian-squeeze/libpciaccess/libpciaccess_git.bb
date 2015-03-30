#
# debian-squeeze
#
DESCRIPTION = "Generic PCI access library for X"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=aa44c26bc646c6c9d9619c73b94a6e31"
SECTION = "devel"
PR = "r0"

DEPENDS = "zlib util-macros"

inherit autotools
inherit debian-squeeze
