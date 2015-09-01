#
# debian-squeeze
#
DESCRIPTION = "Linux Standard Base 3.2 core support package"
SECTION = "misc"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://lsb_release.1;md5=a49fc4cc872f0c469a3924e19964858e"

inherit debian-squeeze
inherit autotools
DEPENDS += ""

PACKAGES =+ "${PN}-base ${PN}-release"
ALLOW_EMPTY_${PN} = "1"
FILES_${PN}-base += "/lib/lsb/*"
FILES_${PN}-release += "${bindir} ${libdir} ${datadir} ${sysconfdir}"
RDEPENDS += "${PN}-base"
#RDEPENDS += "${PN}-release"
RPROVIDES_${PN}-base = "${PN}-base"
RDEPENDS_${PN}-release = "python-textutils python-shell"

SRC_URI += "file://init-functions"

do_compile() {
}

do_install() {
	install -d ${D}/lib/lsb
	install -m 755 ${WORKDIR}/init-functions ${D}/lib/lsb/
	install -d ${D}${bindir}
	install -m 755 lsb_release ${D}${bindir}
	install -d ${D}${libdir}/python2.6/dist-packages
	install -m 644 lsb_release.py ${D}${libdir}/python2.6/dist-packages
	install -d ${D}${datadir}/bug
	install -m 644 debian/lsb-release.bugscript ${D}${datadir}/bug/lsb-release

	if [ ! -e ${D}${sysconfdir}/lsb-release ]; then
	    install -d ${D}${sysconfdir}
	    echo "DISTRIB_ID=${DISTRO}" > ${D}${sysconfdir}/lsb-release
	    echo "DISTRIB_DESCRIPTION=${DISTRO} ${DISTRO_VERSION}" >> ${D}${sysconfdir}/lsb-release
	    echo "DISTRIB_RELEASE=${DISTRO_VERSION}" >> ${D}${sysconfdir}/lsb-release
	    echo "DISTRIB_CODENAME=${METADATA_BRANCH}" >> ${D}${sysconfdir}/lsb-release
        fi
}
