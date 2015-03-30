#
# debian-squeeze
#
DESCRIPTION = "Linux Virtual Server support programs \
The Linux Virtual Server is a highly scalable and highly available server \
built on a cluster of real servers. The architecture of the cluster is \
transparent to end users, and the users see only a single virtual server."
SECTION = "net"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://README;md5=77ccb03f78ad31f6336ec6c52daa4237"
PR = "r0"

inherit debian-squeeze autotools

DEPENDS += "popt libnl"
RDEPENDS += "popt libnl"

PARALLEL_MAKE = ""
EXTRA_OEMAKE = " BUILD_ROOT=${D}"

do_compile_prepend() {
	# Fix compiler
	sed -i -e "s:^CC.*:CC = ${CC}:" Makefile
	sed -i -e "s:^CC.*:CC = ${CC}:" libipvs/Makefile
	sed -i -e "s: nm : ${NM} :g" Makefile
	# Fix search directory for libpopt
	sed -i -e "s:^LIB_SEARCH =.*:LIB_SEARCH = ${STAGING_LIBDIR}:" Makefile
	sed -i -e "s:^MANDIR.*:MANDIR = ${datadir}/man:" Makefile
	sed -i -e "s:etc/rc.d:etc:g" Makefile	
	sed -i -e "s:ar rv:${AR} rv:" libipvs/Makefile
	# Remove strip option when install
	sed -i -e "s:0755 -s ipvsadm:0755 ipvsadm:" Makefile
}

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	# Fix initscript 
	sed -i -e "s:^AUTO.*:AUTO=\"true\":" ${S}/debian/ipvsadm.init
	sed -i -e "s:^DAEMON=\"none\":DAEMON=\"master\":" ${S}/debian/ipvsadm.init
	install -m 0755 ${S}/debian/ipvsadm.init ${D}${sysconfdir}/init.d/ipvsadm
}
