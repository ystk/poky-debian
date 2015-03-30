#
# sysvinit_2.88dsf.bb
#

DESCRIPTION = "System-V like init."
DESCRIPTION = "This package is required to boot in most configurations.  It provides the /sbin/init program.  This is the first process started on boot, and the last process terminated before the system halts."
HOMEPAGE = "http://savannah.nongnu.org/projects/sysvinit/"
SECTION = "base"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe \
                    file://COPYRIGHT;endline=15;md5=349c872e0066155e1818b786938876a4"
#PR = "r5"

RDEPENDS_${PN} = "${PN}-inittab"

#SRC_URI = "http://download.savannah.gnu.org/releases-noredirect/sysvinit/sysvinit-${PV}.tar.bz2 \
#	   file://install.patch \
#	   file://crypt-lib.patch \
#           file://need \
#           file://provide \
#           file://rcS-default \
#           file://rc \
#           file://rcS \
#	   file://bootlogd.init"

#SRC_URI[md5sum] = "6eda8a97b86e0a6f59dabbf25202aa6f"
#SRC_URI[sha256sum] = "60bbc8c1e1792056e23761d22960b30bb13eccc2cabff8c7310a01f4d5df1519"

S = "${WORKDIR}/sysvinit-${PV}"
B = "${S}/src"

inherit update-alternatives

ALTERNATIVE_NAME = "init"
ALTERNATIVE_LINK = "${base_sbindir}/init"
ALTERNATIVE_PATH = "${base_sbindir}/init.sysvinit"
ALTERNATIVE_PRIORITY = "50"

PACKAGES =+ "sysvinit-pidof sysvinit-sulogin"
FILES_${PN} += "${base_sbindir}/* ${base_bindir}/*"
FILES_sysvinit-pidof = "${base_bindir}/pidof.sysvinit ${base_sbindir}/killall5"
FILES_sysvinit-sulogin = "${base_sbindir}/sulogin"

RDEPENDS_${PN} += "sysvinit-pidof"

CFLAGS_prepend = "-D_GNU_SOURCE "
export LCRYPT = "-lcrypt"
EXTRA_OEMAKE += "'base_bindir=${base_bindir}' \
		 'base_sbindir=${base_sbindir}' \
		 'bindir=${bindir}' \
		 'sbindir=${sbindir}' \
		 'sysconfdir=${sysconfdir}' \
		 'includedir=${includedir}' \
		 'mandir=${mandir}'"

#do_install () {
#	oe_runmake 'ROOT=${D}' install
#	install -d ${D}${sysconfdir} \
#		   ${D}${sysconfdir}/default \
#		   ${D}${sysconfdir}/init.d
#	install -m 0644    ${WORKDIR}/rcS-default	${D}${sysconfdir}/default/rcS
#	install -m 0755    ${WORKDIR}/rc		${D}${sysconfdir}/init.d
#	install -m 0755    ${WORKDIR}/rcS		${D}${sysconfdir}/init.d
#	install -m 0755    ${WORKDIR}/bootlogd.init     ${D}${sysconfdir}/init.d/bootlogd
#	ln -sf bootlogd ${D}${sysconfdir}/init.d/stop-bootlogd
#	install -d ${D}${sysconfdir}/rcS.d
#	ln -sf ../init.d/bootlogd ${D}${sysconfdir}/rcS.d/S07bootlogd
#	for level in 2 3 4 5; do
#		install -d ${D}${sysconfdir}/rc$level.d
#		ln -s ../init.d/stop-bootlogd ${D}${sysconfdir}/rc$level.d/S99stop-bootlogd
#	done
#	mv                 ${D}${base_sbindir}/init               ${D}${base_sbindir}/init.${BPN}
#	mv ${D}${base_bindir}/pidof ${D}${base_bindir}/pidof.${BPN}
#	mv ${D}${base_sbindir}/halt ${D}${base_sbindir}/halt.${BPN}
#	mv ${D}${base_sbindir}/reboot ${D}${base_sbindir}/reboot.${BPN}
#	mv ${D}${base_sbindir}/shutdown ${D}${base_sbindir}/shutdown.${BPN}
#	mv ${D}${base_sbindir}/poweroff ${D}${base_sbindir}/poweroff.${BPN}
#	mv ${D}${bindir}/last ${D}${bindir}/last.${BPN}
#	mv ${D}${bindir}/mesg ${D}${bindir}/mesg.${BPN}
#	mv ${D}${bindir}/wall ${D}${bindir}/wall.${BPN}
#}

pkg_postinst_${PN} () {
	update-alternatives --install ${base_sbindir}/halt halt halt.${BPN} 200
	update-alternatives --install ${base_sbindir}/reboot reboot reboot.${BPN} 200
	update-alternatives --install ${base_sbindir}/shutdown shutdown shutdown.${BPN} 200
	update-alternatives --install ${base_sbindir}/poweroff poweroff poweroff.${BPN} 200
	update-alternatives --install ${bindir}/last last last.${BPN} 200
	update-alternatives --install ${bindir}/mesg mesg mesg.${BPN} 200
	update-alternatives --install ${bindir}/wall wall wall.${BPN} 200
}

pkg_prerm_${PN} () {
	update-alternatives --remove halt halt.${BPN}
	update-alternatives --remove reboot reboot.${BPN}
	update-alternatives --remove shutdown shutdown.${BPN}
	update-alternatives --remove poweroff poweroff.${BPN}
	update-alternatives --remove last last.${BPN}
	update-alternatives --remove mesg mesg.${BPN}
	update-alternatives --remove wall wall.${BPN}
}

pkg_postinst_sysvinit-pidof () {
	update-alternatives --install ${base_bindir}/pidof pidof pidof.${BPN} 200
}

pkg_prerm_sysvinit-pidof () {
	update-alternatives --remove pidof pidof.${BPN}
}

#
# debian-squeeze
#

inherit debian-squeeze
inherit update-rc.d

SRC_URI = " \
file://install.patch \
file://crypt-lib.patch \
file://need \
file://provide \
file://rcS-default \
file://rc \
file://rcS \
file://bootlogd.init \
file://stop-bootlogd \
file://remove-debian-commands-from-initscripts.patch \
"

# implement bootlogd as a single package
PACKAGES =+ "sysvinit-bootlogd"
FILES_sysvinit-bootlogd = " \
${base_sbindir}/bootlogd \
${sysconfdir}/init.d/*bootlogd \
${sysconfdir}/rc*.d/*bootlogd \
"

# rc number should be changed flexibly
SYSVINIT_BOOTLOGD_RC ?= "07"
SYSVINIT_STOP_BOOTLOGD_RC ?= "99"

# implement initscripts package which includes additional init scripts
PACKAGES =+ "${PN}-reboot ${PN}-halt ${PN}-bootmisc"
FILES_${PN}-reboot = "${sysconfdir}/init.d/reboot"
FILES_${PN}-halt = "${sysconfdir}/init.d/halt"
FILES_${PN}-bootmisc = "${sysconfdir}/init.d/bootmisc.sh"

INITSCRIPT_PACKAGES = "${PN}-halt ${PN}-reboot ${PN}-bootmisc"
INITSCRIPT_NAME_${PN}-halt = "halt"
INITSCRIPT_PARAMS_${PN}-halt = "stop 99 0 ."
INITSCRIPT_NAME_${PN}-reboot = "reboot"
INITSCRIPT_PARAMS_${PN}-reboot = "stop 99 6 ."
INITSCRIPT_NAME_${PN}-bootmisc = "bootmisc.sh"
INITSCRIPT_PARAMS_${PN}-bootmisc = "start 20 S ."

RDEPENDS_${PN} += "${PN}-bootlogd ${PN}-reboot ${PN}-halt ${PN}-bootmisc"

do_install () {
	oe_runmake 'ROOT=${D}' install
	install -d ${D}${sysconfdir} \
		   ${D}${sysconfdir}/default \
		   ${D}${sysconfdir}/init.d
	install -m 0644    ${WORKDIR}/rcS-default	${D}${sysconfdir}/default/rcS
	install -m 0755    ${WORKDIR}/rc		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/rcS		${D}${sysconfdir}/init.d
	install -m 0755    ${WORKDIR}/bootlogd.init     ${D}${sysconfdir}/init.d/bootlogd
	install -m 0755	   ${WORKDIR}/stop-bootlogd	${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/rcS.d

	# NOTE: use variables to define rc number
	ln -sf ../init.d/bootlogd ${D}${sysconfdir}/rcS.d/S${SYSVINIT_BOOTLOGD_RC}bootlogd
	for level in 2 3 4 5; do
		install -d ${D}${sysconfdir}/rc$level.d
		ln -s ../init.d/stop-bootlogd ${D}${sysconfdir}/rc$level.d/S${SYSVINIT_STOP_BOOTLOGD_RC}stop-bootlogd
	done

	mv                 ${D}${base_sbindir}/init               ${D}${base_sbindir}/init.${BPN}
	mv ${D}${base_bindir}/pidof ${D}${base_bindir}/pidof.${BPN}
	mv ${D}${base_sbindir}/halt ${D}${base_sbindir}/halt.${BPN}
	mv ${D}${base_sbindir}/reboot ${D}${base_sbindir}/reboot.${BPN}
	mv ${D}${base_sbindir}/shutdown ${D}${base_sbindir}/shutdown.${BPN}
	mv ${D}${base_sbindir}/poweroff ${D}${base_sbindir}/poweroff.${BPN}
	mv ${D}${bindir}/last ${D}${bindir}/last.${BPN}
	mv ${D}${bindir}/mesg ${D}${bindir}/mesg.${BPN}
	mv ${D}${bindir}/wall ${D}${bindir}/wall.${BPN}

	# install additional init scripts
	install -m 0755 ${S}/debian/src/initscripts/etc/init.d/halt ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/debian/src/initscripts/etc/init.d/reboot ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/debian/src/initscripts/etc/init.d/bootmisc.sh ${D}${sysconfdir}/init.d
	# vars.sh is loaded by init scripts
	install -d ${D}${base_libdir}/init/
	install -m 0755 ${S}/debian/src/initscripts/lib/init/vars.sh ${D}${base_libdir}/init/
}

# for vars.sh
FILES_${PN} += "${base_libdir}"
