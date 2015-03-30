require gcc-linaro-${PV}.inc
require gcc-configure-runtime.inc
require gcc-package-runtime.inc

SRC_URI_append = " \
file://fortran-cross-compile-hack2.patch \
file://Fix-missing-gthr-default.h.2.patch \
file://enable-glibcxx-has-gthreads.patch \
"

ARCH_FLAGS_FOR_TARGET += "-isystem${STAGING_INCDIR}"

EXTRA_OECONF += "--disable-libunwind-exceptions --disable-sjlj-exceptions"
EXTRA_OECONF_append_linuxstdbase = " --enable-clocale=gnu"
