#
# tzdata_2011i.bb
#

DESCRIPTION = "Timezone data"
HOMEPAGE = "ftp://elsie.nci.nih.gov/pub/"
SECTION = "base"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://asia;beginline=1;endline=2;md5=06468c0e84ef4d4c97045a4a29b08234"
#DEPENDS = "tzcode-native"

#PR = "r0"

#RCONFLICTS= "timezones timezone-africa timezone-america timezone-antarctica \
#             timezone-arctic timezone-asia timezone-atlantic \
#             timezone-australia timezone-europe timezone-indian \
#             timezone-iso3166.tab timezone-pacific timezone-zone.tab"

#SRC_URI = "ftp://elsie.nci.nih.gov/pub/tzdata${PV}.tar.gz;name=tzdata"

#SRC_URI[tzdata.md5sum] = "c7a86ec34f30f8d6aa77ef94902a3047"
#SRC_URI[tzdata.sha256sum] = "f8dde7ca5e61f21ac34c8cdbef5568d00c829981211098f059d8104964c81ffa"

#S = "${WORKDIR}"

TZONES= "africa antarctica asia australasia europe northamerica southamerica  \
         factory etcetera backward systemv \
        "
# pacificnew 

#do_compile () {
#        for zone in ${TZONES}; do \
#            ${STAGING_BINDIR_NATIVE}/zic -d ${WORKDIR}${datadir}/zoneinfo -L /dev/null \
#                -y ${S}/yearistype.sh ${S}/${zone} ; \
#            ${STAGING_BINDIR_NATIVE}/zic -d ${WORKDIR}${datadir}/zoneinfo/posix -L /dev/null \
#                -y ${S}/yearistype.sh ${S}/${zone} ; \
#            ${STAGING_BINDIR_NATIVE}/zic -d ${WORKDIR}${datadir}/zoneinfo/right -L ${S}/leapseconds \
#                -y ${S}/yearistype.sh ${S}/${zone} ; \
#        done
#}

#do_install () {
#        install -d ${D}/usr ${D}${datadir}/zoneinfo
#        cp -pPR ${S}/usr ${D}/
#        # libc is removing zoneinfo files from package
#        cp -pP "${S}/zone.tab" ${D}${datadir}/zoneinfo
#        cp -pP "${S}/iso3166.tab" ${D}${datadir}/zoneinfo
#	chown -R root:root ${D}
#}

# Packages primarily organized by directory with a major city
# in most time zones in the base package

#PACKAGES = "tzdata tzdata-misc tzdata-posix tzdata-right tzdata-africa \
#    tzdata-americas tzdata-antarctica tzdata-arctic tzdata-asia \
#    tzdata-atlantic tzdata-australia tzdata-europe tzdata-pacific"
#
#FILES_tzdata-africa += "${datadir}/zoneinfo/Africa/*"
#RPROVIDES_tzdata-africa = "tzdata-africa"
#
#FILES_tzdata-americas += "${datadir}/zoneinfo/America/*  \
#                ${datadir}/zoneinfo/US/*                \
#                ${datadir}/zoneinfo/Brazil/*            \
#                ${datadir}/zoneinfo/Canada/*            \
#                ${datadir}/zoneinfo/Mexico/*            \
#                ${datadir}/zoneinfo/Chile/*"
#RPROVIDES_tzdata-americas = "tzdata-americas"
#
#FILES_tzdata-antarctica += "${datadir}/zoneinfo/Antarctica/*"
#RPROVIDES_tzdata-antarctica = "tzdata-antarctica"
#
#FILES_tzdata-arctic += "${datadir}/zoneinfo/Arctic/*"
#RPROVIDES_tzdata-arctic = "tzdata-arctic"
#
#FILES_tzdata-asia += "${datadir}/zoneinfo/Asia/*        \
#                ${datadir}/zoneinfo/Indian/*            \
#                ${datadir}/zoneinfo/Mideast/*"
#RPROVIDES_tzdata-asia = "tzdata-asia"
#
#FILES_tzdata-atlantic += "${datadir}/zoneinfo/Atlantic/*"
#RPROVIDES_tzdata-atlantic = "tzdata-atlantic"
#
#FILES_tzdata-australia += "${datadir}/zoneinfo/Australia/*"
#RPROVIDES_tzdata-australia = "tzdata-australia"
#
#FILES_tzdata-europe += "${datadir}/zoneinfo/Europe/*"
#RPROVIDES_tzdata-europe = "tzdata-europe"
#
#FILES_tzdata-pacific += "${datadir}/zoneinfo/Pacific/*"
#RPROVIDES_tzdata-pacific = "tzdata-pacific"
#
#FILES_tzdata-posix += "${datadir}/zoneinfo/posix/*"
#RPROVIDES_tzdata-posix = "tzdata-posix"
#
#FILES_tzdata-right += "${datadir}/zoneinfo/right/*"
#RPROVIDES_tzdata-right = "tzdata-right"
#
#
#FILES_tzdata-misc += "${datadir}/zoneinfo/Cuba           \
#                ${datadir}/zoneinfo/Egypt                \
#                ${datadir}/zoneinfo/Eire                 \
#                ${datadir}/zoneinfo/Factory              \
#                ${datadir}/zoneinfo/GB-Eire              \
#                ${datadir}/zoneinfo/Hongkong             \
#                ${datadir}/zoneinfo/Iceland              \
#                ${datadir}/zoneinfo/Iran                 \
#                ${datadir}/zoneinfo/Israel               \
#                ${datadir}/zoneinfo/Jamaica              \
#                ${datadir}/zoneinfo/Japan                \
#                ${datadir}/zoneinfo/Kwajalein            \
#                ${datadir}/zoneinfo/Libya                \
#                ${datadir}/zoneinfo/Navajo               \
#                ${datadir}/zoneinfo/Poland               \
#                ${datadir}/zoneinfo/Portugal             \
#                ${datadir}/zoneinfo/Singapore            \
#                ${datadir}/zoneinfo/Turkey"
#RPROVIDES_tzdata-misc = "tzdata-misc"
#
#
#FILES_${PN} += "${datadir}/zoneinfo/Pacific/Honolulu     \
#                ${datadir}/zoneinfo/America/Anchorage    \
#                ${datadir}/zoneinfo/America/Los_Angeles  \
#                ${datadir}/zoneinfo/America/Denver       \
#                ${datadir}/zoneinfo/America/Chicago      \
#                ${datadir}/zoneinfo/America/New_York     \
#                ${datadir}/zoneinfo/America/Caracas      \
#                ${datadir}/zoneinfo/America/Sao_Paulo    \
#                ${datadir}/zoneinfo/Europe/London        \
#                ${datadir}/zoneinfo/Europe/Paris         \
#                ${datadir}/zoneinfo/Africa/Cairo         \
#                ${datadir}/zoneinfo/Europe/Moscow        \
#                ${datadir}/zoneinfo/Asia/Dubai           \
#                ${datadir}/zoneinfo/Asia/Karachi         \
#                ${datadir}/zoneinfo/Asia/Dhaka           \
#                ${datadir}/zoneinfo/Asia/Bankok          \
#                ${datadir}/zoneinfo/Asia/Hong_Kong       \
#                ${datadir}/zoneinfo/Asia/Tokyo           \
#                ${datadir}/zoneinfo/Australia/Darwin     \
#                ${datadir}/zoneinfo/Australia/Adelaide   \
#                ${datadir}/zoneinfo/Australia/Brisbane   \
#                ${datadir}/zoneinfo/Australia/Sydney     \
#                ${datadir}/zoneinfo/Pacific/Noumea       \
#                ${datadir}/zoneinfo/CET                  \
#                ${datadir}/zoneinfo/CST6CDT              \
#                ${datadir}/zoneinfo/EET                  \
#                ${datadir}/zoneinfo/EST                  \
#                ${datadir}/zoneinfo/EST5EDT              \
#                ${datadir}/zoneinfo/GB                   \
#                ${datadir}/zoneinfo/GMT                  \
#                ${datadir}/zoneinfo/GMT+0                \
#                ${datadir}/zoneinfo/GMT-0                \
#                ${datadir}/zoneinfo/GMT0                 \
#                ${datadir}/zoneinfo/Greenwich            \
#                ${datadir}/zoneinfo/HST                  \
#                ${datadir}/zoneinfo/MET                  \
#                ${datadir}/zoneinfo/MST                  \
#                ${datadir}/zoneinfo/MST7MDT              \
#                ${datadir}/zoneinfo/NZ                   \
#                ${datadir}/zoneinfo/NZ-CHAT              \
#                ${datadir}/zoneinfo/PRC                  \
#                ${datadir}/zoneinfo/PST8PDT              \
#                ${datadir}/zoneinfo/ROC                  \
#                ${datadir}/zoneinfo/ROK                  \
#                ${datadir}/zoneinfo/UCT                  \
#                ${datadir}/zoneinfo/UTC                  \
#                ${datadir}/zoneinfo/Universal            \
#                ${datadir}/zoneinfo/W-SU                 \
#                ${datadir}/zoneinfo/WET                  \
#                ${datadir}/zoneinfo/Zulu                 \
#                ${datadir}/zoneinfo/zone.tab             \
#                ${datadir}/zoneinfo/iso3166.tab          \
#                ${datadir}/zoneinfo/Etc/*"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

S = "${WORKDIR}/tzdata-${PV}"

do_compile () {
        for zone in ${TZONES}; do \
            /usr/sbin/zic -d ${S}/build${datadir}/zoneinfo -L /dev/null \
                -y ${S}/yearistype.sh ${S}/${zone} ; \
            /usr/sbin/zic -d ${S}/build${datadir}/zoneinfo/posix -L /dev/null \
                -y ${S}/yearistype.sh ${S}/${zone} ; \
            /usr/sbin/zic -d ${S}/build${datadir}/zoneinfo/right -L ${S}/leapseconds \
                -y ${S}/yearistype.sh ${S}/${zone} ; \
        done
}

do_install () {
        install -d ${D}/usr ${D}${datadir}/zoneinfo
        cp -pPR ${S}/build/usr ${D}/
        # libc is removing zoneinfo files from package
        cp -pP "${S}/zone.tab" ${D}${datadir}/zoneinfo
        cp -pP "${S}/iso3166.tab" ${D}${datadir}/zoneinfo
	chown -R root:root ${D}
}

TZDATA_PKGS = " \
tzdata-africa \
tzdata-america \
tzdata-antarctica \
tzdata-arctic \
tzdata-asia \
tzdata-atlantic \
tzdata-australia \
tzdata-brazil \
tzdata-canada \
tzdata-chile \
tzdata-etc \
tzdata-europe \
tzdata-indian \
tzdata-mexico \
tzdata-pacific \
tzdata-us \
tzdata-posix \
tzdata-right \
tzdata-misc \
"

PACKAGES = "tzdata ${TZDATA_PKGS}"

FILES_${PN} = ""
ALLOW_EMPTY_${PN} = "1"
RDEPENDS_${PN} += "${TZDATA_PKGS}"

FILES_tzdata-africa += "${datadir}/zoneinfo/Africa/*"
RPROVIDES_tzdata-africa = "tzdata-africa"

FILES_tzdata-america += "${datadir}/zoneinfo/America/*"
RPROVIDES_tzdata-america = "tzdata-america"

FILES_tzdata-antarctica += "${datadir}/zoneinfo/Antarctica/*"
RPROVIDES_tzdata-antarctica = "tzdata-antarctica"

FILES_tzdata-arctic += "${datadir}/zoneinfo/Arctic/*"
RPROVIDES_tzdata-arctic = "tzdata-arctic"

FILES_tzdata-asia += "${datadir}/zoneinfo/Asia/*"
RPROVIDES_tzdata-asia = "tzdata-asia"

FILES_tzdata-atlantic += "${datadir}/zoneinfo/Atlantic/*"
RPROVIDES_tzdata-atlantic = "tzdata-atlantic"

FILES_tzdata-australia += "${datadir}/zoneinfo/Australia/*"
RPROVIDES_tzdata-australia = "tzdata-australia"

FILES_tzdata-brazil += "${datadir}/zoneinfo/Brazil/*"
RPROVIDES_tzdata-brazil = "tzdata-brazil"

FILES_tzdata-canada += "${datadir}/zoneinfo/Canada/*"
RPROVIDES_tzdata-canada = "tzdata-canada"

FILES_tzdata-chile += "${datadir}/zoneinfo/Chile/*"
RPROVIDES_tzdata-chile = "tzdata-chile"

FILES_tzdata-etc += "${datadir}/zoneinfo/Etc/*"
RPROVIDES_tzdata-etc = "tzdata-etc"

FILES_tzdata-europe += "${datadir}/zoneinfo/Europe/*"
RPROVIDES_tzdata-europe = "tzdata-europe"

FILES_tzdata-indian += "${datadir}/zoneinfo/Indian/*"
RPROVIDES_tzdata-indian = "tzdata-indian"

FILES_tzdata-mexico += "${datadir}/zoneinfo/Mexico/*"
RPROVIDES_tzdata-mexico = "tzdata-mexico"

FILES_tzdata-pacific += "${datadir}/zoneinfo/Pacific/*"
RPROVIDES_tzdata-pacific = "tzdata-pacific"

FILES_tzdata-us += "${datadir}/zoneinfo/US/*"
RPROVIDES_tzdata-us = "tzdata-us"

FILES_tzdata-posix += "${datadir}/zoneinfo/posix/*"
RPROVIDES_tzdata-posix = "tzdata-posix"

FILES_tzdata-right += "${datadir}/zoneinfo/right/*"
RPROVIDES_tzdata-right = "tzdata-right"

FILES_tzdata-misc += "${datadir}/zoneinfo/*"
RPROVIDES_tzdata-misc = "tzdata-misc"
