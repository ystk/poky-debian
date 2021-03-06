#
# gnash-minimal_0.8.3.bb
#

require gnash-minimal.inc

#PR = "r9"

#EXTRA_OECONF += " --without-included-ltdl \
#                  --with-ltdl-include=${STAGING_INCDIR} \
#                  --with-ltdl-lib=${STAGING_LIBDIR} \
#"

#SRC_URI += "file://libtool-2.2.patch file://libintl.patch"



#SRC_URI[md5sum] = "5033ef2602ea1234a9ccb73000a0dedb"
#SRC_URI[sha256sum] = "af1fd8454472e0ac588c015b09c67449392f32aa6297d4a625b8344dce11c39a"

#
# debian-squeeze
#

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "gnash"

PR = "r0"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=f27defe1e96c2e1ecd4e0c9be8967949"

# FIXME: Required to enable system font
DEPENDS += "fontconfig"

# If there is some way for enable "HAVE_PTHREADS" and "USE_FREETYPE, this should be deleted. 
do_compile_prepend() {
	echo "enable HAVE_PTHREADS and USE_FREETYPE"
	cat ${S}/gnashconfig.h | sed "s%\/\* #undef HAVE_PTHREADS \*\/%#define HAVE_PTHREADS 1%" \
	| sed -e "s%\/\* #undef USE_FREETYPE \*\/%#define USE_FREETYPE 1%" \
	> ${S}/gnashconfig.h.tmp
	mv ${S}/gnashconfig.h.tmp ${S}/gnashconfig.h
}
