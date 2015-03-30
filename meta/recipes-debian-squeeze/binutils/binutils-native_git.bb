#
# Temporal split native recipe
#

require binutils.inc

LIC_FILES_CHKSUM="\
    file://src-release;endline=17;md5=4830a9ef968f3b18dd5e9f2c00db2d35\
    file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552\
    file://COPYING.LIB;md5=9f604d8a4f8e74f4f5140845a21b6674\
    file://COPYING3;md5=d32239bcb673463ab874e80d47fae504\
    file://COPYING3.LIB;md5=6a6a8e020838b23406c81b19c1d46df6\
    file://gas/COPYING;md5=d32239bcb673463ab874e80d47fae504\
    file://include/COPYING;md5=59530bdf33659b29e73d4adb9f9f6552\
    file://include/COPYING3;md5=d32239bcb673463ab874e80d47fae504\
    file://libiberty/COPYING.LIB;md5=a916467b91076e631dd8edb7424769c7\
    file://bfd/COPYING;md5=d32239bcb673463ab874e80d47fae504\
    "

#
# debian-squeeze
#

inherit native
inherit debian-squeeze

PR = "r0"

# syslex-regenerate-fix.patch: fix compile error (git fetch)
# libiberty_path_fix.patch: fix install path of libiberty (x86_64)
SRC_URI += " \
file://syslex-regenerate-fix.patch \
file://libiberty_path_fix.patch \
"

#
# FIXME: tempral fix
#
# binutila-native compile failed with many "set but not used"
# errors on x86_64 host system. See also tail of bintuils_git.bb,
# they seemed to be similar problems about x86_64 source code.
#
# NOTE: "-Wno-error=unused-but-set-variable" requires GCC >= 4.6,
# so gcc version in host system (NATIVE_GCCVERSION ?= "4.4")
# must be checked before build binutils-native. Please override
# this value in local.conf if you use newer gcc in host system.
#
CFLAGS += "${@['', '-Wno-error=unused-but-set-variable '][bb.utils.vercmp(('', bb.data.getVar('NATIVE_GCCVERSION', d), ''), ('', '4.7', '')) >= 0]}"
