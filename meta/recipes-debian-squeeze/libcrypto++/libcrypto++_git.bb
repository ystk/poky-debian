#
# debian-squeeze 
#

DESCRIPTION = "General purpose cryptographic library" 
HOMEPAGE = "http://www.cryptopp.com"
SECTION = "libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = " \
file://License.txt;md5=e3f7406e1a28514d3a8617d70b77aee5 \
file://Readme.txt;md5=61227c8a9835975b71f5793e5456aaa1 \
"

PR = "r0"

inherit debian-squeeze

EXTRA_OEMAKE += "-f GNUmakefile PREFIX=${D}/usr"

# Replace PACKAGE_DATA_DIR by real prefix.
# It is added by debian/patches/cryptest-data-files-location.diff.
do_patch_srcpkg_append() {
	for file in $(grep -rl "PACKAGE_DATA_DIR" ${S} | sort | uniq); do
		sed -i "s@PACKAGE_DATA_DIR@\"/usr/share/crypto++/\"@g" $file
	done
}

# filename "Makefile" is hard corded
do_compile() {
	oe_runmake
}

do_install() {
	oe_runmake DESTDIR="${D}" install
}
