#
# git_1.7.5.1.bb
#

require git.inc

#PR = "r3"

EXTRA_OECONF += "ac_cv_snprintf_returns_bogus=no ac_cv_c_c99_format=yes \
                 ac_cv_fread_reads_directories=${ac_cv_fread_reads_directories=yes} \
                 "

#SRC_URI[md5sum] = "a49291116e3b0564e069ae989e4db6fb"
#SRC_URI[sha256sum] = "a1d4a1c59300e68fbc493a2cbe9257048d4d6f4363924bf34f38c413a825f80c"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

do_patch_srcpkg() {
	cd ${S}
	ls debian/diff > series
	QUILT_PATCHES=debian/diff \
	quilt --quiltrc /dev/null push -a
}
