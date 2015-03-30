#
# debian-squeeze
#
DESCRIPTION = "Standards-based cluster framework (daemon and modules)"
SECTION = "admin"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6ddd1b9ffefcca04866906195d8bda33"
PR = "r0"

inherit autotools
inherit debian-squeeze
EXTRA_OECONF = "--disable-nss"

# replace AC_REPLACE_FNMATCH by AC_FUNC_FNMATCH not to use AC_LIBOBJ
# because it causes an link error (fnmatch.h => fnmatch_.h)
do_unpack_append() {
	bb.build.exec_func('fix_fnmatch', d)
}
fix_fnmatch() {
	sed -i "s@AC_REPLACE_FNMATCH@AC_FUNC_FNMATCH@g" ${S}/configure.ac
}
