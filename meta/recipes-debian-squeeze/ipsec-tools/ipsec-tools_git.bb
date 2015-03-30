#
# ipsec-tools/ipsec-tools_0.7.2.bb
# This recipe is imported from OpenEmbedded
# Commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

PR = "${INC_PR}.0"

require ipsec-tools.inc
LIC_FILES_CHKSUM = "file://README;md5=a0d521d17650595e599fbbb74ef83980"
#SRC_URI[ipsec-tools-0.7.2.md5sum] = "72861f005746ee27984b2ee715ecc629"
#SRC_URI[ipsec-tools-0.7.2.sha256sum] = "08722ff6c62de3e042fef337454f03622a79053108d6dcc686c9c854f9f9e031"

EXTRA_OECONF += " --disable-security-context"

#
# debian-squeeze
#

inherit debian-squeeze
DEPENDS += "e2fsprogs krb5 libpam openssl"
SRC_URI += "file://fix_config.patch \
        file://fix_make.patch"

