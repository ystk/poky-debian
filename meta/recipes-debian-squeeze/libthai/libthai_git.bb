DESCRIPTION = " Development files for Thai language support library"
SECTION = "libs"
LICENSE = "LGPL-2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=2d5025d4aa3495befef8f17206a5b0a1"

inherit debian-squeeze
inherit autotools
DEPENDS += "libdatrie-native libdatrie"
