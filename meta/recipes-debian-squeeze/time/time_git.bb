#
# recipes-extended/time/time_1.7.bb
#

require time.inc

#SRC_URI = "${GNU_MIRROR}/time/time-${PV}.tar.gz \
#	   file://debian.patch"

#SRC_URI[md5sum] = "e38d2b8b34b1ca259cf7b053caac32b3"
#SRC_URI[sha256sum] = "e37ea79a253bf85a85ada2f7c632c14e481a5fd262a362f6f4fd58e68601496d"
#	   file://autofoo.patch
#	   file://compile.patch
S = "${WORKDIR}/time-${PV}"

inherit autotools

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"
