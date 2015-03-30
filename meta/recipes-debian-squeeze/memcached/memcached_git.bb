#
# debian-squeeze
#
DESCRIPTION = "A high-performance memory object caching system"
HOMEPAGE = "http://www.danga.com/memcached/"
SECTION = "web"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=7e5ded7363d335e1bb18013ca08046ff"
PR = "r0"

inherit debian-squeeze autotools update-rc.d

INITSCRIPT_NAME = "memcached"
INITSCRIPT_PARAMS = "defaults"

DEPENDS += "libevent"
RDEPENDS += "libevent"

SRC_URI += " \
file://remove_cross_compile_check.patch \
file://memcached.default \
"

EXTRA_OECONF += " --with-libevent=${STAGING_EXECPREFIXDIR} "
CFLAGS += " -I${STAGING_INCDIR} -DENDIAN_BIG=1"

do_install_append() {
	install -d ${D}${sysconfdir}
	install -m 0644 ${S}/debian/memcached.conf ${D}${sysconfdir}
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/scripts/memcached-init ${D}${sysconfdir}/init.d/memcached
	install -d ${D}${sysconfdir}/default
	install -m 0644 ${WORKDIR}/memcached.default ${D}${sysconfdir}/default/memcached
	install -d ${D}${datadir}/memcached/scripts
	install -m 0744 ${S}/scripts/start-memcached ${D}${datadir}/memcached/scripts
	install -m 0744 ${S}/scripts/memcached-tool ${D}${datadir}/memcached/scripts
}
