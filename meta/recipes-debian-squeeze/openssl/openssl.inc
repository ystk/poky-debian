#
# openssl.inc
#

SUMMARY = "Secure Socket Layer"
DESCRIPTION = "Secure Socket Layer (SSL) binary and related cryptographic tools."
HOMEPAGE = "http://www.openssl.org/"
BUGTRACKER = "http://www.openssl.org/news/vulnerabilities.html"
SECTION = "libs/network"

# "openssl | SSLeay" dual license
LICENSE = "OpenSSL"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=f9a8f968107345e0b75aa8c2ecaa7ec8"

DEPENDS = "perl-native-runtime"

#SRC_URI = "http://www.openssl.org/source/openssl-${PV}.tar.gz \
#           file://parallel-make-fix.patch \
#          "
#S = "${WORKDIR}/openssl-${PV}"

AR_append = " r"
CFLAG = "${@base_conditional('SITEINFO_ENDIANNESS', 'le', '-DL_ENDIAN', '-DB_ENDIAN', d)} \
	-DTERMIO ${FULL_OPTIMIZATION} -Wall"

# Avoid binaries being marked as requiring an executable stack (which causes 
# issues with SELinux on the host)
CFLAG_append_virtclass-native = " -Wa,--noexecstack"

# -02 does not work on mipsel: ssh hangs when it tries to read /dev/urandom
CFLAG_mtx-1 := "${@'${CFLAG}'.replace('-O2', '')}"
CFLAG_mtx-2 := "${@'${CFLAG}'.replace('-O2', '')}"

export DIRS = "crypto ssl apps"
export EX_LIBS = "-lgcc -ldl"
export AS = "${CC} -c"

inherit pkgconfig siteinfo

#PACKAGES =+ "libcrypto libssl"
#FILES_libcrypto = "${libdir}/libcrypto.so.*"
#FILES_libssl = "${libdir}/libssl.so.*"
FILES_${PN} =+ " ${libdir}/ssl/*"

#do_configure () {
#	cd util
#	perl perlpath.pl ${STAGING_BINDIR_NATIVE}
#	cd ..
#	ln -sf apps/openssl.pod crypto/crypto.pod ssl/ssl.pod doc/
#
#	os=${HOST_OS}
#	if [ "x$os" = "xlinux-uclibc" ]; then
#		os=linux
#	fi
#	target="$os-${HOST_ARCH}"
#	case $target in
#	linux-arm)
#		target=linux-elf-arm
#		;;
#	linux-armeb)
#		target=linux-elf-armeb
#		;;
#	linux-sh3)
#		target=debian-sh3
#		;;
#	linux-sh4)
#		target=debian-sh4
#		;;
#	linux-i486)
#		target=debian-i386-i486
#		;;
#	linux-i586)
#		target=debian-i386-i586
#		;;
#	linux-i686)
#		target=debian-i386-i686/cmov
#		;;
#	linux-mips)
#		target=debian-mips
#		;;
#	linux-mipsel)
#		target=debian-mipsel
#		;;
#	linux-powerpc)
#		target=linux-ppc
#		;;
#	linux-gnuspe-powerpc)
#		target=linux-ppc
#		;;
#	linux-powerpc64)
#		target=linux-ppc64
#		;;
#	linux-supersparc)
#		target=linux-sparcv8
#		;;
#	linux-sparc)
#		target=linux-sparcv8
#		;;
#	esac
#	# inject machine-specific flags
#	sed -i -e "s|^\(\"$target\",\s*\"[^:]\+\):\([^:]\+\)|\1:${CFLAG}|g" Configure
#        useprefix=${prefix}
#        if [ "x$useprefix" = "x" ]; then
#                useprefix=/
#        fi        
#	perl ./Configure ${EXTRA_OECONF} shared --prefix=$useprefix --openssldir=${libdir}/ssl --libdir=`basename ${libdir}` $target
#}

do_compile () {
	oe_runmake
}

do_install () {
	install -m 0755 -d ${D}${libdir}/pkgconfig
	oe_runmake INSTALL_PREFIX="${D}" MANDIR="${mandir}" install
	chmod 644 ${D}${libdir}/pkgconfig/openssl.pc
	oe_libinstall -so libcrypto ${D}${libdir}
	oe_libinstall -so libssl ${D}${libdir}
}

#
# debian-squeeze
#

LIC_FILES_CHKSUM = \
	"file://LICENSE;md5=83d26c69f6f0172ee7f795790424b453"

# Additional configure options.
# Exclude three algorithm to avoid patent problems.
CONFOPTS = "no-idea no-mdc2 no-rc5"

do_configure () {
	cd util
	perl perlpath.pl ${STAGING_BINDIR_NATIVE}
	cd ..
	ln -sf apps/openssl.pod crypto/crypto.pod ssl/ssl.pod doc/

	os=${HOST_OS}
	if [ "x$os" = "xlinux-uclibc" ]; then
		os=linux
	fi
	target="$os-${HOST_ARCH}"

	# Fix ARM target name
	case $target in
	linux-*arm)
		target=debian-arm
		;;
	linux-*armeb)
		target=debian-armeb
		;;
	linux-sh3)
		target=debian-sh3
		;;
	linux-sh4)
		target=debian-sh4
		;;
	linux-i486)
		target=debian-i386-i486
		;;
	linux-i586)
		target=debian-i386-i586
		;;
	linux-i686)
		target=debian-i386-i686/cmov
		;;
	linux-mips)
		target=debian-mips
		;;
	linux-mipsel)
		target=debian-mipsel
		;;
	linux-powerpc)
		target=linux-ppc
		;;
	linux-gnuspe-powerpc)
		target=linux-ppc
		;;
	linux-powerpc64)
		target=linux-ppc64
		;;
	linux-supersparc)
		target=linux-sparcv8
		;;
	linux-sparc)
		target=linux-sparcv8
		;;
	esac
	# inject machine-specific flags
	sed -i -e "s|^\(\"$target\",\s*\"[^:]\+\):\([^:]\+\)|\1:${CFLAG}|g" Configure
        useprefix=${prefix}
        if [ "x$useprefix" = "x" ]; then
                useprefix=/
        fi        

	# Add CONFOPTS to remove some algorithm
	perl ./Configure ${EXTRA_OECONF} shared --prefix=$useprefix --openssldir=${libdir}/ssl --libdir=`basename ${libdir}` ${CONFOPTS} $target
}

# runtime packages should not be provided by ${BPN}-nativesdk
PACKAGES =+ "${PN}-libcrypto ${PN}-libssl"
FILES_${PN}-libcrypto = "${libdir}/libcrypto.so.*"
FILES_${PN}-libssl = "${libdir}/libssl.so.*"
RPROVIDES_${BPN}-libcrypto = "libcrypto"
RPROVIDES_${BPN}-libssl = "libssl"
