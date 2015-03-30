#
# debian-squeeze
#
DESCRIPTION = "rich set of graph drawing tools"
LICENSE = "GPL & LGPL"
SECTION = "graphics"
LIC_FILES_CHKSUM = "file://COPYING;md5=059e8cd6165cb4c31e351f2b69388fd9"
PR = "r0"
inherit debian-squeeze autotools native
EXTRA_OECONF += "--enable-perl=no"
SRC_URI += "file://fix_gvcext.patch"
#LDFLAGS += "-L${STAGING_LIBDIR_NATIVE}/perl-native/perl"

