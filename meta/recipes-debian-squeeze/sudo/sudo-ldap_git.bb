#
# debian-squeeze
#

require sudo_${PV}.bb

DEBIAN_SQUEEZE_SRCPKG_NAME = "sudo"

DEPENDS += "openldap"

EXTRA_OECONF += "--with-ldap --with-fqdn \
		--with-logging=syslog --with-logfac=authpriv \
		--with-ldap-conf-file=/etc/sudo-ldap.conf"

do_install_append() {
	install -d ${D}${sysconfdir}/openldap/schema
	install -m 444 ${S}/schema.OpenLDAP ${D}${sysconfdir}/openldap/schema/sudo.schema
}
