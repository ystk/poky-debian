#
# debian-squeeze
#

DESCRIPTION = "GnuPG keys of the Debian archive"
SECTION = "misc"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://README;md5=334a1b9da4ce18a4aec5f027b7820331"

inherit debian-squeeze

do_install() {
	cd ${S}
	mkdir -p ${D}/var/lib/apt/keyrings
	mkdir -p ${D}/usr/share/keyrings
	cp keyrings/debian-squeeze-archive-keyring.gpg 	${D}/usr/share/keyrings
	cp keyrings/debian-squeeze-archive-removed-keys.gpg 	${D}/usr/share/keyrings
	cp keyrings/debian-squeeze-archive-keyring.gpg 	${D}/var/lib/apt/keyrings
	cd ${D}/usr/share/keyrings/
        ln -s debian-squeeze-archive-keyring.gpg archive.gpg
}

FILES_${PN} += "${datadir}/keyrings/debian-squeeze*"


