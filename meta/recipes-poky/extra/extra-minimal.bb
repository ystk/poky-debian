LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420 \
"

PV = "1.0"
PR = "r0"

do_configure() {
	:
}

do_compile() {
	:
}

EXTRA_INITTAB_SYSINIT ?= "${sysconfdir}/init.d/rcS"
EXTRA_INITTAB_RESPAWN ?= "${base_sbindir}/getty 38400 tty1"
EXTRA_INITTAB_CTRLALTDEL ?= "${base_sbindir}/reboot"
EXTRA_INITTAB_SHUTDOWN ?= "${sysconfdir}/init.d/rcE"

EXTRA_EMPTY_DIRS ?= "/proc /root /sys"

SRC_URI = " \
file://fstab \
file://group \
file://init.d \
file://passwd \
"

do_install() {
	install -d ${D}/${sysconfdir}

	for d in ${EXTRA_EMPTY_DIRS}; do
		install -d ${D}/$d
	done

	install -m 0644 ${WORKDIR}/fstab ${D}/${sysconfdir}
	install -m 0644 ${WORKDIR}/group ${D}/${sysconfdir}
	install -m 0644 ${WORKDIR}/passwd ${D}/${sysconfdir}

	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init.d/* ${D}/${sysconfdir}/init.d

	cat <<EOF > ${D}/${sysconfdir}/inittab
::sysinit:${EXTRA_INITTAB_SYSINIT}
::respawn:${EXTRA_INITTAB_RESPAWN}
::ctrlaltdel:${EXTRA_INITTAB_CTRLALTDEL}
::shutdown:${EXTRA_INITTAB_SHUTDOWN}
EOF
}

FILES_${PN} += "/*"
