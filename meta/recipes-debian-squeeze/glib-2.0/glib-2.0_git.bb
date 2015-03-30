#
# glib-2.0_2.27.5.bb
#

require glib.inc

#PR = "r7"

#SRC_URI = "${GNOME_MIRROR}/glib/2.27/glib-${PV}.tar.bz2 \
#           file://configure-libtool.patch \
#           file://60_wait-longer-for-threads-to-die.patch \
#           file://g_once_init_enter.patch \
#          "

#SRC_URI[md5sum] = "b7025b581bf78fcd656117bfc113f21f"
#SRC_URI[sha256sum] = "aad3038db865b762e01b1dc455ffd601b4083c069018d290e5fdfe1a61d328dc"

SRC_URI_append_virtclass-native = " file://glib-gettextize-dir.patch"

# Only apply this patch for target recipe on uclibc
SRC_URI_append_libc-uclibc = " ${@['', 'file://no-iconv.patch']['${PN}' == '${BPN}']}"

BBCLASSEXTEND = "native nativesdk"

DEFAULT_PREFERENCE = "-1"

#
# debian-squeeze
#

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "glib2.0"

PR = "r0"

# "configure-libtool.patch" is required to avoid configure error.
# Target file name "configure.ac" is renamed to "configure.in".
SRC_URI = " \
file://configure-libtool.patch \
"
