#
# debian-squeeze
#

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=a102552aca2f2cc3c23f8662e4c902a5"

PR = "r0"

inherit debian-squeeze native

SRC_URI = "file://mkinitramfs"

do_unpack_append() {
	bb.build.exec_func('setup_scripts', d)
}

setup_scripts() {
	# we put only "/conf/initramfs.conf" into initramfs
	sed -i "s|. /conf/arch.conf||" ${S}/init

	# mkinitramfs must use poky internal datadir
	sed -i "s@\(DATADIR\)=.*@\1=${STAGING_DIR_HOST}/${datadir}/initramfs-tools@" \
		${WORKDIR}/mkinitramfs
}

do_configure() {
	:
}

do_compile() {
	:
}

do_install() {
	# install mkinitramfs
	install -d ${D}/${sbindir}
	install -m 0755 ${WORKDIR}/mkinitramfs ${D}/${sbindir}

	# install data files
	DATADIR="${D}/${datadir}/initramfs-tools"
	install -d $DATADIR
	install -m 0755 ${S}/init $DATADIR
	cp -r ${S}/scripts $DATADIR
	install -d $DATADIR/scripts/init-top $DATADIR/scripts/init-bottom
	# Only "BOOT" is used by "init". Other configurations are not needed.
	install -d $DATADIR/conf
	echo "BOOT=local" > $DATADIR/conf/initramfs.conf
}
