#
# debian-squeeze
#

DESCRIPTION = "Standards-based cluster framework (daemon and modules)"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4cb00dd52a063edbece6ae248a2ba663"
SECTION = "admin"
PR = "r0"

inherit autotools
inherit debian-squeeze

DEPENDS += "corosync groff"

# replace AC_REPLACE_FNMATCH by AC_FUNC_FNMATCH not to use AC_LIBOBJ
# because it causes an link error (fnmatch.h => fnmatch_.h)
do_unpack_append() {
	bb.build.exec_func('fix_fnmatch', d)
}
fix_fnmatch() {
	sed -i "s@AC_REPLACE_FNMATCH@AC_FUNC_FNMATCH@g" ${S}/configure.ac
}
