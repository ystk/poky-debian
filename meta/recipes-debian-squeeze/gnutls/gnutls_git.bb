#
# HIDDEN/recipes-support/gnutls/gnutls_2.12.5.bb
#
require gnutls.inc

PR = "${INC_PR}.0"

#SRC_URI += "file://makefile.patch \"
#            file://configure-fix.patch"

#SRC_URI[md5sum] = "2d0bd5ae11534074fcd78da6ea384e64"
#SRC_URI[sha256sum] = "bf263880f327ac34a561d8e66b5a729cbe33eea56728bfed3406ff2898448b60"
DEBIAN_SQUEEZE_SRCPKG_NAME = "gnutls26"
