#
# debian-squeeze
#

SUMMARY = "High performance data logging and graphing system for time series data"
HOMEPAGE = "http://oss.oetiker.ch/rrdtool/"

inherit debian-squeeze

PR = "r0"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=44fee82a1d2ed0676cf35478283e0aa0"

DEPENDS = "libpng zlib cairo pango glib-2.0 libxml2 groff-native"

inherit autotools gettext 

# the configure script was determined to test the config-test program on the host, 
# that abviously failed. So that patch to skip test when cross-compiling.
SRC_URI += "file://fix_config_to_skip_cross_compiling.patch"

EXTRA_OECONF = " \
    --build=${BUILD_SYS} \
    --disable-python \
    --disable-ruby \
    --with-perl-options=INSTALLDIRS=vendor \
    --enable-shared \	
    --disable-static \
    --program-prefix='' \
"
LIBTOOL = "${HOST_SYS}-libtool"
EXTRA_OEMAKE = " LIBTOOL='${LIBTOOL}'"

do_configure () {
	oe_runconf
}
