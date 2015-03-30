#
# debian-squeeze
#
DESCRIPTION = "Common CA certificates"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://brasil.gov.br/README;md5=611471d187879e45aefd6cbc9eb549d6 "
SECTION = "misc"
PR = "r0"
DEPENDS = "debconf openssl"

inherit autotools
inherit debian-squeeze
SRC_URI += "file://fix_Makefile.patch"
