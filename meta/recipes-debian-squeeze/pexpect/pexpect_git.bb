#
# debian-squeeze
#
DESCRIPTION = "Python module for automating interactive applications"
SECTION = python
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://LICENSE;md5=04a2bf11b85ce49d4a8c0c413fd34404"

inherit debian-squeeze
inherit distutils

RDEPENDS = "python"

BBCLASSEXTEND = "native"
