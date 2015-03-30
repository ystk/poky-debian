#require ${PN}_${PV}.inc
require mysql.inc

#DEPENDS += mysql5-native
DEPENDS += mysql-native
#SRC_URI[md5sum] = "32e7373c16271606007374396e6742ad"
#SRC_URI[sha256sum] = "2b0737b84e7b42c9e54c9658d23bfaee1189cd5955f26b10bdb862761d0f0432"

do_configure () {
	sed -i "460,472d" ${S}/Makefile.in
	sed -i "26303,26308d" ${S}/configure
	sed -i -e "26303s/.*/     :/" ${S}/configure
	sed -i "48115,48120d" ${S}/configure
	sed -i -e "48115s/.*/     :/" ${S}/configure
	sed -i "48215,48220d" ${S}/configure
	sed -i -e "48215s/.*/     :/" ${S}/configure
	sed -i "48422,48427d" ${S}/configure
	sed -i -e "48422s/.*/     :/" ${S}/configure
	oe_runconf
}

do_compile_prepend() {
	sed -i "328d" ${S}/Docs/Makefile
	sed -i "328d" ${S}/Docs/Makefile.in
	sed -i "328d" ${S}/Docs/Images/Makefile.in
	sed -i "941,947d" ${S}/extra/Makefile
	sed -i -e "s:\./gen_lex_hash:${STAGING_BINDIR_NATIVE}/gen_lex_hash:g" ${S}/sql/Makefile
	sed -i -e "s/STACK_DIRECTION/-1/g" ${S}/sql/sql_parse.cc
}

# rename installed file - removing host prefix from name
do_install_append() {
        cd ${D}
        find -name "${HOST_SYS}*" > log
        while read line
        do
                name1=$line
                name2=`echo -n $line | sed -e "s/${HOST_SYS}-//"`
                mv $name1 $name2
        done < log
        rm log
}
export LIBTOOL="${STAGING_BINDIR}/crossscripts/${HOST_SYS}-libtool"
EXTRA_OEMAKE += " 'LIBTOOL=${LIBTOOL}'"
