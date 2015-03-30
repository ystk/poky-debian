inherit debian-squeeze autotools

PR = "r0"

LICENSE = "NCSA"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=61cbac6719ae682ce6cd45b5c11e21af"

# This is a temporary way to pass configure. We should fix it.
do_configure() {
	autoreconf -f -v -i
	LINE=`grep -n "cannot run test program while cross compiling" ${S}/configure | cut -d':' -f1`
	BEGIN=`expr $LINE - 2`
	END=`expr $LINE + 1`

	echo "Cut an error message for cross compile and use sysroot libtool"
	SYSROOT_LIBTOOL="${STAGING_DIR_TARGET}/usr/bin/crossscripts/${TARGET_PREFIX}libtool"
	cat ${S}/configure | sed "$BEGIN,${END}d" | \
	sed -e "s%\$(top_builddir)/libtool%${SYSROOT_LIBTOOL}%" \
	> ${S}/configure.tmp

	mv ${S}/configure.tmp ${S}/configure
	chmod 755 ${S}/configure

	oe_runconf
}

do_install_prepend() {
	echo "Later, strip command is executed in do_package, so delete option of using host strip command"
	cat ${S}/libtar/Makefile | sed 's%INSTALL_PROGRAM\t= ${INSTALL} -s%INSTALL_PROGRAM\t= ${INSTALL}%' > ${S}/libtar/Makefile.tmp
	mv ${S}/libtar/Makefile.tmp ${S}/libtar/Makefile
}

do_install_append() {
	echo "A directory in a sample program is not need, so delete it"
	rm -rf ${D}/usr/bin
}
