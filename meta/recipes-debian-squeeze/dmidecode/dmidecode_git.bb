#
# debian-squeeze
#
DESCRIPTION = "Dump Desktop Management Interface data"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=393a5ca445f6965873eca0259a17f833 \
		    file://README;md5=9bf9d2ef95a809361e0280e57c926751"
SECTION = "utils"
PR = "r0"

inherit autotools
inherit debian-squeeze
do_compile_prepend() {
	sed -i 's:CC      = gcc:#CC      = gcc:' Makefile
	sed -i 's:strip \: $(PROGRAMS):#strip \: $(PROGRAMS):' Makefile
	sed -i 's:strip $(PROGRAMS):#strip $(PROGRAMS):' Makefile
}

FILES_${PN} += "/usr/local/*"
