require mono.inc

DEPENDS = "mono-native glib-2.0 perl-native mono-mcs-intermediate "
#PR = "${INC_PR}.1"

# mono makes use of non-thumb-compatible inline asm.
ARM_INSTRUCTION_SET = "arm"

SRC_URI += "file://configure.patch \
	   file://fix-config.patch \
           "

LIBTOOL = ${HOST_SYS}-libtool
EXTRA_OEMAKE += "'LIBTOOL=${LIBTOOL}'"
# Per http://www.mono-project.com/Mono:ARM
#EXTRA_OECONF += " --disable-mcs-build "
# Instead, get the mcs tree from a different build (see mono-mcs-intermediate)
do_configure() {
	oe_runconf
}

do_install_prepend() {
	install -d ${D}
	(cd ${D}; tar -xzf ${STAGING_DATADIR_NATIVE}/mono-mcs/mono-mcs-${PV}.tar.gz)
}

do_install_append() {
	# mono-mcs-intermediate builds and installs jay (a Yacc for Java and C#),
	# however, jay is not being cross-compiled and thus only
	# available for the buildhost architecture, so remove it
	# entirely
	(cd ${D};
	rm -rf ./usr/share/man/man1/jay.1 ./usr/share/jay \
	    ./usr/share/jay/README.jay \
	    ./usr/bin/jay)

	# Not packaged with the default rules and apparently
	# not used for anything
	rm -rf ${D}${datadir}/mono-1.0/mono/cil/cil-opcodes.xml
}

#inherit mono

# Import file definitions from Debian
require mono_2.x-files.inc

# Add some packages
PACKAGES_append = "mono-doc mono mono-runtime"

#FILES_mono-doc_append = " /usr/share/libgc-mono/ "

FILES_mono = ""
ALLOW_EMPTY_mono = "1"
RDEPENDS_mono = "mono-common mono-jit"

FILES_mono-runtime = ""
ALLOW_EMPTY_mono-runtime = "1"
RDEPENDS_mono-runtime = "mono-jit mono-gac"

RDEPENDS_mono-jit = "mono-common"

FILES_libmono-dev =+ " /usr/lib/libmono.la /usr/lib/libmono-profiler-cov.la /usr/lib/libmono-profiler-aot.la \
	/usr/lib/libMonoPosixHelper.la /usr/lib/libMonoSupportW.la"
FILES_libmono-dbg =+ " /usr/lib/.debug/libmono*.so.* /usr/lib/.debug/libikvm-native.so \
	/usr/lib/.debug/libMonoPosixHelper.so /usr/lib/.debug/libMonoSupportW.so"

# Packages not included in Debian
PACKAGES_prepend = "libnunit2.2-cil-dbg libnunit2.2-cil-dev libnunit2.2-cil \
	libmono-cecil0.5-cil-dbg libmono-cecil0.5-cil-dev libmono-cecil0.5-cil \
	libmono-db2-1.0-cil-dev"

FILES_libnunit2.2-cil = "/usr/lib/mono/gac/nunit.*/2.2.* /usr/lib/mono/1.0/nunit.*.dll"
FILES_libnunit2.2-cil-dev = "/usr/lib/pkgconfig/mono-nunit.pc"
FILES_libnunit2.2-cil-dbg = "/usr/lib/mono/gac/nunit*/2.2.*/nunit.*.dll.mdb"

FILES_libmono-cecil0.5-cil = "/usr/lib/mono/gac/Mono.Cecil/0.5.*"
FILES_libmono-cecil0.5-cil-dbg = "/usr/lib/mono/gac/Mono.Cecil/0.5.0.1__0738eb9f132ed756/Mono.Cecil.dll.mdb"

FILES_libmono-db2-1.0-cil = "/usr/lib/mono/gac/IBM.Data.DB2/1.0* /usr/lib/mono/1.0/IBM.Data.DB2.dll"
FILES_libmono-db2-1.0-cil-dbg = "/usr/lib/mono/gac/IBM.Data.DB2/1.0*/IBM.Data.DB2.dll.mdb"

# Move .pc files
FILES_libmono-cairo1.0-cil-dev = "/usr/lib/pkgconfig/mono-cairo.pc"
PACKAGES =+ " libmono-cairo1.0-cil-dev "

