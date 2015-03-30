DESCRIPTION = "A library for direct userspace use of RDMA (InfiniBand/iWARP)"
HOMEPAGE = "https://www.openfabrics.org/downloads/libibverbs/"
SECTION = "libs"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=7c557f27dd795ba77cc419dddc656b51"

inherit autotools 
inherit debian-squeeze

PR = "r0"

PACKAGES =+ "libibverbs-tests"

FILES_libibverbs-tests = "/usr/bin/ibv_*"

