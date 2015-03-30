DESCRIPTION = "mechanism-switch gssapi library"
SECTION = "libs"
LICENSE = "BSD-3-Clause & MIT-style@"
LIC_FILES_CHKSUM = "file://COPYING;md5=56871e72a5c475289c0d5e4ba3f2ee3a"

inherit autotools debian-squeeze

DEPENDS += " krb5"
