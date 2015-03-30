DESCRIPTION = "Database server drivers for libdbi"
HOMEPAGE = "http://libdbi.sourceforge.net/"
SECTION = "libs"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=39b9adcec0e6f250059e8331c14dc7c7"
PR = "r0"

inherit autotools debian-squeeze

DEPENDS += "libdbi mysql postgresql sqlite3"

EXTRA_OECONF += " \
--with-dbi-incdir=${STAGING_INCDIR} \
--with-dbi-libdir=${STAGING_LIBDIR} \
--with-mysql \
--with-mysql-incdir=${STAGING_INCDIR} \
--with-mysql-libdir=${STAGING_LIBDIR}/mysql \
--with-pgsql \
--with-pgsql-incdir=${STAGING_INCDIR} \
--with-pgsql-libdir=${STAGING_LIBDIR} \
--with-sqlite3 \
--with-sqlite3-incdir=${STAGING_INCDIR} \
--with-sqlite3-libdir=${STAGING_LIBDIR} \
"
PACKAGES += "libdbd-sqlite3 libdbd-mysql libdbd-pgsql"

FILES_libdbd-sqlite3 = "${libdir}/dbd/libdbdsqlite3*"
FILES_libdbd-pgsql = "${libdir}/dbd/libdbdpgsql*"
FILES_libdbd-mysql = "${libdir}/dbd/libdbdmysql*"
FILES_${PN}-dbg += "${libdir}/dbd/.debug"
