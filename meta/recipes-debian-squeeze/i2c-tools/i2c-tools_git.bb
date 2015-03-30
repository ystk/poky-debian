DESCRIPTION = "Set of i2c tools for linux"
SECTION = "utils"
LICENSE = "GPLv2"

PR = "r0"
#
#SRC_URI = "http://dl.lm-sensors.org/i2c-tools/releases/i2c-tools-${PV}.tar.bz2 \
#           file://Module.mk \
#          "
#

inherit autotools

#do_compile_prepend() {
#        cp ${WORKDIR}/Module.mk ${S}/eepromer/
#        sed -i 's#/usr/local#/usr#' Makefile
#        echo "include eepromer/Module.mk" >> Makefile	
#}

do_install_append() {
        install -d ${D}${includedir}/linux
        install -m 0644 include/linux/i2c-dev.h ${D}${includedir}/linux/i2c-dev-user.h
	rm -f ${D}${includedir}/linux/i2c-dev.h
}

#SRC_URI[md5sum] = "511376eed04455cdb277ef19c5f73bb4"
#SRC_URI[sha256sum] = "23b28e474741834e3f1b35b0686528769a13adc92d2ff5603cbda1d6bd5e5629"

#
#debian-squeeze
#
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
DEPENDS += "python-native python"
RDEPENDS += "python"

inherit debian-squeeze

do_compile_prepend() {
        sed -i 's#/usr/local#/usr#' Makefile
	sed -i 's#gcc#${HOST_PREFIX}gcc#' Makefile
}


# implement python-smbus package
do_compile_append() {
	cd py-smbus
	
	BUILD_SYS="${BUILD_SYS}" \
	HOST_SYS="${HOST_SYS}" \
	STAGING_INCDIR="${STAGING_INCDIR}" \
	STAGING_LIBDIR="${STAGING_LIBDIR}" \
	CFLAGS="${CFLAGS} -I../include" \
		${STAGING_BINDIR_NATIVE}/python setup.py build
}

PYTHON_VER = "2.6"

do_install_append() {
	install -d ${D}${libdir}/python${PYTHON_VER}/site-packages
	install -m 755 ${S}/py-smbus/build/lib.linux-${BUILD_ARCH}-${PYTHON_VER}/smbus.so \
			${D}${libdir}/python${PYTHON_VER}/site-packages
}

PACKAGES += "python-smbus"
FILES_${PN}-dbg += "${libdir}/python${PYTHON_VER}/site-packages/.debug"
FILES_python-smbus = "${libdir}/*"
