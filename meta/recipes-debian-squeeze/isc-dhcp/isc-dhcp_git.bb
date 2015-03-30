#
# debian-squeeze 
#
DESCRIPTION = "ISC Dhcp"
HOMEPAGE = "http://www.isc.org"
SECTION = "net"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=63295183480c13e4dfee94e2e3ea99b9"
PR = "r0"

DEPENDS = "debianutils openssl"

inherit autotools
inherit debian-squeeze
SRC_URI += "file://fix-makefile.patch"

do_install_append() {
	install -d ${D}/${base_sbindir}

	# change the default path of dhclient (/usr/sbin => /sbin)
	# because other tools (e.g. ifup) assumes that it is installed there
	mv ${D}/${sbindir}/dhclient ${D}/${base_sbindir}

	# Install an essential script of Debian
	install -m 0755 ${S}/debian/dhclient-script.linux \
		${D}/${base_sbindir}/dhclient-script
	# Temporal export to use ifconfig.
	# dhclient-script has no PATH (bug?), related info:
	# http://www.linuxquestions.org/questions/slackware-14/dhclient-and-dhcpcd-broken-after-latest-current-update-793497/
	sed -i '1a\export PATH=$PATH:/sbin' ${D}/${base_sbindir}/dhclient-script

	# Remove all settings in dhclient.conf because
	# the default values are enough valid to work dhcplient
	rm -f ${D}/${sysconfdir}/dhclient.conf
	touch ${D}/${sysconfdir}/dhclient.conf

	# essential directories (see debian/*.dirs)
	install -d \
		${D}/${localstatedir}/lib/dhcp \
		${D}/${localstatedir}/run
}
