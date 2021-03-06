#
# pkgconfig_0.25.bb
#

require pkgconfig.inc

#PR = "r0"

#SRC_URI[md5sum] = "a3270bab3f4b69b7dc6dbdacbcae9745"
#SRC_URI[sha256sum] = "3ba691ee2431f32ccb8efa131e59bf23e37f122dc66791309023ca6dcefcd10e"

#
# debian-squeeze
#

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "pkg-config"

PR = "r0"

# glibconfig-sysdefs.h: Used in "do_configure_prepend"
# autofoo.patch: Required to avoid configure error
SRC_URI = " \
file://autofoo.patch \
file://glibconfig-sysdefs.h \
"
