#
# debian-squeeze
#
DESCRIPTION = "kernel debugging utility, allowing gdb like syntax"
SECTION = "utils"
LICENSE = "GPL-3"
LIC_FILES_CHKSUM = "file://COPYING3;md5=d32239bcb673463ab874e80d47fae504"

inherit debian-squeeze autotools

DEPENDS += "binutils zlib ncurses"
RDEPENDS += "less"

CRASH_VERSION = "5.0.6"
COMPILER_VERSION = "gcc (Debian 4.4.5-8debian-squeeze3) 4.4.5"

SRC_URI += " \
file://kernel3_compatibility.patch \
file://3.x_support.patch \
file://var_length_log_rec.patch \
"

do_configure_prepend() {
	
	# according to configure.c
        target=""
	target_cflags=""
        if [ "${HOST_ARCH}" = "x86_64" ]; then
                target="X86_64"
		target_cflags=""
        elif [ "${HOST_ARCH}" = "i386" ] \
		|| [ "${HOST_ARCH}" = "i486" ] \
		|| [ "${HOST_ARCH}" = "i586" ] \
		|| [ "${HOST_ARCH}" = "i686" ]; then
		target="X86"
		target_cflags="-D_FILE_OFFSET_BITS=64"
	elif [ "${HOST_ARCH}" = "powerpc" ]; then
		target="PPC"
		target_cflags="-D_FILE_OFFSET_BITS=64"
	elif [ "${HOST_ARCH}" = "powerpc64" ]; then
		target="PPC64"
		target_cflags="-m64"
	fi
 
        # create build_data.c
        echo 'char *build_command = "crash";' >> build_data.c
        echo 'char *build_data = "Build for Debian Linux";' >> build_data.c
        echo 'char *build_target = "$target";' >> build_data.c
        echo 'char *build_version = "${CRASH_VERSION}";' >> build_data.c
        echo 'char *compiler_version = "${COMPILER_VERSION}";' >> build_data.c

        #
	# fix Makefile
	#

	# fix hardcode C compiler
        sed -i -e "s@\scc @\t${CC} @g" Makefile
        sed -i -e "388s#@cc#${CC}#" Makefile
        sed -i -e "s@^\sar @\t${AR} @g" Makefile

	# cross compile gdb
        sed -i -e "258s@;@ --host=${HOST_SYS};@" Makefile

	# avoid running configure again
        sed -i -e "/@.\/configure/d" Makefile

	# set target and compile flags
        sed -i -e "s@^TARGET=.*@TARGET=$target@" Makefile
	sed -i -e "s@^TARGET_CFLAGS=.*@TARGET_CFLAGS=$target_cflags@" Makefile
        sed -i -e "s@^GDB_FLAGS=.*@GDB_FLAGS=-DGDB_7_0@" Makefile
}

do_install_prepend() {
	install -d ${D}${bindir}
}
