inherit debian-squeeze autotools

PR = "r0"

LICENSE = "2-clause BSD License or MIT/X License"
LIC_FILES_CHKSUM = "file://nkf.c;md5=11958e8a91c81509a4784b50d728e58e"

# debian/patches doesn't exist, debian/rules doesn't have "patch" rules
# and "nkf1.7+cap1.patch" is not an essential patch, so nothing to do
do_patch_srcpkg_prepend() {
	patchfiles=$(find ${S}/debian \( \
		-name "*patch" -o \
		-name "*.diff" -o \
		-name "series" -o \
		-name "00list" \) | grep -v "nkf1.7+cap1.patch" || true)
	echo "$patchfiles"
	if [ -z "$patchfiles" ]; then
		# do nothing
		echo "nothing to do"
		exit 0
	fi
	# we need to check again if other patches will be added
}

# Change build tools to the cross toolchain
do_compile_prepend() {
	cat Makefile | sed "s%CC = cc%CC = ${TARGET_PREFIX}gcc%" > Makefile.tmp
	mv Makefile.tmp Makefile
}

# Makefile don't have a "install" target
do_install() {
	mkdir -p ${D}${bindir}
	cp nkf ${D}${bindir}
}
