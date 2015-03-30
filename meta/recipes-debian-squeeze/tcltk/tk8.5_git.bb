DESCRIPTION = "Tool Command Language ToolKit Extension"
HOMEPAGE = "http://tcl.sourceforge.net"
SECTION = "devel/tcltk"
LICENSE = "tcl"
#DEPENDS = "tcl virtual/libx11 libxt"
#PR = "r3"

#SRC_URI = "\
#  ${SOURCEFORGE_MIRROR}/tcl/tk${PV}-src.tar.gz \
#  file://confsearch.diff;striplevel=2 \
#  file://manpages.diff;striplevel=2 \
#  file://non-linux.diff;striplevel=2 \
#  file://rpath.diff;striplevel=2 \
#  file://tklibrary.diff;striplevel=2 \
#  file://tkprivate.diff;striplevel=2 \
#  file://fix-xft.diff \
#"
#SRC_URI[md5sum] = "13bf90602e16fc530e05196431021dc6"
#SRC_URI[sha256sum] = "9737da5c30e631281062b6acbb4753840f9e95657c78e37657d9c520589ab2d4"

#S = "${WORKDIR}/tk${PV}/unix"

inherit autotools

EXTRA_OECONF = "\
  --enable-threads \
  --with-tcl=${STAGING_BINDIR_CROSS} \
  --x-includes=${STAGING_INCDIR} \
  --x-libraries=${STAGING_LIBDIR} \
"

do_install_append() {
        mv libtk8.5.so libtk8.5.so.0
        oe_libinstall -so libtk8.5 ${D}${libdir}
        ln -sf wish8.5 ${D}${bindir}/wish
}

#PACKAGES =+ "${PN}-lib"

FILES_${PN} += "${libdir}/libtk8.5.so.0 \
	       ${libdir}/tk*"
FILES_${PN}-dev += "${libdir}/tk8.5/tkConfig.sh \
		  ${libdir}/libtk8.5.so \
		  ${includedir}/*"
RDEPENDS += "${PN}-dev"
RPROVIDES_${PN}-dev = "${PN}-dev"

BINCONFIG_GLOB = "*Config.sh"
BBCLASSEXTEND = "native"

#
# debian-squeeze
#
inherit debian-squeeze
LIC_FILES_CHKSUM = "file://README;md5=dd07f137f768cfbc672b36aea2b37368"
DEPENDS = "tcl8.5 virtual/libx11 libxt"
PR = "r0"
S = "${WORKDIR}/${PN}-${PV}/unix"
SRC_URI = "\
  file://non-linux.diff;striplevel=2 \
  file://rpath.diff;striplevel=2 \
  file://fix-xft.diff \
"
do_compile_prepend() {
	# fix include path for tcl headers
	sed -i 's:/usr/include/tcl-private/:${STAGING_INCDIR}/tcl8.5/:' Makefile
}

do_install_append() {
        # Stage a few extra headers to make expect
        install -d ${D}${includedir}/${PN}/generic
        install -m 0644 ../generic/*.h ${D}${includedir}/${PN}/generic
        install -m 0644 *.h ${D}${includedir}/${PN}/generic
        install -d ${D}${includedir}/${PN}/unix
        install -m 0644 *Unix*.h ${D}${includedir}/${PN}/unix/
        # trick: We set it to incorrect value but binconfig will fix it
        # correctly for both target and staging package.
        sed -i 's:${includedir}/tk-private:${STAGING_INCDIR}/${PN}:' ${D}${libdir}/tkConfig.sh
        mv ${D}${libdir}/tkConfig.sh ${D}${libdir}/tk8.5/tkConfig.sh
}

