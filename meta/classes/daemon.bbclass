inherit passwd

DAEMON_NAME ?= "daemon"
DAEMON_PASS ?= ""
DAEMON_UID ?= "1"
DAEMON_GID ?= "1"
DAEMON_COMMENT ?= ""
DAEMON_HOME ?= "/nonexistent"
DAEMON_SHELL ?= "/bin/false"

DAEMON_PASSWD ?= "\
${DAEMON_NAME}:${DAEMON_PASS}:${DAEMON_UID}:${DAEMON_GID}:\
${DAEMON_COMMENT}:${DAEMON_HOME}:${DAEMON_SHELL}"

DAEMON_GROUP ?= "${DAEMON_NAME}:${DAEMON_PASS}:${DAEMON_GID}:"

do_install_append() {
	install -d ${D}/${PASSWD_DIR}
	echo "${DAEMON_PASSWD}" > ${D}/${PASSWD_DIR}/daemon

	install -d ${D}/${GROUP_DIR}
	echo "${DAEMON_GROUP}" > ${D}/${GROUP_DIR}/daemon
}
