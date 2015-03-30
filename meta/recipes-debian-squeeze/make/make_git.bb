#
# make_3.82.bb
#

PR = "r0"
LICENSE="GPLv3&LGPLv2"
#LIC_FILES_CHKSUM = "file://tests/COPYING;md5=d32239bcb673463ab874e80d47fae504 \
#                    file://glob/COPYING.LIB;md5=4a770b67e6be0f60da244beb2de0fce4"
require make.inc

#SRC_URI[md5sum] = "1a11100f3c63fcf5753818e59d63088f"
#SRC_URI[sha256sum] = "e2c1a73f179c40c71e2fe8abf8a8a0688b8499538512984da4a76958d0402966"

#
# debian-squeeze
#

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "make-dfsg"

LIC_FILES_CHKSUM = " \
file://tests/COPYING;md5=8ca43cbc842c2336e835926c2166c28b \
file://glob/COPYING.LIB;md5=4a770b67e6be0f60da244beb2de0fce4 \
"
