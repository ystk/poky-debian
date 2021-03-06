#
# tsdv
#
require intltool_${PV}.bb

DEPENDS += " libxml-parser-perl-native"

inherit native

EXTRA_OEMAKE = "'PERL_TARGET=/usr/bin/env perl'"

do_configure_prepend() {
	cd ${S}
	for i in intltool*.in ; do
	 	sed -i -e s:-w::g $i
	done
}

do_configure_append() {
        sed -i -e s:head\ -1:head\ -n1:g intltool.m4
}
