DEFAULTTUNE ?= "armv5"

ARMPKGARCH ?= "armv5"

TUNEVALID[armv5] = "Enable instructions for ARMv5"
TUNE_CONFLICTS[armv5] = "armv4"
TUNE_CCARGS += "${@bb.utils.contains("TUNE_FEATURES", "armv5", "-march=armv5${ARMPKGSFX_THUMB}${ARMPKGSFX_DSP}", "", d)}"

ARMPKGSFX_DSP = "${@bb.utils.contains("TUNE_FEATURES", [ "armv5", "dsp" ], "e", "", d)}"

require conf/machine/include/arm/arch-armv4.inc
require conf/machine/include/arm/feature-arm-vfp.inc

# Little Endian
AVAILTUNES += "armv5 armv5t"
TUNE_FEATURES_tune-armv5 ?= "armv5"
TUNE_FEATURES_tune-armv5t ?= "armv5 thumb"
PACKAGE_EXTRA_ARCHS_tune-armv5 = "${PACKAGE_EXTRA_ARCHS_tune-armv4} armv5"
PACKAGE_EXTRA_ARCHS_tune-armv5t = "${PACKAGE_EXTRA_ARCHS_tune-armv4t} armv5 armv5t"

# Little Endian + VFP/DSP
AVAILTUNES += "armv5-vfp armv5t-vfp armv5hf-vfp armv5thf-vfp"
TUNE_FEATURES_tune-armv5-vfp ?= "armv5 vfp"
TUNE_FEATURES_tune-armv5t-vfp ?= "armv5 vfp"
TUNE_FEATURES_tune-armv5hf-vfp ?= "${TUNE_FEATURES_tune-armv5-vfp} callconvention-hard"
TUNE_FEATURES_tune-armv5thf-vfp ?= "${TUNE_FEATURES_tune-armv5t-vfp} callconvention-hard"
PACKAGE_EXTRA_ARCHS_tune-armv5-vfp = "${PACKAGE_EXTRA_ARCHS_tune-armv5} armv5-vfp"
PACKAGE_EXTRA_ARCHS_tune-armv5t-vfp = "${PACKAGE_EXTRA_ARCHS_tune-armv5t} armv5-vfp armv5t-vfp"
PACKAGE_EXTRA_ARCHS_tune-armv5hf-vfp = "armv5hf-vfp"
PACKAGE_EXTRA_ARCHS_tune-armv5thf-vfp = "armv5hf-vfp armv5thf-vfp"
PACKAGE_EXTRA_ARCHS_tune-armv5tehf-vfp = "${PACKAGE_EXTRA_ARCHS_tune-armv5thf-vfp} armv5ehf-vfp armv5tehf-vfp"

# Big Endian
AVAILTUNES += "armv5b armv5tb"
TUNE_FEATURES_tune-armv5b ?= "${TUNE_FEATURES_tune-armv5} bigendian"
TUNE_FEATURES_tune-armv5tb ?= "${TUNE_FEATURES_tune-armv5t} bigendian"
PACKAGE_EXTRA_ARCHS_tune-armv5b = "${PACKAGE_EXTRA_ARCHS_tune-armv4} armv5b"
PACKAGE_EXTRA_ARCHS_tune-armv5tb = "${PACKAGE_EXTRA_ARCHS_tune-armv4t} armv5b armv5tb"

# Big Endian + VFP/DSP
AVAILTUNES += "armv5b-vfp armv5tb-vfp armv5hfb-vfp armv5thfb-vfp"
TUNE_FEATURES_tune-armv5b-vfp ?= "${TUNE_FEATURES_tune-armv5-vfp} bigendian"
TUNE_FEATURES_tune-armv5tb-vfp ?= "${TUNE_FEATURES_tune-armv5t-vfp} bigendian"
TUNE_FEATURES_tune-armv5hfb-vfp ?= "${TUNE_FEATURES_tune-armv5b-vfp} callconvention-hard"
TUNE_FEATURES_tune-armv5thfb-vfp ?= "${TUNE_FEATURES_tune-armv5tb-vfp} callconvention-hard"
PACKAGE_EXTRA_ARCHS_tune-armv5b-vfp = "${PACKAGE_EXTRA_ARCHS_tune-armv5} armv5b-vfp"
PACKAGE_EXTRA_ARCHS_tune-armv5tb-vfp = "${PACKAGE_EXTRA_ARCHS_tune-armv5t} armv5b-vfp armv5tb-vfp"
PACKAGE_EXTRA_ARCHS_tune-armv5hfb-vfp = "armv5hfb-vfp"
PACKAGE_EXTRA_ARCHS_tune-armv5thfb-vfp = "armv5hfb-vfp armv5thfb-vfp"


