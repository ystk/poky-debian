#
# debian-squeeze
#

DESCRIPTION = "Blowfish cryptography for Perl"
SECTION = "perl" 
LICENSE = "Artistic & GPL-1.0+"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=de597ddcff5df33b991439ed78f751e5 \
		    file://blowfish.h;endline=34;md5=4fe17f5d7a1c86d8a78f958bd0702e0e "
PR = "r0"

inherit debian-squeeze
inherit cpan

FILES_${PN}-dbg =+ ${libdir}/perl/*/*/auto/Crypt/Blowfish/.debug
