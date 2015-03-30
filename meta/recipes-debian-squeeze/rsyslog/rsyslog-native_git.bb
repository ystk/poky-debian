#
# 
#
require rsyslog.inc
#PR = "${INC_PR}.0"

#SRC_URI[md5sum] = "a2c2a65ac84d9a895c52a754aff61986"
#SRC_URI[sha256sum] = "fdae6a027942f46eef8abcf2b6b28399986b5e983452e046e729eae70f13febe"
#
# debian-squeeze
#
inherit debian-squeeze native
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=51d9635e646fb75e1b74c074f788e973"
PR = "r0"

SRC_URI = "file://rsyslog.conf \
          file://initscript"
DEPENDS += "zlib lsb"
