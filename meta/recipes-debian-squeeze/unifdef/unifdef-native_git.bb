#
# unifdef-native_2.6.18+git.bb
#

DESCRIPTION = "Kernel header preprocessor"
SECTION = "devel"
LICENSE = "GPL"

LIC_FILES_CHKSUM = "file://${WORKDIR}/unifdef.c;endline=32;md5=1a33f5c39aa718a89058721df61979bd"

#SRC_URI = "file://unifdef.c"

inherit native

do_compile() {
	${CC} ${CFLAGS} ${LDFLAGS} -o unifdef ${WORKDIR}/unifdef.c
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 unifdef ${D}${bindir}
}

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

SRC_URI = " \
file://unifdef.c \
"
