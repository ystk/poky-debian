#
# freetype_2.4.6.bb
#

SUMMARY = "Freetype font rendering library"
DESCRIPTION = "FreeType is a software font engine that is designed to be small, efficient, \
highly customizable, and portable while capable of producing high-quality output (glyph \
images). It can be used in graphics libraries, display servers, font conversion tools, text \
image generation tools, and many other products as well."
HOMEPAGE = "http://www.freetype.org/"
BUGTRACKER = "https://savannah.nongnu.org/bugs/?group=freetype"

LICENSE = "FreeTypeLicense | GPLv2+"
LIC_FILES_CHKSUM = "file://docs/LICENSE.TXT;md5=8bc1a580aeb518100d00a2dd29e68edf \
                    file://docs/FTL.TXT;md5=d479e83797f699fe873b38dadd0fcd4c \
                    file://docs/GPL.TXT;md5=8ef380476f642c20ebf40fecb0add2ec"

SECTION = "libs"

#PR = "r1"

#SRC_URI = "${SOURCEFORGE_MIRROR}/freetype/freetype-${PV}.tar.bz2 \
#           file://no-hardcode.patch"

#SRC_URI[md5sum] = "5e6510613f612809d2d7862592b92ab7"
#SRC_URI[sha256sum] = "24a4a57f3a6859887e91f90f93f754cfc7bf9ab9246a3a696435a0c6a7a1e92a"

#S = "${WORKDIR}/freetype-${PV}"

inherit autotools pkgconfig binconfig

LIBTOOL = "${S}/builds/unix/${HOST_SYS}-libtool"
EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'"
EXTRA_OEMAKE_virtclass-native = ""
EXTRA_OECONF = "--without-zlib --without-bzip2"

do_configure() {
	cd builds/unix
	libtoolize --force --copy
	aclocal -I .
	gnu-configize --force
	autoconf
	cd ${S}
	oe_runconf
}

do_configure_virtclass-native() {
	(cd builds/unix && gnu-configize) || die "failure running gnu-configize"
	oe_runconf
}

do_compile_prepend() {
	${BUILD_CC} -o objs/apinames src/tools/apinames.c
}

FILES_${PN} = "${libdir}/lib*${SOLIBS}"
FILES_${PN}-dev += "${bindir}"

BBCLASSEXTEND = "native"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

# .orig.tar.gz is doubly-compressed
do_unpack_srcpkg_append() {
	PV_SRCPKG=$(head -n 1 ${S}/debian/changelog | sed "s|.*(\([^()]*\)).*|\1|")
	PV_ORIG=$(echo $PV_SRCPKG | sed "s|-.*||")

	tar xvjf ${S}/freetype-$PV_ORIG.tar.bz2 -C ${S}
	mv ${S}/freetype-$PV_ORIG/* ${S}
	rm -r ${S}/freetype-$PV_ORIG
}

# patches exist in debian/patches-freetype (see debian/rules)
do_patch_srcpkg() {
	cd ${S}
	QUILT_PATCHES=${S}/debian/patches-freetype quilt push -a
}
