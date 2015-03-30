#
# eglibc_2.12.bb
#

require eglibc.inc

DEPENDS += "gperf-native"
#PR = "r24"

SRCREV = "14158"

#EGLIBC_BRANCH="eglibc-2_12"
#SRC_URI = "svn://www.eglibc.org/svn/branches/;module=${EGLIBC_BRANCH};proto=http \
#           file://eglibc-svn-arm-lowlevellock-include-tls.patch \
#           file://IO-acquire-lock-fix.patch \
#           file://shorten-build-commands.patch \
#           file://mips-rld-map-check.patch \
#           file://armv4-eabi-compile-fix.patch \
#           file://etc/ld.so.conf \
#           file://generate-supported.mk \
#	  "
SRC_URI_append_virtclass-nativesdk = " file://ld-search-order.patch"
#S = "${WORKDIR}/${EGLIBC_BRANCH}/libc"
B = "${WORKDIR}/build-${TARGET_SYS}"

PACKAGES_DYNAMIC = "libc6*"
RPROVIDES_${PN}-dev = "libc6-dev virtual-libc-dev"
PROVIDES_${PN}-dbg = "glibc-dbg"

# the -isystem in bitbake.conf screws up glibc do_stage
BUILD_CPPFLAGS = "-I${STAGING_INCDIR_NATIVE}"
TARGET_CPPFLAGS = "-I${STAGING_DIR_TARGET}${layout_includedir}"

GLIBC_ADDONS ?= "ports,nptl,libidn"

GLIBC_BROKEN_LOCALES = " _ER _ET so_ET yn_ER sid_ET tr_TR mn_MN gez_ET gez_ER bn_BD te_IN es_CR.ISO-8859-1"

FILESPATH = "${@base_set_filespath([ '${FILE_DIRNAME}/eglibc-${PV}', '${FILE_DIRNAME}/eglibc', '${FILE_DIRNAME}/files', '${FILE_DIRNAME}' ], d)}"

#
# For now, we will skip building of a gcc package if it is a uclibc one
# and our build is not a uclibc one, and we skip a glibc one if our build
# is a uclibc build.
#
# See the note in gcc/gcc_3.4.0.oe
#

python __anonymous () {
    import bb, re
    uc_os = (re.match('.*uclibc$', bb.data.getVar('TARGET_OS', d, 1)) != None)
    if uc_os:
        raise bb.parse.SkipPackage("incompatible with target %s" %
                                   bb.data.getVar('TARGET_OS', d, 1))
}

export libc_cv_slibdir = "${base_libdir}"

EXTRA_OECONF = "--enable-kernel=${OLDEST_KERNEL} \
                --without-cvs --disable-profile --disable-debug --without-gd \
                --enable-clocale=gnu \
                --enable-add-ons=${GLIBC_ADDONS},ports \
                --with-headers=${STAGING_INCDIR} \
                --without-selinux \
                ${GLIBC_EXTRA_OECONF}"

EXTRA_OECONF += "${@get_libc_fpu_setting(bb, d)}"

do_unpack_append() {
	bb.build.exec_func('do_move_ports', d)
}

do_move_ports() {
        if test -d ${WORKDIR}/${EGLIBC_BRANCH}/ports ; then
	    rm -rf ${S}/ports
	    mv ${WORKDIR}/${EGLIBC_BRANCH}/ports ${S}/
	fi
}

do_configure () {
# override this function to avoid the autoconf/automake/aclocal/autoheader
# calls for now
# don't pass CPPFLAGS into configure, since it upsets the kernel-headers
# version check and doesn't really help with anything
        if [ -z "`which rpcgen`" ]; then
                echo "rpcgen not found.  Install glibc-devel."
                exit 1
        fi
        (cd ${S} && gnu-configize) || die "failure in running gnu-configize"
        find ${S} -name "configure" | xargs touch
        CPPFLAGS="" oe_runconf
}

rpcsvc = "bootparam_prot.x nlm_prot.x rstat.x \
	  yppasswd.x klm_prot.x rex.x sm_inter.x mount.x \
	  rusers.x spray.x nfs_prot.x rquota.x key_prot.x"

do_compile () {
	# -Wl,-rpath-link <staging>/lib in LDFLAGS can cause breakage if another glibc is in staging
	unset LDFLAGS
	base_do_compile
	(
		cd ${S}/sunrpc/rpcsvc
		for r in ${rpcsvc}; do
			h=`echo $r|sed -e's,\.x$,.h,'`
			rpcgen -h $r -o $h || bbwarn "unable to generate header for $r"
		done
	)
}

require eglibc-package.inc

BBCLASSEXTEND = "nativesdk"

#
# debian-squeeze
#

inherit debian-squeeze

PR = "r0"

# shorten-build-commands.patch: Reduce non-essential build commands (optional)
SRC_URI = " \
file://etc/ld.so.conf \
file://generate-supported.mk \
file://shorten-build-commands.patch \
file://local-fix-libssp-detection.patch \
"

# "MAKEINFO=:" avoids an install error.
# This error occurs in installation of "manual" sub directory.
do_configure_prepend() {
	export MAKEINFO=:
}

# Add symlink of dynamic linker only if ARM hard float is used as target ABI.
# This link is needed only if newer gcc is used. The both of newer gcc and
# newer eglibc use this name (ld-linux-arm${ARMPKGSFX_EABI}.so.*).
# But Debian eglibc (old) cannot support this name, so we need to add it here.
#
# NOTE: This code is only for armhf. We need to make it more flexible
#       in order to handle the same problems on other architectures.
EGLIBC_LD_ARMHF = "ld-linux.so.3"
EGLIBC_LD_ARMHF_LINK = "ld-linux-armhf.so.3"
do_install_append() {
	if [ "${PN}" = "eglibc" -a "${TARGET_ARCH}" = "arm" -a "${ARMPKGSFX_EABI}" = "hf" ]; then
		if [ ! -e ${D}${base_libdir}/${EGLIBC_LD_ARMHF} ]; then
			echo "cannot find ${D}${base_libdir}/${EGLIBC_LD_ARMHF}"
			exit 1
		fi
		ln -s ${EGLIBC_LD_ARMHF} ${D}${base_libdir}/${EGLIBC_LD_ARMHF_LINK}
	fi
}
