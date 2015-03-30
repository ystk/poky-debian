#
# debian-squeeze
#
DESCRIPTION = "Debian Font Manager -- automatic font configuration framework"
SECTION = "admin"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://src/defoma;md5=91f504c73b6156b9d6a5e3aa19b0b89a"

inherit debian-squeeze
inherit autotools
DEPENDS += ""
do_install() {
	install -d ${D}/usr/share/man/man8
        install -m 0755 ${S}/man/defoma-reconfigure.8 ${D}/usr/share/man/man8
	install -d ${D}/usr/share/man/fr/man8
        install -m 0755 ${S}/man/defoma-reconfigure.8 ${D}/usr/share/man/fr/man8
	install -d ${D}/usr/share/man/fr/man1
	install -m 0755 ${S}/man/defoma-app.1 ${D}/usr/share/man/fr/man1
	install -m 0755 ${S}/man/dh_installdefoma.1 ${D}/usr/share/man/fr/man1
	install -m 0755 ${S}/man/defoma-font.1 ${D}/usr/share/man/fr/man1
	install -m 0755 ${S}/man/defoma-id.1 ${D}/usr/share/man/fr/man1
	install -m 0755 ${S}/man/defoma-user.1 ${D}/usr/share/man/fr/man1
	install -m 0755 ${S}/man/defoma-hints.1 ${D}/usr/share/man/fr/man1
	install -m 0755 ${S}/man/defoma.1 ${D}/usr/share/man/fr/man1
	install -m 0755 ${S}/man/defoma-subst.1 ${D}/usr/share/man/fr/man1

	install -d ${D}/usr/share/man/fr/man3
	install -m 0755 ${S}/man/Defoma::Subst.3pm ${D}/usr/share/man/fr/man3
	install -m 0755 ${S}/man/Defoma::Common.3pm ${D}/usr/share/man/fr/man3
	install -m 0755 ${S}/man/Defoma::Font.3pm ${D}/usr/share/man/fr/man3
	install -m 0755 ${S}/man/Defoma::Id.3pm ${D}/usr/share/man/fr/man3	

	install -d ${D}/usr/share/man/man1
	install -m 0755 ${S}/man/defoma-app.1 ${D}/usr/share/man/man1
        install -m 0755 ${S}/man/dh_installdefoma.1 ${D}/usr/share/man/man1
        install -m 0755 ${S}/man/defoma-font.1 ${D}/usr/share/man/man1
        install -m 0755 ${S}/man/defoma-id.1 ${D}/usr/share/man/man1
        install -m 0755 ${S}/man/defoma-user.1 ${D}/usr/share/man/man1
        install -m 0755 ${S}/man/defoma-hints.1 ${D}/usr/share/man/man1
        install -m 0755 ${S}/man/defoma.1 ${D}/usr/share/man/man1
        install -m 0755 ${S}/man/defoma-subst.1 ${D}/usr/share/man/man1

	install -d ${D}/usr/share/man/man3	
	install -m 0755 ${S}/man/Defoma::Subst.3pm ${D}/usr/share/man/man3
        install -m 0755 ${S}/man/Defoma::Common.3pm ${D}/usr/share/man/man3
        install -m 0755 ${S}/man/Defoma::Font.3pm ${D}/usr/share/man/man3
        install -m 0755 ${S}/man/Defoma::Id.3pm ${D}/usr/share/man/man3

	install -d ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libdefoma-user2.pl ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libdefoma-id.pl ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libhint-type1.pl ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libhint-truetype.pl ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libperl-file.pl ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libdefoma-subst.pl ${D}/usr/share/defoma	
	install -m 0755 ${S}/libs/libperl-hint.pl ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/csetenc-xenc.data2 ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libhint-cmap.pl ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libdefoma-user.pl ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libconsole.pl ${D}/usr/share/defoma	
	install -m 0755 ${S}/libs/defoma-test.sh ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libhint-cid.pl ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libdefoma-font.pl ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/libdefoma-app.pl ${D}/usr/share/defoma
	install -m 0755 ${S}/libs/xenc-cset.data ${D}/usr/share/defoma

	install -d ${D}/usr/bin
        install -m 0755 ${S}/src/defoma ${D}/usr/bin
	mv ${D}/usr/bin/defoma ${D}/usr/bin/defoma-user
        install -m 0755 ${S}/src/defoma-hints ${D}/usr/bin
        install -m 0755 ${S}/src/dh_installdefoma ${D}/usr/bin
	install -m 0755 ${S}/src/defoma ${D}/usr/bin
	mv ${D}/usr/bin/defoma ${D}/usr/bin/defoma-app
	install -m 0755 ${S}/src/defoma ${D}/usr/bin
	mv ${D}/usr/bin/defoma ${D}/usr/bin/defoma-id
	install -m 0755 ${S}/src/defoma ${D}/usr/bin
	mv ${D}/usr/bin/defoma ${D}/usr/bin/defoma-subst
	install -m 0755 ${S}/src/defoma ${D}/usr/bin
	mv ${D}/usr/bin/defoma ${D}/usr/bin/defoma-font
	install -m 0755 ${S}/src/defoma ${D}/usr/bin

	install -d ${D}/usr/sbin
        install -m 0755 ${S}/src/defoma-reconfigure ${D}/usr/sbin
	install -d ${D}/etc/defoma
	install -m 0755 ${S}/conf/csetenc-xenc.data2 ${D}/etc/defoma
        install -m 0755 ${S}/conf/loc-cset.data ${D}/etc/defoma
        install -m 0755 ${S}/conf/xenc-cset.data ${D}/etc/defoma
        install -m 0755 ${S}/conf/ps-cset-enc.data ${D}/etc/defoma
	
}

