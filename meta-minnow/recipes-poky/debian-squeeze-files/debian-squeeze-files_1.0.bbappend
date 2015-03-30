FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://load-keys"

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/load-keys ${D}${sysconfdir}/init.d
}
