#
# debian-squeeze
#

DESCRIPTION = "Update Configuration File: preserve user changes to config files"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=361b6b837cad26c6900a926b62aada5f"
SECTION = "utils"
PR = "r0"

inherit autotools
inherit debian-squeeze

SRC_URI += "file://fix_Makefile.patch"
