#
# HIDDEN/recipes-extended/chkconfig/chkconfig_1.3.52.bb 
#
SUMMARY = "A system tool for maintaining the /etc/rc*.d hierarchy"

DESCRIPTION = "Chkconfig is a basic system utility.  It updates and queries runlevel \
information for system services.  Chkconfig manipulates the numerous \
symbolic links in /etc/rc.d, to relieve system administrators of some \
of the drudgery of manually editing the symbolic links."

HOMEPAGE = "http://fedorahosted.org/releases/c/h/chkconfig"

LICENSE = "GPLv2"
#LIC_FILES_CHKSUM = "file://COPYING;md5=5574c6965ae5f583e55880e397fbb018"

DEPENDS = "libnewt popt"

PR = "r1"

#SRC_URI = "http://fedorahosted.org/releases/c/h/chkconfig/${BPN}-${PV}.tar.bz2"

#SRC_URI[md5sum] = "a576808a6d66763606d750ec451bab6d"
#SRC_URI[sha256sum] = "aa36bf7fd3f7c6e3371eddd8a7846b83435c48f132cc5919d83a830504b797d6"

#inherit autotools gettext

#do_install_append() {
#    mkdir -p ${D}/etc/chkconfig.d
#    rm -f ${D}/usr/sbin/update-alternatives
#}
#
# debian-squeeze
#
inherit debian-squeeze
SRC_URI += " \
	   file://deps-output.patch \
	   file://rcd-path.patch"
LIC_FILES_CHKSUM = "file://chkconfig.8;md5=c7ce9dcd97f8005ebeb5a04c69ce8269"

RDEPENDS_${PN} += " \
insserv \
perl-module-strict \
perl-module-getopt-long \
perl-module-file-temp \
"

do_install() {
	cd ${S}
	install -D chkconfig ${D}/sbin/chkconfig
	install -D chkconfig.8 ${D}/usr/share/man/man8/chkconfig.8
}
do_patch_srcpkg() {
	:
}
