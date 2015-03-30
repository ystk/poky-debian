#
# debian-squeeze
#
DESCRIPTION = "POSIX-compliant shell"
SECTION = "shell"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5262b4a1a1bff72b48e935531976d2e"
PR = "r0"

DEPENDS = " debianutils"
EXTRA_OECONF += "--sysconfdir=${STAGING_DIR_HOST}/etc --prefix=${STAGING_DIR_HOST}/usr --mandir=${STAGING_DIR_HOST}/usr/share/man"

inherit autotools
inherit debian-squeeze
