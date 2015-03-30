#
# debian-squeeze
#
DESCRIPTION = "Linux Key Management Utilities"                      
HOMEPAGE = "http://people.redhat.com/~dhowells/keyutils"
LICENSE = "GPLv2 & LGPLv2"                                                              
LIC_FILES_CHKSUM = "file://README;md5=a4b8e4f3a38d7d3fd118ef6f6b1f8268"        
SECTION = "admin"                                                      
PR = "r0"                                                                       
                                                                                
inherit autotools                                                               
inherit debian-squeeze                                                                 

PACKAGES += "libkeyutils1"
ALLOW_EMPTY-${PN} = "1"
FILES_libkeyutils1 = "/lib/*"
RDEPENDS += "libkeyutils1"
RPROVIDES_libkeyutils1 = "libkeyutils1"

