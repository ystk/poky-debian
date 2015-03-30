#
# debian-squeeze 
#

DESCRIPTION = "powerful, efficient, and scalable Mail Transport Agent" 
SECTION = "mail"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=11fc9340eac9582779bb65da57f2b076"
PR = "r0"

inherit autotools 
inherit debian-squeeze

S = "${DEBIAN_SQUEEZE_UNPACKDIR}/${DEBIAN_SQUEEZE_SRCPKG_NAME}-8.14.3"

do_unpackpost() {
	cd ${DEBIAN_SQUEEZE_UNPACKDIR}
	tar zvxf ${DEBIAN_SQUEEZE_SRCPKG_NAME}.8.14.3.tar.gz
}

do_patch_srcpkg() {
	# Apply patches according to their patch format
                if [ -f debian/patches/8.14/8.14.3/series ]; then
                        # quilt
                        if [ -s debian/patches/8.14/8.14.3/series ]; then
                                QUILT_PATCHES=debian/patches \
                                quilt --quiltrc /dev/null push -a || test $$? = 2
                        else
                                echo "$srcpkg: No patches in series"
                        fi
		fi

}

do_compile_prepend() {
	chmod +w devtools/OS/Linux
	echo "define(\`confCC', \`${HOST_SYS}-gcc')" >> devtools/OS/Linux
}

do_install_prepend() {
	mkdir -p ${D}${libdir}
	mkdir -p ${D}${includedir}
	mkdir -p ${D}${datadir}
	mkdir -p ${D}${sbindir}
	mkdir -p ${D}${bindir}
	mkdir -p ${D}/usr/man/man1

}
addtask unpackpost after do_unpack before do_patch_srcpkg
