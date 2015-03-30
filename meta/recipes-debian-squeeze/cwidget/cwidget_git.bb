#
# debian-squeeze
#
DESCRIPTION = "high-level terminal interface library for C++ (runtime files)"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
SECTION = "libs"

inherit debian-squeeze autotools gettext
DEPENDS += "libsigc++-2.0 ncurses"
HEADER="button.h        \
        bin.h           \
        center.h        \
        container.h     \
        editline.h      \
        menu.h          \
        layout_item.h   \
        label.h         \
        frame.h         \
        statuschoice.h  \
        scrollbar.h     \
        passthrough.h   \
        pager.h         \
        multiplex.h     \
        minibuf_win.h   \
        menubar.h       \
        widget.h        \
        tree.h          \
        table.h         \
        treeitem.h      \
        transient.h     \
        togglebutton.h  \
        subtree.h       \
        text_layout.h   \
        staticitem.h    \
        size_box.h      \
        stacked.h       \
        radiogroup.h    \
        statuschoice.h  \
        scrollbar.h     \
        passthrough.h   \
        pager.h         \
        multiplex.h     \
        minibuf_win.h   \
        menubar.h"

do_compile_prepend() {
	sed -i 's:CXXFLAGS =:CXXFLAGS = -fno-strict-aliasing :' $(find -name "Makefile")
	sed -i 's:install-data-am\: install-widgetsincludeHEADERS:install-data-am\::' src/cwidget/widgets/Makefile
}
do_install() {
	oe_runmake install DESTDIR=${D}
	install -d ${D}/usr/include/cwidget/widgets
	for i in ${HEADER}; do
		install -m 0644 ${S}/src/cwidget/widgets/$i ${D}/usr/include/cwidget/widgets
	done
}
