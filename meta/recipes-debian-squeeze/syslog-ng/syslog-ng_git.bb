#
# debian-squeeze
#
DESCRIPTION = "Next generation logging daemon"
SECTION = "admin"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=133409beb2c017d3b0f406f22c5439e7"
PR = "r0"

inherit autotools
inherit debian-squeeze

DEPENDS = "libcap glib-2.0 libnet pcre openssl tcp-wrappers zlib eventlog"
