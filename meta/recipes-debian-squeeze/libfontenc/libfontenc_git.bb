DESCRIPTION = "X11 font encoding library"
SECTION = "libs"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=96254c20ab81c63e65b26f0dbcd4a1c1"


#
# debian-squeeze
#
inherit debian-squeeze
inherit autotools
DEPENDS += "zlib"
