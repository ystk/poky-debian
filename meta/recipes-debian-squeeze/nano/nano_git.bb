DESCRIPTION = "small, friendly text editor inspired by Pico"
SECTION = "editors"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=f27defe1e96c2e1ecd4e0c9be8967949"


#
# debian-squeeze
#
inherit debian-squeeze
inherit autotools gettext
DEPENDS += "ncurses texinfo"
