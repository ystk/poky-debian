#
# HIDDEN/recipes-connectivity/iproute2/iproute2_2.6.38.bb
#
require iproute2.inc

PR = "r0"

#SRC_URI = "http://developer.osdl.org/dev/iproute2/download/${BPN}-${PV}.tar.bz2 \

#SRC_URI[md5sum] = "a243bfea837e71824b7ca26c3bb45fa8"
#SRC_URI[sha256sum] = "47629a4f547f21d94d8e823a87dd8e13042cadecefea2e2dc433e4134fa9aec4"
#
#debian-squeeze
#

inherit autotools debian-squeeze
SRC_URI = "file://configure-cross.patch \
        file://fix-src.patch \
        file://db_185.h \
        file://if_packet.h "

do_configure_prepend() {
        cd ${WORKDIR}
        cp db_185.h ${S}/include
        cp if_packet.h ${S}/include
        sed -i 's:HOSTCC = gcc::' ${S}/Makefile
        sed -i 's:CC = gcc::' ${S}/Makefile
}
do_compile_prepend() {
        cd ${S}
        for dir in ip misc tc; do
                cp ${dir}/Makefile{,.orig}
                sed 's/0755 -s/0755/' ${dir}/Makefile.orig > ${dir}/Makefile
        done
        cp misc/Makefile{,.orig}
        sed '/^TARGETS/s@arpd@@g' misc/Makefile.orig > misc/Makefile
        sed -i 's:HOSTCC ?= $(CC):HOSTCC ?= '${CCACHE}''${BUILD_PREFIX}'gcc '${BUILD_CC_ARCH}':' netem/Makefile
}
