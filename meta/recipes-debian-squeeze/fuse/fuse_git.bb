LICENSE = "LGPLv2 & GPLv2"
LICENSE_${PN} = "GPLv2"
LICENSE_lib${PN} = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f \
		    file://COPYING.LIB;md5=f30a9716ef3762e3467a2f62bf790f0a"

PR = "r0"

inherit debian-squeeze autotools
