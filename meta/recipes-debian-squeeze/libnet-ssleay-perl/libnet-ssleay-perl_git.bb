DESCRIPTION = "Perl module for Secure Sockets Layer"
HOMEPAGE = "http://search.cpan.org/dist/Net-SSLeay/"
SECTION = "perl"
LICENSE = "GPL-1"
LIC_FILES_CHKSUM = "file://README;md5=080c2a6308efa51fcaa3e6381ac4c384"
PR = "r0"

DEPENDS += " openssl"
inherit debian-squeeze cpan
