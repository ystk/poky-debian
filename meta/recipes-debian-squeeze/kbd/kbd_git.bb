#
# debian-squeeze
#

DESCRIPTION = "Linux console font and keytable utilities"
SECTION = "utils"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=587ce626d15bd61699a64a6b8a5afefb"
PR = "r2"

inherit autotools gettext

BBCLASSEXTEND = "native"
RREPLACES_${PN} = "console-tools"
RPROVIDES_${PN} = "console-tools"
RCONFLICTS_${PN} = "console-tools"


FILES_${PN}-consolefonts = "${datadir}/consolefonts"
FILES_${PN}-consoletrans = "${datadir}/consoletrans"
FILES_${PN}-keymaps = "${datadir}/keymaps"
FILES_${PN}-unimaps = "${datadir}/unimaps"

ALTERNATIVE_NAMES_USRBIN = "chvt deallocvt fgconsole openvt"

PACKAGES += "${PN}-consolefonts ${PN}-keymaps ${PN}-unimaps ${PN}-consoletrans"

do_install_append() {
   usrbinprogs_a="${ALTERNATIVE_NAMES_USRBIN}"
   for p in $usrbinprogs_a; do
     if [ -f "${D}${bindir}/$p" ]; then
       mv "${D}${bindir}/$p" "${D}${bindir}/$p.${PN}"
     fi
   done
}

pkg_postinst_${PN} () {
   usrbinprogs_a="${ALTERNATIVE_NAMES_USRBIN}"
   for p in $usrbinprogs_a; do
     if [ -f "$D${bindir}/$p" ]; then
       update-alternatives --install ${bindir}/$p $p $p.${PN} 100
     fi
   done
}


pkg_postrm_${PN} () {
   usrbinprogs_a="${ALTERNATIVE_NAMES_USRBIN}"
   for p in $usrbinprogs_a; do
     update-alternatives --remove $p $p.${PN}
   done
}

inherit debian-squeeze

# remove a file with invalid string
DEBIAN_SQUEEZE_GIT_TSFIX_EXCLUDES = "doc/utf"
