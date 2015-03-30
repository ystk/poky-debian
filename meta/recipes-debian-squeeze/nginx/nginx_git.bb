DESCRIPTION = "small, but very powerful and efficient web server and mail proxy \
Nginx (engine x) is a web server created by Igor Sysoev and kindly provided to \
the open-source community. This server can be used as standalone HTTP server \
and as a reverse proxy server before some Apache or another big server to \
reduce load to backend servers by many concurrent HTTP-sessions."

SECTION = "httpd"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a2b87213506e111fd2bb5e87d98dc630"

inherit autotools debian-squeeze

DEPENDS += " pcre openssl geoip zlib"

CONFIGUREOPTS = " --prefix=${prefix}"

SRC_URI += " \
file://Makefile;apply=no \
file://Makefile.obj;apply=no \
file://ngx_auto_config.h;apply=no \
file://ngx_auto_headers.h;apply=no \
file://ngx_modules.c;apply=no \
"

do_configure() {
	:
}

do_compile_prepend() {
	cp ${WORKDIR}/Makefile ${S}
	mkdir ${S}/objs
	cp ${WORKDIR}/ngx_* ${S}/objs
	cp ${WORKDIR}/Makefile.obj ${S}/objs/Makefile
	cp -r ${S}/src ${S}/objs
	sed -i -e "s:^CC =.*:CC = ${HOST_SYS}-gcc:" ${S}/objs/Makefile
	sed -i -e "s:^CFLAGS = .*:CFLAGS = ${CFLAGS}:" ${S}/objs/Makefile
	sed -i -e "s:^CPP = .*:CPP = ${HOST-SYS}-gcc -E:" ${S}/objs/Makefile
	sed -i -e "s:local/::g" ${S}/objs/Makefile

	sed -i -e "s:usr/local:usr:g" $(grep -nr "usr/local" ${S} | cut -f1 -d: | sort | uniq)
}

FILES_${PN} = " \
${exec_prefix}/nginx/* \
"
