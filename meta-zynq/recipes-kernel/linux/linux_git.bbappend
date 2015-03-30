GIT_COMMIT = "d8abcf184e28acc496cb1fa8eb44a2bc3773cd41"

kernel_do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}" ${KERNEL_EXTRA_ARGS}
        if test "${KERNEL_IMAGETYPE_FOR_MAKE}.gz" = "${KERNEL_IMAGETYPE}"; then
                gzip -9c < "${KERNEL_IMAGETYPE_FOR_MAKE}" > "${KERNEL_OUTPUT}"
        fi
}

do_compile_kernelmodules() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        if (grep -q -i -e '^CONFIG_MODULES=y$' .config); then   
                oe_runmake modules  CC="${KERNEL_CC}" LD="${KERNEL_LD}" ${KERNEL_EXTRA_ARGS}
        else
                bbnote "no modules to compile"
        fi
}

