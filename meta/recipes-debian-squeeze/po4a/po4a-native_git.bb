
#
# debian-squeeze
#
DESCRIPTION = "tools for helping translation of documentation"
SECTION = "text"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=a96fc9b4cc36d80659e694ea109f0325"


inherit debian-squeeze
inherit autotools gettext native
#DEPENDS += "jade libsgmls-perl docbook-xml docbook-xsl"
do_install_append() {
   mkdir -p  ${D}/${STAGING_DIR_NATIVE}/usr
   cp -r ${D}/usr/local/* ${D}/${STAGING_DIR_NATIVE}/usr
}
