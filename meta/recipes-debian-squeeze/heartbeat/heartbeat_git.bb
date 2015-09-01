#
# debian-squeeze
#

DESCRIPTION = "Subsystem for High-Availability Linux"
LICENSE = "GPLv2 & LGPLv2"
LIC_FILES_CHKSUM = "file://README.in;md5=1f3097ac2ebc9d5a036564b502b8951d"
SECTION = "admin"
PR = "r0"

inherit autotools
inherit debian-squeeze

DEPENDS += "glib-2.0 cluster-glue libtool net-snmp perl net-tools python libxml2 openssh openhpi"

# use native compiler because it requires to excute binary files
# to extract define while configuring,
# so apply fix_extract_header_define.patch
SRC_URI = " \
file://fix_config1.patch \
file://fix_config2.patch \
file://fix_config3.patch \
file://fix_make.patch \
file://fix-ftbfs-lp1188428.patch \
file://fix_extract_header_define.patch \
"

do_configure_prepend() {
	# export flag so compiler can find the header
        export CFLAGS=" -I${STAGING_INCDIR} "
        # fix directory for installing library
        sed -i -e "s:LibDirSuffix=lib64:LibDirSuffix=lib:" configure.in

	./bootstrap
}

# remove ${STAGING_DIR_NATIVE} from python paths extracted from @PYTHON@,,
# which is a native python path in sysroot
do_configure_append() {
	sed -i "s|^\#\!${STAGING_DIR_NATIVE}\(/.*/python\)|\#\!\1|" \
		${S}/cts/*.py ${S}/heartbeat/lib/ha_propagate
	sed -i "s@\(CRMTEST\)=\"${STAGING_DIR_NATIVE}\(/.*/python\)@\1=\"\2@" \
		${S}/heartbeat/lib/BasicSanityCheck
}

do_compile_prepend() {
	sed -i 's:\-Werror::g' $(find -name "Makefile")
	sed -i 's:#include <ipc.h>:#include <sys/ipc.h>:' membership/ccm/ccm.h
	sed -i 's:#include <base64.h>:#include <clplumbing/base64.h>:' membership/ccm/ccm.h
	sed -i 's:#include <ipc.h>:#include <sys/ipc.h>:' membership/ccm/ccmlib.h
	sed -i 's:#include <base64.h>:#include <clplumbing/base64.h>:' membership/ccm/ccmlib.h
}

LIBTOOL = "${HOST_SYS}-libtool"
EXTRA_OEMAKE = "'LIBTOOL=${LIBTOOL}'"

# plugins for heartbeat in cluster-glue
RDEPENDS += " \
cluster-glue \
cluster-glue-plugin-interfacemgr \
cluster-glue-plugin-raexec \
libltdl \
bzip2\
"

do_install_append() {
        # because daemon require these
        install -m 0644 ${S}/doc/ha.cf ${D}${sysconfdir}/ha.d
        install -m 0600 ${S}/doc/authkeys ${D}${sysconfdir}/ha.d
        install -m 0600 ${S}/doc/haresources ${D}${sysconfdir}/ha.d
}

FILES_${PN}-dbg += "${libdir}/heartbeat/plugins/*/.debug/"
