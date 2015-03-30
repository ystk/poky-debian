#
# debian-squeeze 
#

DESCRIPTION = "Mono"
SECTION = "cli-mono" 
LICENSE = "GPL & LGPL & MIT & MPL"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=58d09de7e50ecdbafcf97e7eb5d0c34a"
PR = "r0"

inherit debian-squeeze

CFLAGS_append += " -DARM_FPU_NONE=1 "
# parallel make does not work for mono (currently)

PARALLEL_MAKE = ""

do_fix_dolt_libtool() {
	# look for libtool prefixed with HOST_SYS
	sed -e "s/slash\}libtool/slash\}${HOST_SYS}-libtool/" -i acinclude.m4
	echo No acinclude!
	libtoolize --force --copy
}

inherit autotools
EXTRA_OECONF_arm = "--without-tls"
EXTRA_OECONF_mipsel = "--without-tls"

addtask fix_dolt_libtool after do_patch before do_configure
