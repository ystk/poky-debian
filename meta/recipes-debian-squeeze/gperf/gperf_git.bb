#
# gperf_3.0.3.bb
#

require gperf.inc

LICENSE  = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=a5f84abb0070adf8a0925d7d534b6e0a \
                    file://src/main.cc;firstline=8;endline=19;md5=3eab12ba436c4da725b19b131def6de9"

#PR = "r1"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

SRC_URI += "file://configure.ac"

# git fetch: Remove "doc" from subdirs
do_configure_prepend() {
	cp ${WORKDIR}/configure.ac ${S}
}
