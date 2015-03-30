DESCRIPTION = "utility for IPMI control with kernel driver or LAN interface"
SECTION = "utils"
LICENSE = "BSD" 
LIC_FILES_CHKSUM = "file://COPYING;md5=9aa91e13d644326bf281924212862184"

S = "${WORKDIR}/git"

inherit autotools
inherit debian-squeeze
SRC_URI += "file://freeipmi.patch"

PR = "r0"

do_configure() {
	sed -i  '24315,24318d' ${S}/configure
	sed -i -e "24315s/.*/      :/" ${S}/configure
	oe_runconf
}

DEPENDS += "freeipmi"
