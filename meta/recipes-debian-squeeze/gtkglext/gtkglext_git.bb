# debian-squeeze
#
DESCRIPTION = "OpenGL Extension to GTK+"
SECTION = "libs"
LICENSE = "GPL & LGPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit debian-squeeze
inherit autotools
DEPENDS += "mesa"
