inherit debian-squeeze-misc
inherit update-rc.d

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://etc/init.d/auto-update-tool.sh;beginline=5;endline=23;md5=8e2fc31db46355b8dbe5a927ef8b9829"

DEBIAN_SQUEEZE_MISC_NAME = "fs-autoupdate"
DEBIAN_SQUEEZE_MISC_GIT = "${DEBIAN_SQUEEZE_GIT_TOOLS}"
DEBIAN_SQUEEZE_MISC_COMMIT = "remotes/origin/${DEBIAN_SQUEEZE_CODENAME}-master"

PR = "r0"

# board specific script (must be specified by BSP)
AUTO_UPDATE_TOOL_BOARD ?= "null"

SRC_URI = "file://${AUTO_UPDATE_TOOL_BOARD}"

do_fetch_prepend() {
	bb.build.exec_func('check_board_script', d)
}

check_board_script() {
	if [ "${AUTO_UPDATE_TOOL_BOARD}" = "null" ]; then
		echo "AUTO_UPDATE_TOOL_BOARD is not defined"
		exit 1
	fi
}

do_configure() {
	:
}

do_compile() {
	:
}

do_install() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 \
		${S}${sysconfdir}/init.d/auto-update-tool.sh \
		${D}${sysconfdir}/init.d

	install -d ${D}${sysconfdir}/default
	install -m 0644 \
		${S}${sysconfdir}/default/auto-update-tool \
		${D}${sysconfdir}/default
	sed -i "s@^BOARD_TYPE=@BOARD_TYPE=${AUTO_UPDATE_TOOL_BOARD}@" \
		${D}${sysconfdir}/default/auto-update-tool

	install -d ${D}${datadir}/auto-update-tool/board-specify
	install -m 0644 \
		${WORKDIR}/${AUTO_UPDATE_TOOL_BOARD} \
		${D}${datadir}/auto-update-tool/board-specify/${AUTO_UPDATE_TOOL_BOARD}
}

FILES_${PN} += "/*"

# rc number should be changed flexibly
AUTO_UPDATE_TOOL_RC ?= "10"
INITSCRIPT_NAME = "auto-update-tool.sh"
INITSCRIPT_PARAMS = "start ${AUTO_UPDATE_TOOL_RC} S ."

# information for do_package
# source version cannot be defined automatically
PKG_SRC_VERSION = "N/A"
