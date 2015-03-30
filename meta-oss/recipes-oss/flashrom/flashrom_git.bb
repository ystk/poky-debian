SUMMARY = "Utility for controlling flash chips"
DESCRIPTION = "flashrom is a utility for identifying, \
reading, writing, verifying and erasing flash chips. \
It is designed to flash BIOS/EFI/coreboot/firmware/optionROM \
images on mainboards, network/graphics/storage controller cards, \
and various other programmer devices."
HOMEPAGE = "http://flashrom.org/Flashrom"
SECTION = "electronics"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

PR = "r0"

inherit debian-squeeze-misc
inherit autotools

DEBIAN_SQUEEZE_MISC_GIT = "${DEBIAN_SQUEEZE_GIT_OSS}"
DEBIAN_SQUEEZE_MISC_COMMIT = "master"

SRC_URI = ""

DEPENDS = "pciutils libftdi"

# /usr/local is the default value
EXTRA_OEMAKE = "PREFIX=/usr"

# no configure
do_configure() {
	:
}

PKG_SRC_CATEGORY = "oss"
