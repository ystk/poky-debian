inherit debian-squeeze-linux-libc-headers

DESCRIPTION = "Sanitized set of 2.6 kernel headers for the C library's use"
SECTION = "devel"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7" 

# Running scripts/Makefile.headersinst in a deep directory
# sometimes causes the following error:
#   make[2]: execvp: /bin/sh: Argument list too long
# One of the following patches (depends on kernel version)
# should be applied to fix the error using xargs
#   205-fix-headers_install.patch (ver >= 3.10)
#   scripts-Makefile.headersinst-install-headers-from-sc.patch (3.10 > ver >= 3.7)
SRC_URI += " \
file://205-fix-headers_install.patch;apply=no \
file://scripts-Makefile.headersinst-install-headers-from-sc.patch;apply=no \
"

python do_patch_append() {
	bb.build.exec_func('fix_makefile_headersinst', d)
}

fix_makefile_headersinst() {
	patch=""
	if grep -q '$(CONFIG_SHELL) $< $(installdir) $(input-files); \\' ${S}/scripts/Makefile.headersinst; then
		# ver >= 3.10
		patch="205-fix-headers_install.patch"
	elif grep -q '$(PERL) $< $(installdir) $(SRCARCH) $(input-files); \\' ${S}/scripts/Makefile.headersinst; then
		# 3.10 > ver >= 3.7
		patch="scripts-Makefile.headersinst-install-headers-from-sc.patch"
	fi

	if [ -n "$patch" ]; then
		pushd ${S} > /dev/null
		echo "applying $patch..."
		patch -p1 < ${WORKDIR}/$patch
		popd > /dev/null
	else
		# ver < 3.7
		echo "no patch to fix Makefile.headersinst for this kernel version, do nothing"
	fi
}
