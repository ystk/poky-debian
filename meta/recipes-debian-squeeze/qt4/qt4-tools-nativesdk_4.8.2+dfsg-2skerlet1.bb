#
# qt4-tools-nativesdk_4.6.3.bb
#

require qt4-tools-nativesdk.inc

#PR = "${INC_PR}.0"

#SRC_URI[md5sum] = "5c69f16d452b0bb3d44bc3c10556c072"
#SRC_URI[sha256sum] = "f4e0ada8d4d516bbb8600a3ee7d9046c9c79e38cd781df9ffc46d8f16acd1768"

#
# debian-squeeze
#

PR = "r0"

do_configure_prepend() {
	# Avoid problems with Qt 4.8.0 configure setting QMAKE_LINK from LD (since we want the linker to be g++)
	unset LD

	if [ -f mkspecs/common/g++-base.conf ] ; then
		# don't use host g++ even during configure (4.8.0+)
		sed -i -e "s#g++#${CXX}#" mkspecs/common/g++-base.conf
		sed -i -e "s#gcc#${CC}#" mkspecs/common/g++-base.conf
	fi
}
