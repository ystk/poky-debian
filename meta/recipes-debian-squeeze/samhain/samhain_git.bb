#
# debian-squeeze
#

DESCRIPTION = "Data integrity and host intrusion alert system"
HOMEPAGE = "http://la-samhna.de/samhain/index.html"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8ca43cbc842c2336e835926c2166c28b"
SECTION = "admin"
PR = "r0"

inherit autotools
inherit debian-squeeze

SRC_URI += "file://fix_src.patch"

DEPENDS += "gnutls"

CONFIGUREOPTS = " --build=${BUILD_SYS} \                                                                   
                  --host=${HOST_SYS} \                                                                     
                  --target=${TARGET_SYS} \                                                                 
                  --prefix=${prefix} \                                                                     
                  --exec_prefix=${exec_prefix} \                                                           
                  --bindir=${bindir} \                                                                     
                  --sbindir=${sbindir} \                                                                   
                  --libexecdir=${libexecdir} \                                                             
                  --datadir=${datadir} \                                                                   
                  --sysconfdir=${sysconfdir} \                                                             
                  --sharedstatedir=${sharedstatedir} \                                                     
                  --localstatedir=${localstatedir} \                                                       
                  --libdir=${libdir} \                                                                     
                  --includedir=${includedir} \                                                             
                  --infodir=${infodir} \                            
                  --mandir=${mandir} "                                                   
 
do_configure() {
	gnu-configize
	autoconf
	oe_runconf
}
