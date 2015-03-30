#
# linux-perf.inc
#
# Functions and configurations to build perf modules.
# This file is based on three recipes from poky:
#   linux-tools.inc (edison)
#   perf.bb and perf-features.inc (daisy)
#

#
# copied from recipes-kernel/perf/perf-features.inc (daisy)
#

# available features: perf-scripting, perf-tui
# define none by default because they adds many packages
# like perl to DEPENDS and RDEPENDS of kernel
PERF_FEATURES_ENABLE ?= ""

def perf_feature_enabled(feature, trueval, falseval, d):
    """
    Check which perf features are enabled.

    The PERF_FEATURES_ENABLE variable lists the perf features to
    enable.  Override it if you want something different from what's
    listed above, which is the default.  If empty, the build won't
    enable any features (which may be exactly what you want, just a
    barebones perf without any extra baggage, what you get if you
    specify an empty feature list).

    Available perf features:
      perf-scripting: enable support for Perl and Python bindings
      perf-tui: enable support for the perf TUI (via libnewt)

    """
    enabled_features = d.getVar("PERF_FEATURES_ENABLE", True) or ""
    if feature in enabled_features:
        return trueval
    return falseval

#
# partially copied from recipes-kernel/perf/perf.bb (daisy)
#

# build-depends
TUI_DEPENDS = "${@perf_feature_enabled('perf-tui', 'libnewt:do_populate_sysroot', '',d)}"
SCRIPTING_DEPENDS = "${@perf_feature_enabled('perf-scripting', 'perl:do_populate_sysroot python:do_populate_sysroot', '',d)}"

# runtime-depends
SCRIPTING_RDEPENDS = "${@perf_feature_enabled('perf-scripting', 'perl perl-modules python', '',d)}"

# make options
SCRIPTING_DEFINES = "${@perf_feature_enabled('perf-scripting', '', 'NO_LIBPERL=1 NO_LIBPYTHON=1',d)}"
TUI_DEFINES = "${@perf_feature_enabled('perf-tui', '', 'NO_NEWT=1',d)}"

#
# partially copied from recipes-kernel/linux/linux-tools.inc (edison)
# partially modified to enable/disable perf-features
#

do_compile_perf() {
	oe_runmake -C ${S}/tools/perf CC="${CC}" LD="${LD}" prefix=${prefix} NO_DWARF=1 \
		${SCRIPTING_DEFINES} ${TUI_DEFINES}
}

fakeroot do_install_perf() {
	oe_runmake -C ${S}/tools/perf CC="${CC}" LD="${LD}" prefix=${prefix} DESTDIR=${D} install NO_DWARF=1 \
		${SCRIPTING_DEFINES} ${TUI_DEFINES}
}

# perf tasks
addtask compile_perf after do_compile before do_install
addtask install_perf after do_install before do_package

do_compile_perf[umask] = 022
do_install_perf[umask] = 022

# build-depends
do_compile_perf[depends] = " \
virtual/${MLPREFIX}libc:do_populate_sysroot \
${MLPREFIX}elfutils:do_populate_sysroot \
${TUI_DEPENDS} \
${SCRIPTING_DEPENDS} \
"

# runtime-depends
RDEPENDS_perf += "${SCRIPTING_RDEPENDS}"
