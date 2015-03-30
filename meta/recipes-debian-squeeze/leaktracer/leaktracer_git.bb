#
# debian-squeeze
#
DESCRIPTION = "Simple and efficient memory-leak tracer for C++ programs"
HOMEPAGE = "http://www.andreasen.org/LeakTracer"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://README;md5=76a1a6940728ec0b207b0a6c3b10a62d"
SECTION = "devel"
PR = "r0"

DEPENDS += "gdb"
inherit autotools
inherit debian-squeeze

SRC_URI += "file://fix-make.patch"
do_install() {
	mkdir -p ${D}/usr/lib/leaktracer
	mkdir -p ${D}/usr/bin
	cp LeakCheck ${D}/usr/bin
	cp LeakTracer.so ${D}/usr/lib/leaktracer
	cp leak-analyze ${D}/usr/bin
}	

