#
# gsoap/gsoap_2.7.13.bb
# This recipe is imported from OpenEmbedded
# Commit: 86ff745847ebfcfb77730851b72a7d576422c660
#
require gsoap_${PV}.inc

DEPENDS = "gsoap-native"

do_install_append() {
   install -d ${D}${libdir}
   for lib in libgsoapssl libgsoapssl++ libgsoap libgsoapck++ libgsoap++ libgsoapck
   do
       oe_libinstall -C soapcpp2 $lib ${D}${libdir}
   done
}


FILES_${PN} = "${bindir}/wsdl2h ${bindir}/soapcpp2"
FILES_${PN} += "${datadir}"
