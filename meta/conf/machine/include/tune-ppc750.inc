TARGET_CC_ARCH = "-mcpu=750"
BASE_PACKAGE_ARCH = "ppc750"
FEED_ARCH = "ppc750"
PACKAGE_EXTRA_ARCHS += "ppc750"

DEFAULTTUNE ?= "ppc750"

require conf/machine/include/powerpc/arch-powerpc.inc

TUNEVALID[ppc750] = "Enable ppc750 specific processor optimizations"
TUNE_CCARGS += "${@bb.utils.contains("TUNE_FEATURES", "ppc750", "-mcpu=750", "", d)}"
TUNE_PKGARCH = "${@bb.utils.contains('TUNE_FEATURES', 'ppc750', 'ppc750', '${PPCPKGARCH}', d)}"

AVAILTUNES = "ppc750"
TUNE_FEATURES_tune-ppc750 = "m32 fpu-hard ppc750"
PACKAGE_EXTRA_ARCHS_tune-ppc750 = "powerpc ppc750"
