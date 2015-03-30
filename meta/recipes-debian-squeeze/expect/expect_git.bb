#
# debian-squeeze
#

DESCRIPTON = "A program that can automate interactive applications"
SECTION = "interpreters"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://README;md5=2862a5993e5f43b368a49cfaad5bead6"

inherit debian-squeeze
inherit autotools

DEPENDS = "tcl8.5"

# expect-5.44.1.15-notk-1.patch:
#   remove dependency on tk
#   http://trac.cross-lfs.org/browser/patches/expect-5.44.1.15-notk-1.patch?rev=df4f9cae26caee4cfa46cd512f0d43dd06e3531d
# timesys-dont-configure-testsuites.patch:
#   solve "configure: error: ./configure failed for testsuite"
# timesys-allow-cross-compile.patch:
#   solve "configure: error: Expect can't be cross compiled"
SRC_URI = " \
file://expect-5.44.1.15-notk-1.patch \
file://timesys-dont-configure-testsuites.patch \
file://timesys-allow-cross-compile.patch \
"

# "--with-tk=no" is provieded by expect-5.44.1.15-notk-1.patch
EXTRA_OECONF += " \
--with-tcl=${STAGING_LIBDIR}/tcl8.5 \
--with-tclinclude=${STAGING_INCDIR}/tcl8.5 \
--with-tk=no \
--enable-shared \
--enable-threads \
"

EXPECT_LIBDIR = "${libdir}/expect5.44.1.15"
FILES_${PN} += "${EXPECT_LIBDIR}"
FILES_${PN}-dbg += "${EXPECT_LIBDIR}/.debug"
