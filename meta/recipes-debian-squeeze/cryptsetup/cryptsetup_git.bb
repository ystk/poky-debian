#
# debian-squeeze 
#
DESCRIPTION = "configures encrypted block devices"
SECTION = "admin" 
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f" 
PR = "r0"
DEPENDS = "util-linux lvm2 popt libgcrypt11"

inherit autotools gettext
inherit debian-squeeze

# secure memory of cryptsetup needs to be disabled
# if gcrypt is linked with libcap (--with-capabilities)
SRC_URI = "file://disable-secmem-in-gcrypt.patch"
