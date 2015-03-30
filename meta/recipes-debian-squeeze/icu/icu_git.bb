#
# HIDDEN/recipes-support/icu/icu_3.6.bb
#
require icu.inc

#PR = "r6"

#SRC_URI[md5sum] = "6243f7a19e03e05403ce84e597510d4c"
#SRC_URI[sha256sum] = "5135e8d69d6206d320515df7aeee7027711ab1aef9d8dbf29571a97a9746b041"
#
# debian-squeeze
#
PR = "r0"
SRC_URI += " \
           file://noldlibpath.patch;apply=yes \
	   file://rematch-gcc-bug.patch;apply=yes \
	   file://fix-make.patch;apply=yes "
DEPENDS = "icu-native"
DEPENDS_virtclass-native = ""
EXTRA_OECONF += "--with-cross-build=${TMPDIR}/work/${BUILD_SYS}/icu-native-${PV}-${PR}/${BP}/source"
do_configure_append() {
        for i in */Makefile* */*.inc */*/Makefile*  */*/*.inc.in ; do
        	sed -i -e 's:$(INVOKE) $(BINDIR)/:$(INVOKE) :g' $i
                sed -i -e 's:$(BINDIR)/::g' $i
	done
	sed -i -e 's:$(BINDIR)/::g' extra/uconv/pkgdata.inc || true
	sed -i -e 's:$(BINDIR)/::g' extra/uconv/pkgdata.inc.in || true
}

do_compile() {
        oe_runmake 'CXX=${CXX}'
}

do_install_append() {
        chmod +x ${D}${libdir}/lib*
}

PACKAGES =+ "libicudata libicuuc libicui18n libicule libiculx libicutu libicuio"

FILES_libicudata = "${libdir}/libicudata.so.*"
FILES_libicuuc = "${libdir}/libicuuc.so.*"
FILES_libicui18n = "${libdir}/libicui18n.so.*"
FILES_libicule = "${libdir}/libicule.so.*"
FILES_libiculx = "${libdir}/libiculx.so.*"
FILES_libicutu = "${libdir}/libicutu.so.*"
FILES_libicuio = "${libdir}/libicuio.so.*"

BBCLASSEXTEND = "native"

