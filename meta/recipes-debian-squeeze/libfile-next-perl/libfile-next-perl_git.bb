#
# debian-squeeze
#

SUMMARY = "File::Next is an iterator-based module for finding files"

DESCRIPTION = "File::Next is an iterator-based module for finding files. \
It's lightweight, has no dependencies, runs under taint mode, and puts your \
program more directly in control of file selection."

inherit debian-squeeze cpan
SECTION = "perl"

LICENSE = "Artistic-2.0"
LIC_FILES_CHKSUM = "file://Next.pm;md5=ba047700f61d960ee299b5cbefd57684"
PR = "r0"

BBCLASSEXTEND = "native"

FILES_${PN} += "${libdir}"

do_compile_prepend () {
	sed -i -e "s@/vendor_perl@@g" ${S}/Makefile
}


