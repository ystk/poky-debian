DESCRIPTION = "Strict and Targeted variants of the SELinux policy"
SECTION = "admin"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

inherit autotools
inherit debian-squeeze

PR = "0"

S = "${WORKDIR}/git"

