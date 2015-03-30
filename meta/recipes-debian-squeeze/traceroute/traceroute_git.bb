#
# debian-squeeze
#

DESCRIPTION = "Traces the route taken by packets over an IPv4/IPv6 network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f "
SECTION = "net"

inherit autotools
inherit debian-squeeze

do_compile() {
        export LDFLAGS="${TARGET_LDFLAGS} -L${S}/libsupp"
        oe_runmake "env=yes"
}
do_install() {
	install -d ${D}${bindir}
	install -m 755 ${S}/traceroute/traceroute ${D}${bindir}
	mv ${D}${bindir}/traceroute ${D}${bindir}/traceroute.${PN}
}

pkg_postinst() {
	update-alternatives --install ${bindir}/traceroute traceroute ${bindir}/traceroute.${PN} 100
	update-alternatives --install ${bindir}/traceroute6 traceroute6 ${bindir}/traceroute.${PN} 100
}

pkg_postrm() {
	update-alternatives --remove traceroute ${bindir}/traceroute.${PN}
	update-alternatives --remove traceroute6 ${bindir}/traceroute.${PN}
}
