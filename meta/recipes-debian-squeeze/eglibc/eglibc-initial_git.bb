#
# eglibc-initial_2.12.bb
#

require eglibc_${PV}.bb
require eglibc-initial.inc

do_configure_prepend () {
        unset CFLAGS
}

#
# debian-squeeze
#

DEBIAN_SQUEEZE_SRCPKG_NAME = "eglibc"
