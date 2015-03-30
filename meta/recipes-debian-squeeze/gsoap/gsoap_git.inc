#
# gsoap/gsoap_2.7.13.inc
# This recipe is imported from OpenEmbedded
# Commit: 86ff745847ebfcfb77730851b72a7d576422c660
#
DESCRIPTION = "The gSOAP toolkit provides a unique SOAP-to-C/C++ language binding \
for the development of SOAP Web Services and clients."
SECTION = "devel"
LICENSE = "GPLv2 & MPLv1.1"
PR = "r0"

#SRC_URI = "${SOURCEFORGE_MIRROR}/gsoap2/gsoap_${PV}.tar.gz \
#        file://gsoap-2.7-openssl.patch \
#        file://unused_args.patch \
#        "

inherit autotools

#S = "${WORKDIR}/gsoap-2.7"

PARALLEL_MAKE = ""

EXTRA_OEMAKE = "SOAP=${STAGING_BINDIR_NATIVE}/soapcpp2"
#
# debian-squeeze
#
inherit debian-squeeze
LIC_FILES_CHKSUM = "file://README.txt;md5=0a7058cb96a5a0553dffa9392ab919dd"
