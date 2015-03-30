
#
# debian-squeeze
#
DESCRIPTION = "terminal-based package manager (terminal interface only)"
SECTION = "admin"
LICENSE = ""
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

CXXFLAGS += " -Wno-deprecated "
inherit debian-squeeze
inherit autotools gettext
DEPENDS += "apt libsigc++-2.0 cwidget ncurses gettext boost vte libept sqlite3 xapian-core zlib google-mock elinks po4a-native"
do_configure_prepend() {
   sed -i "s:tests$::" Makefile.am
}
do_configure() {
   oe_runconf --disable-werror --disable-docs
}
