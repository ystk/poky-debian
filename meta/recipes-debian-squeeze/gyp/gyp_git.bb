#
# debian-squeeze
#
DESCRIPTION = "Generate Your Projects"
SECTION = "python"
HOMEPAGE = "http://code.google.com/p/gyp/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ab828cb8ce4c62ee82945a11247b6bbd"
PR = "r0"

inherit debian-squeeze 
inherit distutils

BBCLASSEXTEND = "native"

SRC_URI += " \
file://add_GetFlavor.patch \
file://fix_cd_action.patch \
"

do_compile_prepend() {
	sed -i -e "s@start-group@whole-archive@g" pylib/gyp/generator/make.py
	sed -i -e "s@end-group@no-whole-archive@g" pylib/gyp/generator/make.py
}
