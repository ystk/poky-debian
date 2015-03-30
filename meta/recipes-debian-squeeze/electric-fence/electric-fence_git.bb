LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=18810669f13b87348459e611d31ab760"

inherit debian-squeeze autotools

PR = "r0"

SRC_URI = "file://fix-makefile.patch"

do_compile() {
	CFLAGS_ORG=$CFLAGS

	# Build "*.so.*".
	CFLAGS="$CFLAGS -fPIC"
	oe_runmake
	${CC} -g -shared -Wl,-soname,libefence.so.0 \
		-o libefence.so.0.0 \
		efence.o page.o print.o \
		-lc -lpthread
	rm *.o

	# Build "*.a".
	CFLAGS=$CFLAGS_ORG
	oe_runmake
}

do_install() {
	install -d ${D}/${libdir}
	install -m 0644 libefence.a ${D}/${libdir}
	install -m 0644 libefence.so.0.0 ${D}/${libdir}
	ln -s libefence.so.0.0 ${D}/${libdir}/libefence.so.0
	ln -s libefence.so.0 ${D}/${libdir}/libefence.so
}

FILES_${PN}-dev += "${libdir}/libefence.a"
