#
# debian-squeeze
#

require openldap_${PV}.bb

DEPENDS = "cyrus-sasl2-native"
PACKAGES = ""
PR = "r0"

inherit native

