#
# debian-squeeze 
#

DESCRIPTION = "Generic client/server library for SASL authentication."
LICENSE = "BSD"
PR = "r0"
SECTION = "console/network"
DEPENDS = "openssl db"
LIC_FILES_CHKSUM = "file://COPYING;md5=3f55e0974e3d6db00ca6f57f2d206396"

inherit autotools
inherit debian-squeeze

# use cross nm instead of native nm
do_patch_append() {
	pn = bb.data.getVar("PN", d, 1)
	if not pn.endswith('-native') and not pn.endswith('-nativesdk'):
		bb.build.exec_func('replace_nm', d)
}
replace_nm() {
	sed -i "s@^\tnm @\t${TARGET_SYS}-nm @" ${S}/lib/Makefile.am
}

LIBTOOL = "${HOST_SYS}-libtool"
EXTRA_OECONF += " --enable-keep-db-open=yes \
		  --enable-staticdlopen=yes"
EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'"

# do_compile rarely fails with the following error when
# other CPU and I/O bound tasks are running.
#   mv: cannot stat `.deps/allockey.Tpo': No such file or directory
# cyrus-sasl2 may not support parallel build.
PARALLEL_MAKE = ""

do_configure_prepend() {
	rm -f acinclude.m4 config/libtool.m4
}

do_compile_prepend() {
	cd include
	${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS} makemd5.c -o makemd5
	touch makemd5.o makemd5.lo makemd5
	cd ..
}

FILES_${PN} += "${prefix}/lib/sasl2/*.so*"
FILES_${PN}-dev += "${libdir}/sasl2/*.la ${libdir}/sasl2/*.a"
do_install() {
	oe_libinstall -so -a -C lib libsasl2 ${D}${libdir}
	install -d ${D}${libdir}/sasl2
	install -d ${D}${includedir}/sasl
	install -m 0644 ${S}/include/hmac-md5.h ${D}${includedir}/sasl/
	install -m 0644 ${S}/include/md5.h ${D}${includedir}/sasl/
	install -m 0644 ${S}/include/md5global.h ${D}${includedir}/sasl/
	install -m 0644 ${S}/include/sasl.h ${D}${includedir}/sasl/
	install -m 0644 ${S}/include/saslplug.h ${D}${includedir}/sasl/
	install -m 0644 ${S}/include/saslutil.h ${D}${includedir}/sasl/
	install -m 0644 ${S}/include/prop.h ${D}${includedir}/sasl/
}


