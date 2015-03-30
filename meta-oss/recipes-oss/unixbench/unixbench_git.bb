SUMMARY = "the original BYTE UNIX benchmark suite"
DESCRIPTION = "basic indicator of the performance of a Unix-like system"
HOMEPAGE = "https://code.google.com/p/byte-unixbench/"
SECTION = "benchmarks"

LICENSE = "GPLv2-not-sure"
LIC_FILES_CHKSUM = "file://src/time-polling.c;md5=fd1b92e34c31a8daaeb05139bb8f24c8"

PR = "r0"

inherit debian-squeeze-misc

DEBIAN_SQUEEZE_MISC_GIT = "${DEBIAN_SQUEEZE_GIT_OSS}"
DEBIAN_SQUEEZE_MISC_COMMIT = "master"

SRC_URI = ""

RDEPENDS = " \
perl \
perl-module-archive-extract \
perl-module-archive-tar \
perl-module-compress-raw-zlib \
perl-module-compress-zlib \
libconvert-asn1-perl \
perl-module-cpan \
perl-module-cpanplus \
perl-dev \
perl-module-digest-sha \
perl-module-extutils-cbuilder \
perl-module-extutils-cbuilder-base \
perl-module-extutils-cbuilder-platform-aix \
perl-module-extutils-cbuilder-platform-cygwin \
perl-module-extutils-cbuilder-platform-darwin \
perl-module-extutils-cbuilder-platform-dec-osf \
perl-module-extutils-cbuilder-platform-os2 \
perl-module-extutils-cbuilder-platform-unix \
perl-module-extutils-cbuilder-platform-vms \
perl-module-extutils-cbuilder-platform-windows \
perl-module-extutils-embed \
perl-module-extutils-makemaker \
perl-module-extutils-parsexs \
perl-module-file-fetch \
perl-module-file-spec-unix \
perl-module-io-compress-base \
perl-module-io-compress-zlib-constants \
perl-module-io-compress-zlib-extra \
perl-module-io-zlib \
perl-module-ipc-cmd \
perl-lib \
perl-module-locale-maketext-simple \
perl-module-log-message \
perl-module-log-message-simple \
perl-module-build \
perl-module-build.pm \
perl-module-corelist.pm \
perl-module-load \
perl-module-load.pm \
perl-module-loaded.pm \
perl-module-pluggable \
perl-module-pluggable.pm \
libnet-telnet-perl \
perl-module-object-accessor \
perl-module-package-constants \
perl-module-params-check \
perl-module-pod-escapes \
perl-module-pod-simple \
perl-misc \
perl-module-term-ui \
perl-module-term-ui-history \
perl-module-test \
perl-module-test-harness \
perl-module-test-simple \
perl-module-time-piece \
perl-module-version \
perl-module-time-hires \
perl-module-strict \
perl-module-posix \
perl-module-vars \
perl-module-io \
perl-module-io-handle \
perl-module-warnings \
perl-module-warnings-register \
perl-module-xsloader \
perl-module-fcntl \
perl-module-config \
perl-module-carp-heavy \
perl-module-time \
perl-module-exporter-heavy \
perl-module-symbol \
perl-module-selectsaver \
"

FILES_${PN} = " \
/root/unixbench/ \
"

PARALLEL_MAKE = ""

do_compile() {
	oe_runmake GRAPHIC_TESTS= CC=${TARGET_PREFIX}gcc
}

do_install() {
	install -d ${D}/root/unixbench/pgms/
	cp ${S}/pgms/* ${D}/root/unixbench/pgms/
	cp ${S}/Run ${D}/root/unixbench/
	cp ${S}/README ${D}/root/unixbench/
}

PKG_SRC_CATEGORY = "oss"
