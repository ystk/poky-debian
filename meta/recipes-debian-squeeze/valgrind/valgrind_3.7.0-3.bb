#
# debian-squeeze
#

require valgrind.inc

LIC_FILES_CHKSUM = " \
file://COPYING;md5=c46082167a314d785d012a244748d803 \
file://include/pub_tool_basics.h;beginline=1;endline=29;md5=0ef036a7ddce4cdc738d65d63b3e8153 \
file://include/valgrind.h;beginline=1;endline=56;md5=aee56014c1dd64260a59fd4df38752f6 \
file://COPYING.DOCS;md5=8fdeb5abdb235a08e76835f8f3260215 \
"

SRC_URI += "file://rename-supported-arm.patch"
