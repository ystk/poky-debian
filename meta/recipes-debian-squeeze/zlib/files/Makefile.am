lib_LTLIBRARIES = libz.la

libz_la_SOURCES = adler32.c compress.c crc32.c \
                  uncompr.c deflate.c trees.c zutil.c inflate.c \
                  gzio.c infback.c inftrees.c inffast.c

libz_la_LDFLAGS = -version-number 1:2:3 --version-script zlib.map

include_HEADERS = zconf.h zlib.h zlibdefs.h
