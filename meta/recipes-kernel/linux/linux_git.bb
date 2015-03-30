inherit kernel debian-squeeze-linux

SECTION = "kernel"
DESCRIPTION = "Debian Linux kernel"

LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PR = "r0"

DEPENDS += "bc-native"

require linux-dtb.inc
#require linux-perf.inc
