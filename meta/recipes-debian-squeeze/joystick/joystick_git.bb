#
# debian-squeeze
#

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Makefile;beginline=10;endline=26;md5=687aedebce6d08ed73eb4b2f0949c763"

inherit debian-squeeze

DEPENDS = "libsdl"

S = "${WORKDIR}/${BPN}-${PV}/utils"

do_configure() {
	:
}

BINS = " \
evtest \
inputattach \
ffcfstress \
ffmvforce \
ffset \
fftest \
jscal \
jskeepalive \
jstest \
"

do_install() {
	install -d ${D}/${bindir}
	for bin in ${BINS}; do
		install -m 0755 ${B}/$bin ${D}/${bindir}
	done
}

PACKAGES =+ "evtest inputattach"

FILES_evtest = "${bindir}/evtest"
FILES_inputattach = "${bindir}/inputattach"
