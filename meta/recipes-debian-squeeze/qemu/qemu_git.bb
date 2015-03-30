#
# qemu_git.bb
#

require qemu.inc

#SRCREV = "56a60dd6d619877e9957ba06b92d2f276e3c229d"

LIC_FILES_CHKSUM = "file://COPYING;md5=441c28d2cf86e15a37fa47e15a72fbac \
                    file://COPYING.LIB;endline=24;md5=c04def7ae38850e7d3ef548588159913"

#PV = "0.14.0"
#PR = "r1"

FILESPATH = "${FILE_DIRNAME}/qemu-${PV}/:${FILE_DIRNAME}/qemu-git/"
FILESDIR = "${WORKDIR}"

#SRC_URI = "\
#    git://git.qemu.org/qemu.git;protocol=git \
#    file://qemu-git-qemugl-host.patch \
#    file://no-strip.patch \
#    file://fix-nogl.patch \
#    file://qemugl-allow-glxcontext-release.patch \
#    file://linker-flags.patch \
#    file://qemu-vmware-vga-depth.patch \
#    file://enable-i386-linux-user.patch"

#S = "${WORKDIR}/git"

DEFAULT_PREFERENCE = "-1"

#
# debian-squeeze
#

inherit debian-squeeze

# FIXME: Temporal: Backport patch from 0.14
SRC_URI = "file://arm-bgr.patch"

#
# FIXME: Temporal .bb
#
# This recipe doesn't create BIOS images which may be required.
# BIOS images cannot be built on cross compiling.
# So we should download a built binary of BIOS from our repository.
#

EXTRA_OECONF += "--disable-blobs"
EXTRA_OECONF_virtclass-nativesdk += "--disable-blobs"

## Required BIOS binaries not included in qemu source package
#SRC_URI += " \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/o/openbios-ppc/openbios-ppc_1.0+svn640-1.dsc \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/o/openbios-ppc/openbios-ppc_1.0+svn640.orig.tar.gz \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/o/openbios-ppc/openbios-ppc_1.0+svn640-1.diff.gz;apply=no \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/o/openbios-sparc/openbios-sparc_1.0+svn640-1.dsc \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/o/openbios-sparc/openbios-sparc_1.0+svn640.orig.tar.gz \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/o/openbios-sparc/openbios-sparc_1.0+svn640-1.diff.gz;apply=no \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/s/seabios/seabios_0.5.1-3.dsc \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/s/seabios/seabios_0.5.1.orig.tar.gz \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/s/seabios/seabios_0.5.1-3.debian.tar.gz \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/v/vgabios/vgabios_0.6c-2.dsc \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/v/vgabios/vgabios_0.6c.orig.tar.gz \
#${DEBIAN_SQUEEZE_MIRROR}/pool/main/v/vgabios/vgabios_0.6c-2.debian.tar.gz \
#"
#
#BIOS_DIR = "${S}/pc-bios"
#S_OPENBIOS_PPC = "${WORKDIR}/openbios-ppc-1.0+svn640-1"
#S_OPENBIOS_SPARC = "${WORKDIR}/openbios-sparc-1.0+svn640-1"
#S_SEABIOS = "${WORKDIR}/seabios"
#
## Copy required bios images to the source directory
# ...
