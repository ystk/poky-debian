#
# libcap_2.22.bb
#

require libcap.inc

#PR = "r1"

#SRC_URI[md5sum] = "ce64058bdb3f086ddbfca8ce6c919845"
#SRC_URI[sha256sum] = "73ebbd4877b5f69dd28b72098e510c5b318bc480f8201c4061ac98b78c04050f"

#
# debian-squeeze
#

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "libcap2"

PR = "r0"
