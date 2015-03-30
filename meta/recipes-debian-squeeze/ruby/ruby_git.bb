#
# debian-squeeze
#
DESCRIPTION = "Interpreter of object-oriented scripting language Ruby 1.8"
HOMEPAGE = "http://www.ruby-lang.org/"
SECTION = "ruby"
LICENSE = "LGPL-2"
LIC_FILES_CHKSUM = "file://LGPL;md5=7fbc338309ac38fefcd64b04bb903e34"
PR = "r0"

inherit debian-squeeze autotools

DEPENDS += "binutils gdbm ncurses readline zlib openssl tcl8.5"

RUBY_VERSION = "1.8"
DEBIAN_SQUEEZE_SRCPKG_NAME = "ruby${RUBY_VERSION}"

EXTRA_OECONF += " --enable-shared --disable-rpath"

do_compile_prepend() {
        ln -s ${S}/lib/mkmf.rb ${S}/ext/dl/mkmf.rb
        ln -s ${S}/ruby.h ${S}/ext/dl/ruby.h
}

FILES_${PN}-dbg += " \
${libdir}/ruby/${RUBY_VERSION}/${HOST_ARCH}-${TARGET_OS}/.debug \
${libdir}/ruby/${RUBY_VERSION}/${HOST_ARCH}-${TARGET_OS}/racc/.debug \
${libdir}/ruby/${RUBY_VERSION}/${HOST_ARCH}-${TARGET_OS}/io/.debug \
${libdir}/ruby/${RUBY_VERSION}/${HOST_ARCH}-${TARGET_OS}/digest/.debug \
"
