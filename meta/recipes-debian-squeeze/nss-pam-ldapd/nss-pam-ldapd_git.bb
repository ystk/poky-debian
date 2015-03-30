DESCRIPTION = "a library for doging NSS and PAM lookups using an LDAP"
SECTION = "admin"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=fbc093901857fcd118f065f900982c24"

inherit autotools debian-squeeze

PR = "r0"

DEPENDS += " krb5 openldap libpam update-rc.d"
RDEPENDS += "update-rc.d"

SRC_URI += " \
file://common-account \
file://common-auth \
file://common-password \
file://common-session \
file://common-session-noninteractive \
file://20_nslcd \
"

FILES_${PN} += "/${BASELIB}/security/* "
FILES_${PN}-dbg += "/${BASELIB}/security/.debug"

do_compile_prepend() {
	sed -i -e "s:^libdir =.*:libdir = /${BASELIB}:" pam/Makefile
}

do_install_append() {
	chmod 0640 ${D}${sysconfdir}/nslcd.conf
	
	install -d ${D}${sysconfdir}/init.d
	install -m 755 ${S}/debian/nslcd.init ${D}${sysconfdir}/init.d/nslcd
       	 
	# files originally included in pam, modified for ldap
        install -d ${D}${sysconfdir}/pam.d
        install -m 0755    ${WORKDIR}/common-account    ${D}${sysconfdir}/pam.d
        install -m 0755    ${WORKDIR}/common-auth       ${D}${sysconfdir}/pam.d
        install -m 0755    ${WORKDIR}/common-password   ${D}${sysconfdir}/pam.d
        install -m 0755    ${WORKDIR}/common-session    ${D}${sysconfdir}/pam.d
        install -m 0755    ${WORKDIR}/common-session-noninteractive     ${D}${sysconfdir}/pam.d
	
	# volatile file
        install -d ${D}${sysconfdir}/default/volatiles/
	install -m 0644 ${WORKDIR}/20_nslcd ${D}${sysconfdir}/default/volatiles/
}

# setup account
inherit passwd
# user
NSLCD_NAME ?= "nslcd"
NSLCD_PASS ?= ""
NSLCD_UID ?= "89"
NSLCD_GID ?= "89"
NSLCD_COMMENT ?= ""
NSLCD_HOME ?= "/var/run/nslcd"
NSLCD_SHELL ?= "${sbindir}/nologin"
NSLCD_PASSWD = "\
${NSLCD_NAME}:${NSLCD_PASS}:${NSLCD_UID}:${NSLCD_GID}:\
${NSLCD_COMMENT}:${NSLCD_HOME}:${NSLCD_SHELL}"
# group
NSLCD_GRP ?= "nslcd"
NSLCD_GROUP = "${NSLCD_GRP}:x:${NSLCD_GID}:"

do_install_append() {
        install -d ${D}/${PASSWD_DIR}
        echo "${NSLCD_PASSWD}" > ${D}/${PASSWD_DIR}/nslcd
	install -d ${D}/${GROUP_DIR}
	echo "${NSLCD_GROUP}" > ${D}/${GROUP_DIR}/nslcd
}
FILES_${PN} += "${PASSWD_DIR} ${GROUP_DIR}"


pkg_postinst_${PN} () {
        # Build-time actions
        if [ "x$D" != "x" ]; then
                OPT="-r $D"
                update-rc.d $OPT nslcd defaults
        # Run-time actions
        else
                grep -q ${NSLCD_GRP}: /etc/group || addgroup ${NSLCD_GRP}
                adduser --system \
			--home ${NSLCD_HOME} \
			--gecos "nslcd name service LDAP connection daemon" \
			--no-create-home --disabled-password \
			--ingroup ${NSLCD_GRP} ${NSLCD_NAME}
                update-rc.d nslcd defaults 9
        fi

        if [ -e /etc/init.d/populate-volatile.sh ] ; then
                /etc/init.d/populate-volatile.sh update
        fi
}
