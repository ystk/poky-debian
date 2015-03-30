inherit debian-squeeze autotools

PR = "r0"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

DEPENDS = "elfutils binutils"

# sometimes parallel make fails with "sysdep.h: No such file or directory"
PARALLEL_MAKE = ""

do_configure_prepend() {
	cat Makefile.in | sed "s/@CC@/${TARGET_PREFIX}@CC@/" | \
	sed "s%@CPPFLAGS@%-I${STAGING_INCDIR} -I${STAGING_INCDIR}/libelf%" \
	> Makefile.in.tmp
	mv Makefile.in.tmp Makefile.in
}

# ltrace always needs ARCH=${LTRACE_ARCH}.
# This function map TARGET_ARCH to ${LTRACE_ARCH}.
def ltrace_arch(d):
	import re

	target_arch = bb.data.getVar('TARGET_ARCH', d, 1)
	if   re.match('(i.86|athlon)$', target_arch):    return 'i386'
	elif re.match('p(pc|owerpc)(|64)', target_arch): return 'ppc'
	else:                                            return target_arch

LTRACE_ARCH = "${@ltrace_arch(d)}"
EXTRA_OEMAKE += "ARCH=${LTRACE_ARCH}"
