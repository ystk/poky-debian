#
# gdb-cross-canadian_7.3a.bb
#

require gdb-common.inc
require gdb-cross-canadian.inc

#PR = "${INC_PR}.0"

GDBPROPREFIX = "--program-prefix='${TARGET_PREFIX}'"
EXPAT = "--with-expat"

#S = "${WORKDIR}/${BPN}-7.3"

#
# debian-squeeze
#

PR = "r0"
