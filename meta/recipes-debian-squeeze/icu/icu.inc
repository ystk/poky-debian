DESCRIPTION = "The International Component for Unicode (ICU) is a mature, portable set of C/C++ and Java libraries for Unicode support, software internationalization (I18N) and globalization (G11N), giving applications the same results on all platforms."
HOMEPAGE = "http://www-01.ibm.com/software/globalization/icu/index.jsp"

#BASE_SRC_URI = "ftp://ftp.software.ibm.com/software/globalization/icu/3.6/icu4c-3_6-src.tgz \
#           file://gccfix.patch;apply=yes \
#           file://fix-parallel-build.patch \
#           file://use-g++-for-linking.patch;apply=yes"
#SRC_URI = "${BASE_SRC_URI} \
#           file://noldlibpath.patch;apply=yes"
#SRC_URI_virtclass-native = "${BASE_SRC_URI}"

LICENSE = "ICU"
#LIC_FILES_CHKSUM = "file://../license.html;md5=5c94767cedb5d6987c902ac850ded2c6"

PARALLEL_MAKE = ""
FULL_OPTIMIZATION_arm = "-Os"

inherit autotools pkgconfig binconfig

do_configure() {
	libtoolize --force
	gnu-configize --force
	oe_runconf
}

#           
# debian-squeeze
#                                                                            

inherit debian-squeeze                                                              

# fix buffer overflow error:
#   Unable to open or read "../data/icupkg.inc" option file. status = U_BUFFER_OVERFLOW_ERROR
# see http://mail-index.netbsd.org/pkgsrc-bugs/2011/03/11/msg042109.html for more details
SRC_URI = "file://make-buffers-bigger.patch"

S = "${WORKDIR}/${BP}/source"
LIC_FILES_CHKSUM = "file://../license.html;md5=0e62dd7f740e071ee5c4ddab29067ac4"
