#
# debian-squeeze
#
DESCRIPTION = "X11 XKB utilities"
LICENSE = GPL""
LIC_FILES_CHKSUM = "file://xkbutils/COPYING;md5=4641deddaa80fe7ca88e944e1fd94a94"
SECTION = "x11"
PR = "r0"

inherit debian-squeeze autotools

DEPENDS += "libx11 libxaw libxext libxt libxmu"

DIR_LIST = "setxkbmap  xkbcomp  xkbevd  xkbprint  xkbutils"
do_patch_srcpkg() {
	:
}
do_configure() {
	for i in ${DIR_LIST}; do
		if [ -x ${S}/$i/configure ] ; then
                	cfgcmd="${S}/$i/configure \
	                ${CONFIGUREOPTS} ${EXTRA_OECONF} $@"
        	        bbnote "Running $cfgcmd..."
                	$cfgcmd || bbfatal "oe_runconf failed"
	        else
	                bbfatal "no configure script found"
        	fi
		cp -r ${S}/.deps ${S}/$i/
		cp ${S}/Makefile ${S}/$i/
		cp ${S}/config.status ${S}/$i/
		cp ${S}/config.h ${S}/$i/
	done
}
do_compile() {
	for i in ${DIR_LIST}; do
		cd ${S}/$i
		oe_runmake
	done
}
do_install() {
	for i in ${DIR_LIST}; do
                cd ${S}/$i
                oe_runmake install DESTDIR=${D}
        done
}
