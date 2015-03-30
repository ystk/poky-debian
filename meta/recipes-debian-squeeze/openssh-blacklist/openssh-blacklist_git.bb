DESCRIPTION = "list of default blacklisted OpenSSH RSA and DSA keys"
SECTION = "net"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://README;md5=d969efa18902a0ea9bbc20836dc9db3b"


#
# debian-squeeze
#
inherit debian-squeeze
inherit autotools
DEPENDS += ""
PACKAGES += "${PN}-extra"
ALLOW_EMPTY-${PN} = "1"
FILES_${PN} += "/usr/share/ssh/blacklist.DSA-1024 \
	       /usr/share/ssh/blacklist.RSA-2048"
FILES_${PN}-extra = "/usr/share/ssh/blacklist.DSA-2048 \
		     /usr/share/ssh/blacklist.RSA-1024 \
		     /usr/share/ssh/blacklist.RSA-4096"
RDEPENDS += "${PN}-extra"
RPROVIDES_${PN}-extra = "${PN}-extra"

do_compile() {
}

do_install() {
	install -d ${D}/usr/share/ssh
	for i in $(ls [RD]SA-*.?e* | cut -d. -f1 | sort -u); do
        	cat debian/blacklist.prefix > ${D}/usr/share/ssh/blacklist.$i
             	cat $i.* | cut -b13- | sort >> ${D}/usr/share/ssh/blacklist.$i
	done
     	for i in $(ls [RD]SA-*.all | cut -d. -f1 | sort -u); do
              	cat $i.* >> ${D}/usr/share/ssh/blacklist.$i
      	done 
}

