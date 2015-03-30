#
# debian-squeeze
#
DESCRIPTION = "Miscellaneous utilities specific to Debian"
SECTION = "utils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://run-parts.c;beginline=5;endline=25;md5=99a3cac50ee294af7727f6b4f05146e4 \
		    file://tempfile.c;beginline=5;endline=25;md5=1229a3908ef5821bb9f29724fad66e16"
                    
PR = "r0"
DEPENDS = "sensible-utils"

inherit autotools
inherit debian-squeeze
