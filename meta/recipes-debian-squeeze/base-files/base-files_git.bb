#
# HIDDEN/recipes-core/base-files/base-files_3.0.14.bb 
#
SUMMARY = "Miscellaneous files for the base system."
DESCRIPTION = "The base-files package creates the basic system directory structure and provides a small set of key configuration files for the system."
SECTION = "base"
#PR = "r69"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://licenses/GPL-2;md5=b234ee4d69f5fce4486a80fdaf4a4263"
#SRC_URI = "file://rotation \
#           file://nsswitch.conf \
#           file://motd \
#           file://inputrc \
#           file://host.conf \
#           file://profile \
#           file://shells \
#           file://fstab \
#           file://filesystems \
#           file://issue.net \
#           file://issue \
#           file://usbd \
#           file://share/dot.bashrc \
#           file://share/dot.profile \
#           file://licenses/BSD \
#           file://licenses/GPL-2 \
#           file://licenses/GPL-3 \
#           file://licenses/LGPL-2 \
#           file://licenses/LGPL-2.1 \
#           file://licenses/LGPL-3 \
#           file://licenses/GFDL-1.2 \
#           file://licenses/Artistic"
S = "${WORKDIR}"

INHIBIT_DEFAULT_DEPS = "1"

#docdir_append = "/${P}"
dirs1777 = "/tmp ${localstatedir}/volatile/lock ${localstatedir}/volatile/tmp"
dirs2775 = "/home ${prefix}/src ${localstatedir}/local"
dirs755 = "/bin /boot /dev ${sysconfdir} ${sysconfdir}/default \
           ${sysconfdir}/skel /lib /mnt /proc /home/root /sbin \
           ${prefix} ${bindir} ${docdir} /usr/games ${includedir} \
           ${libdir} ${sbindir} ${datadir} \
           ${datadir}/common-licenses ${datadir}/dict ${infodir} \
           ${mandir} ${datadir}/misc ${localstatedir} \
           ${localstatedir}/backups ${localstatedir}/lib \
           /sys ${localstatedir}/lib/misc ${localstatedir}/spool \
           ${localstatedir}/volatile ${localstatedir}/volatile/cache \
           ${localstatedir}/volatile/lock/subsys \
           ${localstatedir}/volatile/log \
           ${localstatedir}/volatile/run \
           /mnt /media /media/card /media/cf /media/net /media/ram \
           /media/union /media/realroot /media/hdd \
           /media/mmc1"
dirs3755 = "/srv  \
            ${prefix}/local ${prefix}/local/bin ${prefix}/local/games \
            ${prefix}/local/include ${prefix}/local/lib ${prefix}/local/sbin \
            ${prefix}/local/share ${prefix}/local/src"
dirs4775 = "/var/mail"

volatiles = "cache run log lock tmp"
conffiles = "${sysconfdir}/debian_version ${sysconfdir}/host.conf \
             ${sysconfdir}/inputrc ${sysconfdir}/issue /${sysconfdir}/issue.net \
             ${sysconfdir}/nsswitch.conf ${sysconfdir}/profile \
             ${sysconfdir}/default"

#
# set standard hostname, might be a candidate for a DISTRO variable? :M:
#
#hostname = "openembedded"

BASEFILESISSUEINSTALL ?= "do_install_basefilesissue"

#do_install () {
#	for d in ${dirs755}; do
#		install -m 0755 -d ${D}$d
#	done
#	for d in ${dirs1777}; do
#		install -m 1777 -d ${D}$d
#	done
#	for d in ${dirs2775}; do
#		install -m 2755 -d ${D}$d
#	done
#	for d in ${volatiles}; do
#		ln -sf volatile/$d ${D}/${localstatedir}/$d
#	done
#	for d in card cf net ram; do
#		ln -sf /media/$d ${D}/mnt/$d
#	done
#
#	${BASEFILESISSUEINSTALL}
#
#	rotation=`cat ${WORKDIR}/rotation`
#	if [ "$rotation" != "0" ]; then
# 		install -m 0644 ${WORKDIR}/rotation ${D}${sysconfdir}/rotation
#	fi
#
#	install -m 0644 ${WORKDIR}/fstab ${D}${sysconfdir}/fstab
#	install -m 0644 ${WORKDIR}/filesystems ${D}${sysconfdir}/filesystems
#	install -m 0644 ${WORKDIR}/usbd ${D}${sysconfdir}/default/usbd
#	install -m 0644 ${WORKDIR}/profile ${D}${sysconfdir}/profile
#	install -m 0644 ${WORKDIR}/shells ${D}${sysconfdir}/shells
#	install -m 0755 ${WORKDIR}/share/dot.profile ${D}${sysconfdir}/skel/.profile
#	install -m 0755 ${WORKDIR}/share/dot.bashrc ${D}${sysconfdir}/skel/.bashrc
#	install -m 0644 ${WORKDIR}/inputrc ${D}${sysconfdir}/inputrc
#	install -m 0644 ${WORKDIR}/nsswitch.conf ${D}${sysconfdir}/nsswitch.conf
#	install -m 0644 ${WORKDIR}/host.conf ${D}${sysconfdir}/host.conf
#	install -m 0644 ${WORKDIR}/motd ${D}${sysconfdir}/motd
#
#	for license in BSD GPL-2 LGPL-2 LGPL-2.1 Artistic GPL-3 LGPL-3 GFDL-1.2; do
#		install -m 0644 ${WORKDIR}/licenses/$license ${D}${datadir}/common-licenses/
#	done
#
#	ln -sf /proc/mounts ${D}${sysconfdir}/mtab
#}

do_install_basefilesissue () {
	if [ -n "${MACHINE}" -a "${hostname}" = "openembedded" ]; then
		echo ${MACHINE} > ${D}${sysconfdir}/hostname
	else
		echo ${hostname} > ${D}${sysconfdir}/hostname
	fi

	install -m 644 ${WORKDIR}/issue*  ${D}${sysconfdir}  
#	install -m 644 ${WORKDIR}/issue  ${D}${sysconfdir}
#	install -m 644 ${WORKDIR}/issue.net  ${D}${sysconfdir}

        if [ -n "${DISTRO_NAME}" ]; then
		echo -n "${DISTRO_NAME} " >> ${D}${sysconfdir}/issue
		echo -n "${DISTRO_NAME} " >> ${D}${sysconfdir}/issue.net
		if [ -n "${DISTRO_VERSION}" ]; then
			echo -n "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue
			echo -n "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue.net
		fi
		echo "\n \l" >> ${D}${sysconfdir}/issue
		echo >> ${D}${sysconfdir}/issue
		echo "%h"    >> ${D}${sysconfdir}/issue.net
		echo >> ${D}${sysconfdir}/issue.net
 	fi
}

do_install_append_linuxstdbase() {
	for d in ${dirs3755}; do
                install -m 0755 -d ${D}$d
        done

	for d in ${dirs4775}; do
                install -m 2755 -d ${D}$d
        done
}

PACKAGES = "${PN}-doc ${PN} ${PN}-dev ${PN}-dbg"
FILES_${PN} = "/"
FILES_${PN}-doc = "${docdir} ${datadir}/common-licenses"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#CONFFILES_${PN} = "${sysconfdir}/fstab ${sysconfdir}/hostname"

#
# debian-squeeze
#

# all files are installed from Debian source package
inherit debian-squeeze
PR = "r0"

LICENSES = " \
Apache-2.0 Artistic BSD \
GFDL-1.2 GFDL-1.3 \
GPL-1 GPL-2 GPL-3 \
LGPL-2 LGPL-2.1 LGPL-3 \
"
GFDL_VER = "1.3"
GPL_VER = "3"
LGPL_VER = "3"

do_install () {
	install -d ${D}${sysconfdir}
	install -m 0644 ${S}${sysconfdir}/host.conf ${D}${sysconfdir}
	sed "s@#OSNAME#@GNU/Linux@g" ${S}${sysconfdir}/motd > ${D}${sysconfdir}/motd
	install -m 0644 ${S}/share/nsswitch.conf ${D}${sysconfdir}
	install -m 0644 ${S}/share/profile ${D}${sysconfdir}

	# install skeletons
	install -d ${D}${sysconfdir}/skel
	install -m 0644 ${S}/share/dot.bashrc ${D}${sysconfdir}/skel/.bashrc
	install -m 0644 ${S}/share/dot.profile ${D}${sysconfdir}/skel/.profile

	# install issue
	if [ -n "${DISTRO_NAME}" -a -n "${DEBIAN_SQUEEZE_VERSION}" ]; then
		echo "${DISTRO_NAME} ${DEBIAN_SQUEEZE_VERSION} \n \l" >> ${D}${sysconfdir}/issue
		echo "${DISTRO_NAME} ${DEBIAN_SQUEEZE_VERSION} %h" >> ${D}${sysconfdir}/issue.net
	else
		echo "WARNING: DISTRO_NAME and DEBIAN_SQUEEZE_VERSION are needed to create issues"
	fi

	# install licenses
	install -d ${D}${datadir}/common-licenses
	for license in ${LICENSES}; do
		install -m 0644 ${S}/licenses/$license ${D}${datadir}/common-licenses
	done
	[ ! -f ${D}${datadir}/common-licenses/GPL-${GPL_VER} ] || \
		ln -s GPL-${GPL_VER} ${D}${datadir}/common-licenses/GPL
	[ ! -f ${D}${datadir}/common-licenses/LGPL-${LGPL_VER} ] || \
		ln -s LGPL-${LGPL_VER} ${D}${datadir}/common-licenses/LGPL
	[ ! -f ${D}${datadir}/common-licenses/GFDL-${GFDL_VER} ] || \
		ln -s GFDL-${GFDL_VER} ${D}${datadir}/common-licenses/GFDL
}
