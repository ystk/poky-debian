#
# parameters to use gcc-linaro
#

GCCVERSION = "4.7"
SDKGCCVERSION = "4.7"

PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}gcc-initial = "gcc-linaro-cross-initial"
PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}gcc-intermediate = "gcc-linaro-cross-intermediate"
PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}gcc = "gcc-linaro-cross"
PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}g++ = "gcc-linaro-cross"
PREFERRED_PROVIDER_virtual/${TARGET_PREFIX}compilerlibs = "gcc-linaro-runtime"
PREFERRED_PROVIDER_libgcc = "libgcc-linaro"

PREFERRED_PROVIDER_virtual/${SDK_PREFIX}gcc-initial-crosssdk = "gcc-linaro-crosssdk-initial"
PREFERRED_PROVIDER_virtual/${SDK_PREFIX}gcc-intermediate-crosssdk = "gcc-linaro-crosssdk-intermediate"
PREFERRED_PROVIDER_virtual/${SDK_PREFIX}gcc-crosssdk = "gcc-linaro-crosssdk"
PREFERRED_PROVIDER_virtual/${SDK_PREFIX}g++-crosssdk = "gcc-linaro-crosssdk"
PREFERRED_PROVIDER_virtual/${SDK_PREFIX}compilerlibs-nativesdk = "gcc-linaro-runtime-nativesdk"
PREFERRED_PROVIDER_libgcc-nativesdk = "libgcc-linaro-nativesdk"

PREFERRED_PROVIDER_virtual/gcc-cross-canadian = "gcc-linaro-cross-canadian"
