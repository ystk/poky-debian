#
# debian-squeeze
#

DESCRIPTION = "console font and keymap setup program"
SECTION = "console/utils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = " \
file://GPL-2;md5=4325afd396febcb659c36b49533135d4 \
file://README;md5=849a4e98bd5a3faf1efba51fbd7f39c8 \
"

inherit debian-squeeze
inherit autotools

PR = "r0"
do_compile_prepend() {
	cp ${WORKDIR}/console-setup-git/Keyboard/KeyboardNames.pl ${WORKDIR}/console-setup-git/Keyboard/MyKeyboardNames.pl 
}

DEPENDS = "xfonts-terminus bdfresize-native"

SRC_URI = "file://fix_Makefile.patch"

FILES_${PN} += "/usr/local/bin/* \
	       /usr/local/share/console-setup/* \
	       /usr/local/share/consolefonts/*"
