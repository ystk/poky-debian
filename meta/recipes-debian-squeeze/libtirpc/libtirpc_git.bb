DESCRIPTION = "transport-independent RPC library"
SECTION = "libs"
LICENSE = "SISSL"
LIC_FILES_CHKSUM = "file://COPYING;md5=1c32c8e351f97e43e1ad6cf7f62de3bf"

inherit autotools debian-squeeze

DEPENDS += " libgssglue"

EXTRA_OECONF += " --enable-gss"
