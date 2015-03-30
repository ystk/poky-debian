
#
# debian-squeeze
#
DESCRIPTION = "Terminal emulator widget for GTK+ 2.0 - runtime files"
SECTION = "libs"
LICENSE = "LGPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=3bf50002aefd002f49e7bb854063f7e7"


inherit debian-squeeze
inherit autotools
DEPENDS += "atk1.0 cairo fontconfig freetype glib-2.0 gtk+2.0 ncurses pango libx11"
