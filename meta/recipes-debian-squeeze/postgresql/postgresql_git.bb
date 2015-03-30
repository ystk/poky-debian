require postgresql.inc

#PR = "${INC_PR}.0"
PR = "r0"

SRC_URI += " \
file://remove.autoconf.version.check.patch \
"

#SRC_URI[md5sum] = "689397187bb1dfe9b5cbde99538311c3"
#SRC_URI[sha256sum] = "08032849da121e67e318f7ff4e68d3ac88f29050e242641f717c4270839b597b"

# debian/patches/series doen't exist.
# All patches are applied by debian/rules (simple-patchsys.mk)
# So need to apply them here by almost the same way.
do_patch_srcpkg() {
	cd ${S}
	for patch in ${S}/debian/patches/*.patch; do
		echo "applying $patch..."
		patch -p1 < $patch
	done
}

DEPENDS += " tzdata"
EXTRA_OECONF += " \
--with-system-tzdata=${STAGING_DATADIR}/zoneinfo \
"

BBCLASSEXTEND = "native"
