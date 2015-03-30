#
# debian-squeeze
#
DESCRIPTION = "utility to set/show the host name or domain name"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=e93ab3e2d11fa93a6ec544dbd75a2647"
SECTION = "admin"
PR = "r0"

inherit autotools
inherit debian-squeeze

SRC_URI += "file://fix_makefile.patch "
