#
# gcc-cross-canadian.inc
#

inherit cross-canadian

DESCRIPTION = "The cross-canadian GNU cc and gcc C compilers for ${TARGET_ARCH} target."
#PN = "gcc-cross-canadian-${TRANSLATED_TARGET_ARCH}"
#BPN = "gcc"

DEPENDS = "virtual/${HOST_PREFIX}binutils-crosssdk virtual/${TARGET_PREFIX}libc-for-gcc gettext-nativesdk"

#
# debian-squeeze
#

BPN = "gcc-default"

# Rename old gcc-cross-canadian to gcc-default-cross-canadian and
# newly define virtual/gcc-cross-canadian to select real recipe
# from gcc-default-cross-canadian or gcc-linaro-cross-canadian.
PROVIDES = "virtual/gcc-cross-canadian"
RPROVIDES_${PN} = "gcc-cross-canadian-${TRANSLATED_TARGET_ARCH}"
