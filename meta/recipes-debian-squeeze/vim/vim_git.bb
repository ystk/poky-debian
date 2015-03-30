#
# vim_7.2.bb
# Imported from OpenEmbedded (Jun 19 2012)
#

VIMVER = "72"

# vim-tiny sets that too
VIMFEATURES ?= "big"

# GUI type - gvim recipe sets "gtk2"
VIMGUI ?= "no"

# gvim recipes uses "--with-x"
VIMX ?= "--without-x"

require vim.inc

#PR = "${INC_PR}.3"

# 001-411.diff contains 411 patches fetched from upstream
#SRC_URI += "file://001-411.diff;patchdir=.."
#SRC_URI += "file://configure.in_remove_CC_quotes.patch;patchdir=.."
#SRC_URI += "file://vimrc"

do_install_append() {
    install -m 0644 ${WORKDIR}/vimrc ${D}/${datadir}/vim
}

RCONFLICTS_${PN} = "gvim vim-tiny"
PACKAGES =+ "${PN}-vimrc"

FILES_${PN}-vimrc = "${datadir}/vim/vimrc"

#
# debian-squeeze
#

LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://README.txt;firstline=44;endline=55;md5=6e26be4cdbd03d4eabdebfe4f66af1dc"

inherit debian-squeeze
DEBIAN_SQUEEZE_SRCPKG_NAME = "vim"

PR = "r0"

S = "${DEBIAN_SQUEEZE_UNPACKDIR}/src"

SRC_URI = "file://vimrc"
