#
# anthy/anthy-native_9100e.bb
# This recipe is imported from OpenEmbedded
# Commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

require anthy_${PV}.bb

DEPENDS = ""
PACKAGES = ""
PR = "r0"

S = "${WORKDIR}/anthy-${PV}"

inherit native
SRC_URI = "file://native-helpers.patch;patch=1"
