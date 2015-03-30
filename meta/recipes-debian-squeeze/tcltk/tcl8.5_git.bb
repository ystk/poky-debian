DESCRIPTION = "Tool Command Language"
HOMEPAGE = "http://tcl.sourceforge.net"
SECTION = "devel/tcltk"
LICENSE = "tcl"
#PR = "r9"

#SRC_URI = "\
#  ${SOURCEFORGE_MIRROR}/tcl/tcl${PV}-src.tar.gz \
#  file://confsearch.diff;striplevel=2 \
#  file://manpages.diff;striplevel=2 \
#  file://non-linux.diff;striplevel=2 \
#  file://rpath.diff;striplevel=2 \
#  file://tcllibrary.diff;striplevel=2 \
#  file://tclpackagepath.diff;striplevel=2 \
#  file://tclprivate.diff;striplevel=2 \
#  file://mips-tclstrtod.patch;patchdir=..;striplevel=0 \
#"
#SRC_URI[md5sum] = "7f123e53b3daaaba2478d3af5a0752e3"
#SRC_URI[sha256sum] = "6b090c1024038d0381e1ccfbd6d5c0f0e6ef205269ceb9d28bd7bd7ac5bbf4a7"

#S = "${WORKDIR}/tcl${PV}/unix"

inherit autotools binconfig

EXTRA_OECONF = "--enable-threads"

do_configure_append() {
        echo > ../compat/fixstrtod.c
}

#do_compile_prepend_pn-tcl () {
#        sed -i -e 's:./tclsh :tclsh :g' Makefile
#}

#do_install_append() {
#        # Stage a few extra headers to make tk happy
#        install -d ${D}${includedir}/tcl-${PV}/generic
#        install -m 0644 ../generic/*.h ${D}${includedir}/tcl-${PV}/generic
#        install -m 0644 *.h ${D}${includedir}/tcl-${PV}/generic
#        install -d ${D}${includedir}/tcl-${PV}/unix
#        install -m 0644 *Unix*.h ${D}${includedir}/tcl-${PV}/unix/
#        rm -f ${D}${includedir}/regex.h
#        ln -sf tclsh8.5 ${D}${bindir}/tclsh
#	# trick: We set it to incorrect value but binconfig will fix it
#	# correctly for both target and staging package.
#	sed -i 's:${includedir}/tcl-private:${STAGING_INCDIR}/tcl-${PV}:' ${D}${libdir}/tclConfig.sh
#}

FILES_${PN} += "${libdir}/tcl*"

BINCONFIG_GLOB = "*Config.sh"
BBCLASSEXTEND = "native"

#
# debian-squeeze
#

inherit debian-squeeze
LIC_FILES_CHKSUM = "file://README;md5=71d96d8ceba168afed00e0f7dcc0b265"
PR = "r0"

SRC_URI = "\
file://non-linux.diff;striplevel=2 \
file://rpath.diff;striplevel=2 \
file://mips-tclstrtod.patch;patchdir=..;striplevel=0 \
"

S = "${WORKDIR}/${PN}-${PV}/unix"

# 1. use tclsh installed in native system instead of a target binary (./tclsh)
# 2. remove symlink ${S}/libtcl8.5.so.0 for tclsh not to link it
do_compile_prepend() {
	sed -i \
		-e 's:./tclsh :tclsh :g' \
		-e 's@ln -sf "$(LIB_INSTALL_DIR)"/$(LIB_FILE).0 ./@# library link removed@' \
		${S}/Makefile.in
}

do_install_append() {
        # Stage a few extra headers to make tk happy
        install -d ${D}${includedir}/${PN}/generic
        install -m 0644 ../generic/*.h ${D}${includedir}/${PN}/generic
        install -m 0644 *.h ${D}${includedir}/${PN}/generic
        install -d ${D}${includedir}/${PN}/unix
        install -m 0644 *Unix*.h ${D}${includedir}/${PN}/unix/
        rm -f ${D}${includedir}/regex.h
        ln -sf tclsh8.5 ${D}${bindir}/tclsh
	# trick: We set it to incorrect value but binconfig will fix it
	# correctly for both target and staging package.
	sed -i 's:${includedir}/tcl-private:${STAGING_INCDIR}/${PN}:' ${D}${libdir}/tclConfig.sh
        mv ${D}${libdir}/tclConfig.sh ${D}${libdir}/tcl8.5/
}

# ${PN}-dev must have higher priority than ${PN} to include tclConfig.sh
PACKAGES = "${PN}-dbg ${PN}-doc ${PN}-dev ${PN}-staticdev ${PN}-locale ${PN}"
FILES_${PN}-dev += "${libdir}/tcl8.5/tclConfig.sh"
