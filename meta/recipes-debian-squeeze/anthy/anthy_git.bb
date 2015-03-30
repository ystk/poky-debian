#
# anthy/anthy_9100e.bb
# This recipe is imported from OpenEmbedded
# Commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

DESCRIPTION = "input method for Japanese - backend, dictionary and utility"
DESCRIPTION_anthy = "A Japanese input method (backend, dictionary and utility)"
DESCRIPTION_libanthy0 = "Anthy runtime library"
DESCRIPTION_libanthy-dev = "Anthy static library, headers and documets for developers"
AUTHOR = "Anthy Developers <anthy-dev@lists.sourceforge.jp>"
HOMEPAGE = "http://anthy.sourceforge.jp"
SECTION = "inputmethods"
SECTION_libanthy0 = "libs/inputmethods"
SECTION_libanthy-dev = "devel/libs"
LICENSE = "LGPLv2.1"
DEPENDS = "anthy-native"
PR = "r0"

#SRC_URI = "http://osdn.dl.sourceforge.jp/anthy/29142/anthy-9100e.tar.gz \
#           file://not_build_elc.patch;patch=1 \
#           file://2ch_t.patch;patch=1 \
#           file://native-helpers.patch;patch=1"

inherit autotools pkgconfig

# gettext

LEAD_SONAME = "libanthy.so.0"
RDEPENDS_anthy = "libanthy0"

#do_stage() {
#	autotools_stage_all
#}

#PACKAGES += "${PN}-el libanthy0 libanthy-dev"
FILES_${PN}-dbg += "${libdir}/.debug"
FILES_libanthy0 = "${libdir}/libanthy.so.*	\
           		   ${libdir}/libanthydic.so.*	\
		           ${libdir}/libanthyinput.so.*"
FILES_libanthy-dev = "${libdir}/libanthy*.la \
                      ${libdir}/libanthy*.a \
                      ${libdir}/libanthy*.so \
	 	              ${includedir}/anthy	\
		              ${libdir}/pkgconfig/anthy.pc"
FILES_${PN}-el = "${datadir}/emacs/*"
FILES_${PN} = "${datadir}/* \
               ${bindir}/* \
               ${sysconfdir}/anthy-conf"
PR = "r0"                                                                       

#                                                                              
# debian-squeeze                                                                      
#                                                                               

inherit debian-squeeze
SRC_URI += "file://native-helpers-orig.patch;patch=1"
LIC_FILES_CHKSUM = "file://COPYING;md5=11f384074d8e93e263b5664ef08a411a"      

# use "libanthy-dev" instead of "anthy-dev"
PACKAGES =+ "${PN}-el libanthy0 libanthy-dev"
