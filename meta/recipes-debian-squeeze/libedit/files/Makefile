#	$NetBSD: Makefile,v 1.36 2007/05/28 12:06:18 tls Exp $
#	@(#)Makefile	8.1 (Berkeley) 6/4/93

#OS!=	sh -c "case `uname -s` in GNU|GNU/*) echo GNU ;; *) uname -s ;; esac"

#.if ${OS} == GNU || ${OS} == Linux
CFLAGS += -include bsd/bsd.h
MKLINT=no
#.endif

USE_SHLIBDIR=	yes

WARNS=	3
LIB=	edit
LIBS=-lncurses

#LIBDPLIBS+=     termcap ${.CURDIR}/../libterm

OSRCS=	chared.c common.c el.c emacs.c fcns.c filecomplete.c help.c hist.c \
	key.c map.c \
	parse.c prompt.c read.c refresh.c search.c sig.c term.c tty.c vi.c

MAN=	editline.3 editrc.5

MLINKS=	editline.3 el_init.3 editline.3 el_end.3 editline.3 el_reset.3 \
	editline.3 el_gets.3 editline.3 el_getc.3 editline.3 el_push.3 \
	editline.3 el_parse.3 editline.3 el_set.3 editline.3 el_get.3 \
	editline.3 el_source.3 editline.3 el_resize.3 editline.3 el_line.3 \
	editline.3 el_insertstr.3 editline.3 el_deletestr.3 \
	editline.3 history_init.3 editline.3 history_end.3 \
	editline.3 history.3 \
	editline.3 tok_init.3 editline.3 tok_end.3 editline.3 tok_reset.3 \
	editline.3 tok_line.3 editline.3 tok_str.3

# For speed and debugging
SRCS=   ${OSRCS} tokenizer.c history.c readline.c
# For protection
#SRCS=	editline.c tokenizer.c history.c readline.c
OOBJS= $(patsubst %.c,%.o,$(SRCS))

LIBEDITDIR?=${.CURDIR}

INCS= histedit.h
INCSDIR=/usr/include

CLEANFILES+=editline.c
CLEANFILES+=common.h.tmp editline.c.tmp emacs.h.tmp fcns.c.tmp fcns.h.tmp
CLEANFILES+=help.c.tmp help.h.tmp vi.h.tmp
CLEANFILES+=tc1.o tc1
CPPFLAGS+=-I. -I${LIBEDITDIR} 
CPPFLAGS+=-I. -I${.CURDIR}
CPPFLAGS+=#-DDEBUG_TTY -DDEBUG_KEY -DDEBUG_READ -DDEBUG -DDEBUG_REFRESH
CPPFLAGS+=#-DDEBUG_PASTE -DDEBUG_EDIT
#CFLAGS+=-I. 

AHDR=vi.h emacs.h common.h 
ASRC=${LIBEDITDIR}/vi.c ${LIBEDITDIR}/emacs.c ${LIBEDITDIR}/common.c

DPSRCS+=	${AHDR} fcns.h help.h fcns.c help.c
CLEANFILES+=	${AHDR} fcns.h help.h fcns.c help.c

SUBDIR=	readline
HOST_SH=sh
LIBEDITDIR=.
all: .depend libedit.a libedit.so

vi.h: vi.c makelist
	${HOST_SH} ${LIBEDITDIR}/makelist -h ${LIBEDITDIR}/vi.c \
	    > $@.tmp && \
	    mv $@.tmp $@ \

emacs.h: emacs.c makelist
	${HOST_SH} ${LIBEDITDIR}/makelist -h ${LIBEDITDIR}/emacs.c \
	    > $@.tmp && \
            mv $@.tmp $@

common.h: common.c makelist
	${HOST_SH} ${LIBEDITDIR}/makelist -h ${LIBEDITDIR}/common.c \
	    > $@.tmp && \
            mv $@.tmp $@

fcns.h: ${AHDR} makelist
	${HOST_SH} ${LIBEDITDIR}/makelist -fh ${AHDR} > $@.tmp && \
	    mv $@.tmp $@

fcns.c: ${AHDR} fcns.h help.h makelist
	${HOST_SH} ${LIBEDITDIR}/makelist -fc ${AHDR} > $@.tmp && \
	    mv $@.tmp $@

help.c: ${ASRC} makelist
	${HOST_SH} ${LIBEDITDIR}/makelist -bc ${ASRC} > $@.tmp && \
	    mv $@.tmp $@

help.h: ${ASRC} makelist
	${HOST_SH} ${LIBEDITDIR}/makelist -bh ${ASRC} > $@.tmp && \
	    mv $@.tmp $@

editline.c: ${OSRCS}
	${HOST_SH} ${LIBEDITDIR}/makelist -e ${.ALLSRC:T} > $@.tmp && \
	    mv $@.tmp $@

tc1.o:	${LIBEDITDIR}/TEST/tc1.c
	
tc1:	libedit.a tc1.o 
	${_MKTARGET_LINK}
	${CC} ${LDFLAGS} ${.ALLSRC} -o ${.TARGET} libedit.a ${LDADD} -ltermcap

.depend: vi.h emacs.h common.h fcns.h help.h help.c
	-mkdir .a .so
.c.o:
	${CC} ${CFLAGS} -c $< -o .a/$*
	${CC} ${CFLAGS} -fPIC -c $< -o .so/$*
libedit.a: ${OOBJS}
	ar -r $@ .a/*
libedit.so: ${OOBJS}
	${CC} --shared -o $@ .so/* ${LIBS}
