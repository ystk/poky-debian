#
# debian-squeeze
#

DESCRIPTION = "TCP/IP swiss army knife -- transitional package"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=f1557018bf57b2ca74c68d44c03ddd91"
SECTION = "net"
PR = "r0"

inherit debian-squeeze

do_compile() {
	sed -i -e 's/\(\W\)gcc\(\W\)/\1$(TARGET_PREFIX)gcc\2/g' \
	-e 's/$(LD)/$(CC)/' ${S}/Makefile

	oe_runmake linux "CFLAGS='-g -Wall -O2' STATIC='' DFLAGS='-DLINUX -DTELNET -DGAPING_SECURITY_HOLE -DIP_TOS'"
}

do_install() {
	install -d ${D}${base_bindir}
	install -m 0755 nc ${D}${base_bindir}
}

SRC_URI += "file://fix_netcat.c.patch"
