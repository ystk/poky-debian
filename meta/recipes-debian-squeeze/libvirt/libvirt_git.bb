#
# debian-squeeze
#
DESCRIPTION = "library for interfacing with different virtualization systems"
HOMEPAGE = "http://libvirt.org"
SECTION = "libs"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=fb919cc88dbe06ec0b0bd50e001ccf1f"

inherit debian-squeeze autotools gettext

DEPENDS += "libxml2 ncurses readline zlib gnutls python cyrus-sasl2 udev \
		libpciaccess libcap-ng libnl lvm2 curl"

EXTRA_OECONF += " \
--with-qemu=no \
--with-lxc=no \
--with-xen=no \
--with-esx=no \
--with-xenapi=no \
--with-qemu-user=no \
--with-qemu-group=no \
--with-sasl \
--with-capng \
"

CFLAGS += " -I${STAGING_INCDIR}/python2.6"

do_configure_prepend() {
	sed -i -e "639,641d" configure.ac
	sed -i -e "s@LIBXML_LIBS=.*@LIBXML_LIBS=\"-lxml2 -L${STAGING_LIBDIR}\"@g" configure.ac
	sed -i -e "s@LIBXML_CFLAGS=.*@LIBXML_CFLAGS=\"-I${STAGING_INCDIR}/libxml2\"@g" configure.ac
}

FILES_${PN} += "${libdir}"
