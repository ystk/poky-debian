#
# debian-squeeze
#

DESCRIPTION = "binfmt_misc plugin for UTF-8 scripts"                      
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=0c56db0143f4f80c369ee3af7425af6e"        
SECTION = "interpreters"                                                      
PR = "r0"                                                                       
                                                                                
inherit autotools                                                               
inherit debian-squeeze                                                                 

SRC_URI += "file://fixMakefile.patch"
