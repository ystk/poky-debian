#
# debian-squeeze 
#

DESCRIPTION = "powerful and widely used FTP server"
SECTION = "net" 
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=d06717198a1e31bdb99dbeaaf4620b3d"
PR = "r0"

DEPENDS = "perl debianutils"

inherit autotools 
inherit debian-squeeze
