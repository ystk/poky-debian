#
# vnc/libvncserver_0.9.1.bb
# This recipe is imported from OpenEmbedded
# Commit: 86ff745847ebfcfb77730851b72a7d576422c660
#

DESCRIPTION = "A library to support implementations of RDP/VNC servers"
AUTHOR = "Johannes Schindelin <dscho@users.sourceforge.net>"
HOMEPAGE = "http://sourceforge.net/projects/libvncserver"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "GPLv2"
DEPENDS = " virtual/libx11 zlib jpeg libxext"
PROVIDES = "x11vnc"
PR = "r0"

#SRC_URI = "${SOURCEFORGE_MIRROR}/libvncserver/LibVNCServer-${PV}.tar.gz"
#S = "${WORKDIR}/LibVNCServer-${PV}"

inherit autotools

#do_stage() {
#    autotools_stage_all
#}

do_install_append() {
        install -d ${D}${bindir}
        install -m 0755 examples/storepasswd ${D}${bindir}
        install -d ${D}${datadir}/fbvncserver/classes
        install -m 0644 classes/index.vnc ${D}${datadir}/fbvncserver/classes/
        install -m 0644 classes/VncViewer.jar ${D}${datadir}/fbvncserver/classes/
}

PACKAGES =+ "x11vnc libvncserver-storepasswd libvncserver-javaapplet"
FILES_x11vnc = "${bindir}/x11vnc ${bindir}/LinuxVNC"
FILES_libvncserver-storepasswd = "${bindir}/storepasswd"
FILES_libvncserver-javaapplet = "${datadir}/fbvncserver/classes/index.vnc \
                                 ${datadir}/fbvncserver/classes/VncViewer.jar"
#
# debian-squeeze
#

inherit debian-squeeze

LIBTOOL = "${HOST_SYS}-libtool"
EXTRA_OEMAKE += "'LIBTOOL=${LIBTOOL}'"

LIC_FILES_CHKSUM = "file://COPYING;md5=361b6b837cad26c6900a926b62aada5f"

SRC_URI += "file://fix-acinclude.patch \
	    file://fix-aclocal.patch\ 
	    file://fix-makefile.patch "

DEPENDS += "libsdl"
