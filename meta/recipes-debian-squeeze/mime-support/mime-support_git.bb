DESCRIPTION = "MIME files 'mime.types' & 'mailcap', and support programs \
As these files can be used by all MIME compliant programs, they \
have been moved into their own package that others can depend upon."

SECTION = "net"
LICENSE = "Debian"
LIC_FILES_CHKSUM = "file://rfcs/rfc1522.txt;md5=ac4bd29ea7ea0b590d11c3d6874b24bc"

inherit debian-squeeze

DEPENDS = " perl"

do_configure() {
	:
}

do_compile() {
	:
}

do_install() {
	mkdir -p ${D}${sysconfdir}
	install -m 644 mime.types ${D}${sysconfdir}
	install -m 644 mailcap.order ${D}${sysconfdir}
	mkdir -p ${D}${sbindir}
	install -m 755 update-mime ${D}${sbindir}
	mkdir -p ${D}${bindir}
	install -m 755 run-mailcap ${D}${bindir}
	mkdir -p ${D}${libdir}/mime
	install -m 644 mailcap ${D}${libdir}/mime
	install -m 755 debian-view ${D}${libdir}/mime
	mkdir -p ${D}${libdir}/mime/packages/mime-support
	install -m 644 mailcap.entries ${D}${libdir}/mime/packages/mime-support
	cd ${D}${bindir}
	ln -s run-mailcap see
	ln -s run-mailcap edit
	ln -s run-mailcap compose
	ln -s run-mailcap print
}

FILES_${PN} = " \
${sysconfdir}/* \
${bindir}/* \
${libdir}/mime/* \
${sbindir}/* \
"

