LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://tipc_config.h;md5=97f683bc903f6997d2da6021e50f59af"

PR = "r0"

inherit debian-squeeze autotools

SRC_URI = " \
file://create-install-dir.patch \
"
