#
# debian-squeeze 
#
DESCRIPTION = "Attach and detach slave interfaces to a bonding device"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://ifenslave.c;beginline=3;endline=25;md5=f619be70e86d1ffd616dd92620fe048f" 
PR = "r0"

inherit debian-squeeze
do_install() {
	cd ${S}
	${CC} -g -Wall -o ifenslave ifenslave.c
	
	install -D ifenslave ${D}/sbin/ifenslave-2.6
	install -m 644 -D debian/ifenslave.8 ${D}/usr/share/man/man8/ifenslave-2.6.8
	install -D debian/up ${D}/etc/network/if-up.d/ifenslave
	install -D debian/pre-up ${D}/etc/network/if-pre-up.d/ifenslave
	install -D debian/post-down ${D}/etc/network/if-post-down.d/ifenslave
}
