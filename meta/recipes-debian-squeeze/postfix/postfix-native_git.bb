require postfix.inc

#SRC_URI[postfix-2.7.0.md5sum] = "df648f59421604e895cce56325f00bae"
#SRC_URI[postfix-2.7.0.sha256sum] = "c5b232ae5e396107bc58aed4178cf6cfd0a75b9dcbbedb49d158eb71d6c91b75"

#PR = "${INC_PR}.0"
PR = "r0"

#
# debian-squeeze
#
do_compile_prepend() {
	sed -i -e "s:makedefs):makedefs Linux 2):" ${S}/Makefile.in
	sed -i -e "s:makedefs;:makedefs Linux 2;:" ${S}/rmail/Makefile.in
	sed -i -e "/gcc / s:$: -L${STAGING_LIBDIR_NATIVE}:" ${S}/src/util/Makefile.in
	sed -i -e "/gcc / s:$: -L${STAGING_LIBDIR_NATIVE}:" ${S}/src/global/Makefile.in
}

# To avoid error: File not recognized: file truncated
# This error is caused by compiler was trying to access to one file
# with many threads at the same time 
PARALLEL_MAKE = ""

# According to debian/rules
BUILD_CFLAGS += " \
-DDEBIAN \
-DMAX_DYNAMIC_MAPS \
-DHAS_PCRE \
-DHAS_LDAP \
-DHAS_CDB \
-DHAS_MYSQL -I${STAGING_INCDIR_NATIVE}/mysql \
"

DEPENDS += "mysql-native tinycdb-native openldap-native postgresql-native libpcre-native"

inherit native
export POSTCONF = "bin/postconf"
do_install_append() {
        mkdir -p ${D}${STAGING_LIBDIR_NATIVE}
        install -m 755 ${S}/lib/libutil.a ${D}${STAGING_LIBDIR_NATIVE}/libpostfix-util.so.1
        install -m 755 ${S}/lib/libglobal.a ${D}${STAGING_LIBDIR_NATIVE}/libpostfix-global.so.1
        install -m 755 ${S}/lib/libdns.a ${D}${STAGING_LIBDIR_NATIVE}/libpostfix-dns.so.1
        install -m 755 ${S}/lib/libtls.a ${D}${STAGING_LIBDIR_NATIVE}/libpostfix-tls.so.1
        install -m 755 ${S}/lib/libmaster.a ${D}${STAGING_LIBDIR_NATIVE}/libpostfix-master.so.1
}

export_library_path() {
        export LD_LIBRARY_PATH="${LD_LIBRARY_PATH}:${S}/lib"
}

LIBDIR = ${D}${STAGING_LIBDIR_NATIVE}
