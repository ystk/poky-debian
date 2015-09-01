#
# debian-squeeze
#

DESCRIPTION = "Perl HTTP/WWW client/server library"
SECTION = "perl"
HOMEPAGE = "http://search.cpan.org/dist/libwww-perl/"

LICENSE = "GPL-1.0+"
LIC_FILES_CHKSUM = " \
file://README;md5=1eed8b1a702a34e82d64428928f5ab19 \
file://README.SSL;md5=1e20a41db2d80376bf75ede3951e3d41"

PR = "r0"

inherit debian-squeeze cpan

# replace perl-native paths in scripts by target perl paths
do_install_append() {
	sed -i "s@${STAGING_BINDIR_NATIVE}/perl-native@${bindir}@" \
		${D}${bindir}/*
}
