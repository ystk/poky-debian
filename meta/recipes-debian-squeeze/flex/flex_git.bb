#
# flex_2.5.35.bb
#

require flex.inc
#PR = "r2"
LICENSE="BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=e4742cf92e89040b39486a6219b68067"
BBCLASSEXTEND = "native"

#SRC_URI[md5sum] = "10714e50cea54dc7a227e3eddcd44d57"
#SRC_URI[sha256sum] = "0becbd4b2b36b99c67f8c22ab98f7f80c9860aec70f0350a0018f29a88704e7b"

#
# debian-squeeze
#

inherit debian-squeeze

# git fetch: Avoid re-generating doc/flex.1
# in order not to use help2man in native build
do_configure_prepend() {
	touch ${S}/doc/flex.1
}
