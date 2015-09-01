#
# Debian
#

DESCRIPTION = "This is libConfuse, a library for parsing configuration files."
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://support/depcomp;md5=21504de31981a67b5b93b265029d1865"

inherit debian-squeeze autotools gettext
SECTION = "libs"
PR = "r0"


# Remove option --disable-silent-rules
CONFIGUREOPTS = " --build=${BUILD_SYS} \
                  --host=${HOST_SYS} \
                  --target=${TARGET_SYS} \
                  --prefix=${prefix} \
                  --exec_prefix=${exec_prefix} \
                  --bindir=${bindir} \
                  --sbindir=${sbindir} \
                  --libexecdir=${libexecdir} \
                  --datadir=${datadir} \
                  --sysconfdir=${sysconfdir} \
                  --sharedstatedir=${sharedstatedir} \
                  --localstatedir=${localstatedir} \
                  --libdir=${libdir} \
                  --includedir=${includedir} \
                  --oldincludedir=${oldincludedir} \
                  --infodir=${infodir} \
                  --mandir=${mandir} \
                  ${@append_libtool_sysroot(d)}"

EXTRA_OECONF += "--enable-shared"

CFLAGS_append = " -fPIC"

PACKAGES =+ "lib${PN}-common lib${PN}-dev"

FILES_lib${PN}-dev = "${libdir} ${includedir}"
FILES_lib${PN}-common = "${datadir}"
