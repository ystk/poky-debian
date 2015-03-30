#
# debian-squeeze
#
DESCRIPTION = "Debian configuration management system"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://doc/README;md5=13cd9bc1096d2ff15f9869d3e0b4d980 \
		    file://debconf.conf;beginline=2;endline=34;md5=5ec88c9d80091921db4376a0e552c091"
SECTION = "admin"
PR = "r0"

inherit autotools
inherit debian-squeeze
SRC_URI += "file://fix_Makefile.patch"
