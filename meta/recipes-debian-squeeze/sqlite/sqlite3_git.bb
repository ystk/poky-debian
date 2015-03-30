#
# sqlite3_3.7.7.1.bb
#

require sqlite3.inc

#LIC_FILES_CHKSUM = "file://sqlite3.h;endline=11;md5=65f0a57ca6928710b418c094b3570bb0"

#SRC_URI = "http://www.sqlite.org/sqlite-autoconf-3070701.tar.gz"
#S = "${WORKDIR}/sqlite-autoconf-3070701"
#PR = "r0"

#SRC_URI[md5sum] = "554026fe7fac47b1cf61c18d5fe43419"
#SRC_URI[sha256sum] = "7dcc36b25f7bcbe2938d0ea2baea5b706f0af93473d02a3f612d7ab39e386edf"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

LIC_FILES_CHKSUM = "file://src/main.c;endline=16;md5=86c9b824e9d68f8a26343a4b57f6d85a"

# libtool.patch: Required to avoid a compile error
SRC_URI = " \
file://libtool.patch \
"
