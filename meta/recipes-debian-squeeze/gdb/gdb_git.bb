#
# gdb_7.3a.bb
#

require gdb.inc
LICENSE="GPLv2 & GPLv3 & LGPLv2 & LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552 \
		    file://COPYING3;md5=d32239bcb673463ab874e80d47fae504 \
		    file://COPYING3.LIB;md5=6a6a8e020838b23406c81b19c1d46df6 \
		    file://COPYING.LIB;md5=9f604d8a4f8e74f4f5140845a21b6674"
#PR = "${INC_PR}.0"

#S = "${WORKDIR}/${BPN}-7.3"

#
# debian-squeeze
#

PR = "r0"
