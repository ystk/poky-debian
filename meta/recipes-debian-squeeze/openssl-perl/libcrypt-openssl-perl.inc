#
# debian-squeeze
#

SECTION = "perl"

inherit debian-squeeze
inherit cpan

DEPENDS += "openssl"

# Makefile* in ${S}/.pc* may confilict with cpan.bbclass
do_configure_prepend() {
	rm -rf ${S}/.pc*
}

FILES_${PN}-dbg =+ ${libdir}/perl/*/*/auto/Crypt/OpenSSL/*/.debug
