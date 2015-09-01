#
# debian-squeeze
#

SUMMARY = "ganglia - distributed monitoring system"
DESCRIPTON = "Ganglia is a scalable distributed monitoring system \
for high-performance computing systems such as clusters and Grids. \
"
SECTION = "net"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=5519c868a3a2638d481065e896ca4d00"
PR = "r0"

inherit debian-squeeze autotools

DEPENDS += "apr libpcre expat confuse rrdtool"
RDEPENDS_${PN}-webfrontend += "php"
 
EXTRA_OECONF += " \
		--sysconfdir=${sysconfdir}/ganglia \
		--enable-shared \
		--with-gmetad \
		--disable-python \
"

do_configure_append () {
	autoreconf -fvi
	(cd libmetrics; autoreconf -fvi)
}

# Setup ganglia user
inherit passwd
GANGLIA_NAME ?= "ganglia"
GANGLIA_PASS ?= ""
GANGLIA_UID ?= "114"
GANGLIA_GID ?= "120"
GANGLIA_COMMENT ?= "Ganglia Monitor"
GANGLIA_HOME ?= "/usr/lib/ganglia"
GANGLIA_SHELL ?= "${bindir}/false"
GANGLIA_PASSWD = "\
${GANGLIA_NAME}:${GANGLIA_PASS}:${GANGLIA_UID}:${GANGLIA_GID}:\
${GANGLIA_COMMENT}:${GANGLIA_HOME}:${GANGLIA_SHELL}"
GANGLIA_GROUP = "${GANGLIA_NAME}:${GANGLIA_PASS}:${GANGLIA_GID}:"

do_install_append () {
	install -d ${D}${sysconfdir}/ganglia
	install -d ${D}${datadir}/ganglia-webfrontend
	install -d ${D}${sysconfdir}/ganglia-webfrontend
	install -d ${D}${sysconfdir}/init.d
	install -m 0644 ${S}/debian/gmond.conf ${D}${sysconfdir}/ganglia
	install -m 0755 ${S}/debian/gmetad.init ${D}${sysconfdir}/init.d/gmetad
	install -m 0755 ${S}/debian/ganglia-monitor.init ${D}${sysconfdir}/init.d/ganglia-monitor
	install -m 0644 ${S}/web/*.php ${D}${datadir}/ganglia-webfrontend
	install -m 0644 ${S}/web/*.css ${D}${datadir}/ganglia-webfrontend
	install -d ${D}${datadir}/ganglia-webfrontend/templates
	install -d ${D}${datadir}/ganglia-webfrontend/templates/default
	install -d ${D}${datadir}/ganglia-webfrontend/templates/default/images
	install -m 0644 ${S}/web/templates/default/*.tpl ${D}${datadir}/ganglia-webfrontend/templates/default
	install -m 0644 ${S}/web/templates/default/images/*.jpg ${D}${datadir}/ganglia-webfrontend/templates/default/images
	install -m 0644 ${S}/web/private_clusters ${D}${sysconfdir}/ganglia-webfrontend
	install -m 0644 ${S}/debian/apache.conf ${D}${sysconfdir}/ganglia-webfrontend
	
	# add ganglia user
	install -d ${D}/${PASSWD_DIR}
	echo "${GANGLIA_PASSWD}" > ${D}/${PASSWD_DIR}/ganglia
	install -d ${D}/${GROUP_DIR}
	echo "${GANGLIA_GROUP}" > ${D}/${GROUP_DIR}/ganglia
}

PACKAGES =+ "${PN}-monitor gmetad libganglia1 libganglia1-dev ${PN}-webfrontend"

FILES_${PN}-monitor = "${sysconfdir}/ganglia/gmond.conf \
		       ${bindir}/gmetric ${bindir}/gstat \
		       ${sbindir}/gmond ${sysconfdir}/init.d/ganglia-monitor \
			   ${PASSWD_DIR} ${GROUP_DIR}"
FILES_gmetad = "${sysconfdir}/ganglia/gmetad.conf ${sysconfdir}/init.d/ \
		${sbindir}/gmetad"
FILES_libganglia1 = "${libdir}/ganglia ${libdir}/*.so.* "
FILES_libganglia1-dev = "${includedir} ${libdir}/libganglia.a ${libdir}/libganglia.la"
FILES_${PN}-webfrontend = "${sysconfdir}/ganglia-webfrontend \
			   ${datadir}/ganglia-webfrontend"

do_install_append() {
	install -d ${D}/${PASSWD_DIR}
	echo "${GANGLIA_PASSWD}" > ${D}/${PASSWD_DIR}/ganglia
	install -d -m 0755 {D}/var/lib/ganglia
	install -d -m 0755 {D}/var/lib/ganglia/rrds
	chown nobody:${GANGLIA_GID} {D}/var/lib/ganglia/rrds
	chown ${GANGLIA_UID}:${GANGLIA_GID} {D}/var/lib/ganglia
}
