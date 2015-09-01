#
# recipes-connectivity/bind/bind_9.7.2-P3.bb
#

DESCRIPTION = "ISC Internet Domain Name Server"
HOMEPAGE = "http://www.isc.org/sw/bind/"
SECTION = "console/network"

LICENSE = "ISC"

DEPENDS = "openssl"
PR = "r0"

#SRC_URI = "ftp://ftp.isc.org/isc/bind9/${PV}/${BPN}-${PV}.tar.gz \
#	   file://conf.patch \
#           file://cross-build-fix.patch \
#	   "

#SRC_URI[md5sum] = "b4537cbae38b2daef36775bf49f33db9"
#SRC_URI[sha256sum] = "cd945f4766be664f4528ec065df626ad3624481695316ec8e13ad272f4abfb7e"

# --enable-exportlib is necessary for building dhcp
EXTRA_OECONF = " --enable-ipv6=no --with-randomdev=/dev/random --disable-threads \
                 --disable-devpoll --disable-epoll \
                 --sysconfdir=${sysconfdir}/bind \
                 --with-openssl=${STAGING_LIBDIR}/.. --with-libxml2=${STAGING_LIBDIR}/.. \
                 --enable-exportlib --with-export-includedir=${includedir} --with-export-libdir=${libdir} \
               "
inherit autotools update-rc.d

INITSCRIPT_NAME = "bind"
INITSCRIPT_PARAMS = "defaults"

PARALLEL_MAKE = ""

PACKAGES_prepend = "${PN}-utils "
FILES_${PN}-utils = "${bindir}/host ${bindir}/dig ${bindir}/nslookup"
#FILES_${PN}-dev += "${bindir}/isc-config.s"

do_install_append() {
	rm "${D}${bindir}/nslookup"
	install -d "${D}${sysconfdir}/bind"
	install -d "${D}${sysconfdir}/init.d"
	install -m 644 ${S}/conf/* "${D}${sysconfdir}/bind/"
	install -m 755 "${S}/init.d" "${D}${sysconfdir}/init.d/bind"
}

CONFFILES_${PN} = " \
	${sysconfdir}/bind/named.conf \
	${sysconfdir}/bind/named.conf.local \
	${sysconfdir}/bind/named.conf.options \
	${sysconfdir}/bind/db.0 \
	${sysconfdir}/bind/db.127 \
	${sysconfdir}/bind/db.empty \
	${sysconfdir}/bind/db.local \
	${sysconfdir}/bind/db.root \
	"

#
# debian-squeeze
#

inherit debian-squeeze

LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=7d054f5dfd641b4293d95016b3ca715a"

DEBIAN_SQUEEZE_SRCPKG_NAME = "bind9"

SRC_URI += " \
file://conf.patch \
file://cross-build-fix.patch \
"

DEBIAN_SQUEEZE_PATCH_FILTER = "debian/patches-applied"

# install isc-config.sh into ${PN}-dev
FILES_${PN} = " \
${bindir}/nsupdate \
${sbindir} \
${sysconfdir} \
${localstatedir} \
"
FILES_${PN}-dev += "${bindir}/isc-config.sh"
