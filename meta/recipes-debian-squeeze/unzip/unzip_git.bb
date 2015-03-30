DESCRIPTION = "De-archiver for .zip files"
SECTION = "utils"
LICENSE = "Info-ZIP"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94caec5a51ef55ef711ee4e8b1c69e29"

inherit debian-squeeze autotools

DEPENDS = "bzip2"
BBCLASSEXTEND = "native"

do_compile_prepend() {
	cp ${S}/unix/Makefile ${S}
	sed -i -e "s:^CC =.*:CC = ${CC}:" ${S}/Makefile
	sed -i -e "s:^AS =.*:AS = ${AS}:" ${S}/Makefile
	sed -i -e "s:^CFLAGS =.*:CFLAGS = ${CFLAGS}:" ${S}/Makefile
}

do_install() {
	install -d ${D}${bindir}
	install -m 755 ${S}/unzip ${D}${bindir}
	install -m 755 ${S}/funzip ${D}${bindir}
	install -m 755 ${S}/unzipsfx ${D}${bindir}
	install -m 755 ${S}/unix/zipgrep ${D}${bindir}
}

EXTRA_OEMAKE = " generic"
