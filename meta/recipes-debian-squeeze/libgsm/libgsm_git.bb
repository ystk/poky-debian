# debian-squeeze
#
DESCRIPTION = "Shared libraries for GSM speech compressor"
SECTION = "libs"
LICENSE = "GSM"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=fc1372895b173aaf543a122db37e04f5"

inherit debian-squeeze
inherit autotools
CFLAGS += "-c -g -fPIC -Wall -D_GNU_SOURCE -D_REENTRANT -DNeedFunctionPrototypes=1 -DWAV49 -I./inc"
do_compile_prepend(){
        sed -i "s:^CC.*:#:" Makefile
        sed -i "s:^AR.*:#:" Makefile
}
do_compile() {
        unset LD
        oe_runmake CCFLAGS="${CFLAGS}"
}


do_install() {
        oe_libinstall -a -C lib libgsm ${D}${libdir}
        oe_libinstall -so -C lib libgsm ${D}${libdir}
        install -d ${D}${includedir}/gsm
        install -m 0644 ${S}/inc/gsm.h ${D}${includedir}/gsm/
        cd ${D}${includedir}
        ln -s gsm/gsm.h gsm.h
}

