
#
# debian-squeeze
#
DESCRIPTION = "advanced text-mode WWW browser"
SECTION = "web"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=21b4b392265b0da6fb0afa2d284ff668"
PR = "r0"


inherit debian-squeeze
inherit autotools gettext
DEPENDS += "xz-utils"
EXTRA_OECONF += "--without-lzma"
do_patch_srcpkg() {
   cd ${S}
   for patch in $(ls ${S}/debian/patches/*); do
      patch -p1 < $patch
   done
}
do_configure_prepend() {
   sed -i 's|features="features.conf"||' configure.in
   sed -i "s|AC_CHECK_FILE.*||" configure.in
   sed -i 's|echo "Feature summary:" > features.log||' configure.in
}
