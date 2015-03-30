#
# binutils-crosssdk_2.21.1a.bb
#

require binutils-cross_${PV}.bb

inherit crosssdk

PROVIDES = "virtual/${TARGET_PREFIX}binutils-crosssdk"

#PR = "r1"

do_configure_prepend () {
	sed -i 's#/usr/local/lib /lib /usr/lib#${SDKPATHNATIVE}/lib ${SDKPATHNATIVE}/usr/lib /usr/local/lib /lib /usr/lib#' ${S}/ld/configure.tgt
}

#
# debian-squeeze
#

PR = "r0"
