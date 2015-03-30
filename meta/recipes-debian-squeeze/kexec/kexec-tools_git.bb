#
# recipes-kernel/kexec/kexec-tools_2.0.2.bb
#

require kexec-tools.inc
export LDFLAGS = "-L${STAGING_LIBDIR}"
EXTRA_OECONF = " --with-zlib=yes"

#PR = "r1"

#SRC_URI += " file://fix_for_compiling_with_gcc-4.6.0.patch"
#SRC_URI[md5sum] = "bc401cf3262b25ff7c9a51fc76c8ab91"
#SRC_URI[sha256sum] = "549ab65c18a2229b6bf21b6875ca6bbe0e579bca08c3543ce6aaf8287a0b4188"

#
# debian-squeeze
#

inherit debian-squeeze
SRC_URI += " file://fix_for_compiling_with_gcc-4.6.0.patch"

PR = "r0"
