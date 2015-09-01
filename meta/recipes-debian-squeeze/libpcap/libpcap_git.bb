#
# recipes-connectivity/libpcap/libpcap_1.1.1.bb
#

require libpcap.inc

#PR = "r0"

#SRC_URI += "file://aclocal.patch \
#            file://ieee80215-arphrd.patch"

#SRC_URI[md5sum] = "1bca27d206970badae248cfa471bbb47"
#SRC_URI[sha256sum] = "508cca15547e55d1318498b838456a21770c450beb2dc7d7d4a96d90816e5a85"

#
# make install doesn't cover the shared lib
# make install-shared is just broken (no symlinks)
#
do_install_prepend () {
    install -d ${D}${libdir}
    install -d ${D}${bindir}
    oe_runmake install-shared DESTDIR=${D}
    oe_libinstall -a -so libpcap ${D}${libdir}
}

#
# debian-squeeze
#

inherit debian-squeeze

# pcap-config must be installed into sysroots for other packages
# e.g. tcpdump
inherit binconfig

PR = "r0"

# rules for shared libraries, which are added by 10_shared_lib.diff,
# have parallel make problem; do_install fails only when
# $(OBJ) are built after $(SHAREDLIB).
PARALLEL_MAKE = ""
