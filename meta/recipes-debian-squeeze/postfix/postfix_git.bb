require postfix.inc

#SRC_URI[postfix-2.7.0.md5sum] = "df648f59421604e895cce56325f00bae"
#SRC_URI[postfix-2.7.0.sha256sum] = "c5b232ae5e396107bc58aed4178cf6cfd0a75b9dcbbedb49d158eb71d6c91b75"

#PR = "${INC_PR}.0"
PR = "r0"

#
# debian-squeeze
#
SRC_URI += " \
file://main.cf \
file://postfix.init \
file://dynamicmaps.cf \
file://prepare_environment.sh \
file://aliases \
"

do_compile_prepend() {
	sed -i -e "s:makedefs):makedefs Linux 2):" ${S}/Makefile.in
	sed -i -e "s:makedefs;:makedefs Linux 2;:" ${S}/rmail/Makefile.in
	sed -i -e "s:gcc :${HOST_SYS}-gcc :g" ${S}/src/util/Makefile.in
	sed -i -e "s:gcc:${HOST_SYS}-gcc:g" ${S}/src/global/Makefile.in
	sed -i -e "136s:$: -L${STAGING_LIBDIR}/mysql:" ${S}/src/global/Makefile.in
	sed -i -e "139s:$: -L${STAGING_LIBDIR}:" ${S}/src/global/Makefile.in
	sed -i -e "s:gcc :${HOST_SYS}-gcc :g" ${S}/src/dns/Makefile.in
	sed -i -e "s:gcc :${HOST_SYS}-gcc :g" ${S}/src/tls/Makefile.in
	sed -i -e "s:gcc :${HOST_SYS}-gcc :g" ${S}/src/master/Makefile.in
}

do_install_append() {
	install -m 0644 ${WORKDIR}/main.cf ${D}${sysconfdir}/postfix/
	install -m 0644 ${WORKDIR}/dynamicmaps.cf ${D}${sysconfdir}/postfix/
	install -m 0755 ${WORKDIR}/prepare_environment.sh ${D}${sysconfdir}/postfix/
	install -m 0755 ${WORKDIR}/postfix.init ${D}${sysconfdir}/init.d/postfix
	install -m 0644 ${WORKDIR}/aliases ${D}${sysconfdir}
}

# To avoid error: File not recognized: file truncated
# This error is caused by compiler was trying to access to one file
# with many threads at the same time 
PARALLEL_MAKE = ""

# According to debian/rules
CFLAGS += " \
-DDEBIAN \
-DMAX_DYNAMIC_MAPS \
-DHAS_PCRE \
-DHAS_LDAP \
-DHAS_CDB \
-DHAS_MYSQL -I${STAGING_INCDIR}/mysql \
"

DEPENDS += "mysql tinycdb openldap postgresql postfix-native"

export POSTCONF = "${STAGING_DIR_NATIVE}${sbindir_native}/postconf"
export_library_path() {
        export LD_LIBRARY_PATH="${LD_LIBRARY_PATH}:${STAGING_LIBDIR_NATIVE}"
}

LIBDIR = ${D}${libdir}

#
# setup group and user
#
inherit passwd
# user and group postfix
POSTFIX_NAME ?= "postfix"
POSTFIX_PASS ?= ""
POSTFIX_UID ?= "125"
POSTFIX_GID ?= "125"
POSTFIX_COMMENT ?= ""
POSTFIX_HOME ?= "/var/spool/postfix"
POSTFIX_SHELL ?= "${sbindir}/nologin"
POSTFIX_PASSWD = "\
${POSTFIX_NAME}:${POSTFIX_PASS}:${POSTFIX_UID}:${POSTFIX_GID}:\
${POSTFIX_COMMENT}:${POSTFIX_HOME}:${POSTFIX_SHELL}"

POSTFIX_GROUP ?= "postfix"
POSTFIX_GRP = "${POSTFIX_GROUP}:x:${POSTFIX_GID}:"

# group postdrop
POSTDROP_GROUP ?= "postdrop"
POSTDROP_GID ?= "126"
POSTDROP_GRP = "${POSTDROP_GROUP}:x:${POSTDROP_GID}"

# user and group vmail
VMAIL_NAME ?= "vmail"
VMAIL_PASS ?= ""
VMAIL_UID ?= "127"
VMAIL_GID ?= "127"
VMAIL_COMMENT ?= ""
VMAIL_HOME ?= "/var/spool/vmail"
VMAIL_SHELL ?= "${sbindir}/nologin"
VMAIL_PASSWD = "\
${VMAIL_NAME}:${VMAIL_PASS}:${VMAIL_UID}:${VMAIL_GID}:\
${VMAIL_COMMENT}:${VMAIL_HOME}:${VMAIL_SHELL}"

VMAIL_GROUP ?= "vmail"
VMAIL_GRP = "${VMAIL_GROUP}:x:${VMAIL_GID}:"

do_install_append() {
        install -d ${D}/${PASSWD_DIR}
        echo "${POSTFIX_PASSWD}" > ${D}/${PASSWD_DIR}/postfix
	echo "${VMAIL_PASSWD}" > ${D}/${PASSWD_DIR}/vmail
	install -d ${D}/${GROUP_DIR}
	echo "${POSTFIX_GRP}" > ${D}/${GROUP_DIR}/postfix
	echo "${POSTDROP_GRP}" > ${D}/${GROUP_DIR}/postdrop
	echo "${VMAIL_GRP}" > ${D}/${GROUP_DIR}/vmail
}

FILES_${PN} += "${libdir}/* ${PASSWD_DIR} ${GROUP_DIR}"
