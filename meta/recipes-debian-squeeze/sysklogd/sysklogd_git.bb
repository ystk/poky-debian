#
# HIDDEN/recipes-extended/sysklogd/sysklogd_1.5.bb
#

require sysklogd.inc
PR = "r0"

#
# debian-squeeze
#

inherit debian-squeeze

SRC_URI += "file://no-strip-install.patch;patch=1 \
	file://nonrootinstall.patch;patch=1 \
	file://sysklogd "

LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b \
                    file://syslogd.c;beginline=2;endline=15;md5=77ffb2fec48c46d7ca0abb2d5813e7fd \  
                    file://klogd.c;beginline=2;endline=19;md5=7e87ed0ae6142de079bce738c10c899d \   
                   "
