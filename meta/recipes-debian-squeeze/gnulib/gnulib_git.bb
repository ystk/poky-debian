#
# debian-squeeze
#
DESCRIPTION = "GNU Portability Library"
HOMEPAGE = "http://www.gnu.org/software/gnulib/"
SECTION = "devel"
LICENSE = "GPL & LGPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=e4cf3810f33a067ea7ccd2cd889fed21"
PR = "r0"

inherit debian-squeeze autotools

# According to debian/rules
do_install() {
	install -d ${D}${bindir}
	cp -a check-module gnulib-tool ${D}${bindir}
	install -d ${D}${datadir}/gnulib
        cp -a build-aux posix-modules config doc lib m4 modules top tests \
             MODULES.html.sh Makefile ${D}${datadir}/gnulib
	
	# Fixing permissions
        chmod 0755 ${D}${datadir}/gnulib/build-aux/config.guess
        chmod 0755 ${D}${datadir}/gnulib/build-aux/config.sub
        chmod 0755 ${D}${datadir}/gnulib/build-aux/gendocs.sh
        chmod 0644 ${D}${datadir}/gnulib/doc/gendocs_template
        chmod 0755 ${D}${datadir}/gnulib/lib/config.charset
        chmod 0644 ${D}${datadir}/gnulib/m4/fflush.m4
        chmod 0644 ${D}${datadir}/gnulib/modules/canonicalize-lgpl
        chmod 0644 ${D}${datadir}/gnulib/modules/fflush
        chmod 0644 ${D}${datadir}/gnulib/modules/fflush-tests
        chmod 0644 ${D}${datadir}/gnulib/tests/test-base64.c
        chmod 0755 ${D}${datadir}/gnulib/tests/test-closein.sh
        chmod 0644 ${D}${datadir}/gnulib/tests/test-fflush.c
        chmod 0755 ${D}${datadir}/gnulib/tests/test-posix_spawn1.in.sh
        chmod 0755 ${D}${datadir}/gnulib/tests/test-posix_spawn2.in.sh

        # Removing unused files
        rm -f ${D}${datadir}/gnulib/modules/COPYING
        rm -f ${D}${datadir}/gnulib/*/.cvsignore
        rm -f ${D}${datadir}/gnulib/*/.gitignore
}

FILES_${PN} = "${bindir}/* ${datadir}/*"
