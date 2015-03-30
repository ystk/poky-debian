#
# debian-squeeze-non-git.bbclass
#
# Generate a source URI list non git remote sources are listed in.
# Inherit it if SRC_URI includes sources which cannot be tagged.
#

DEBIAN_SQUEEZE_NON_GIT_LIST = "${TMPDIR}/debian-squeeze-non-git.list"

do_fetch_append() {
	bb.build.exec_func('add_to_non_git_list', d)
}

add_to_non_git_list() {
	for uri in ${SRC_URI}; do
		if ! echo "$uri" | grep -q "^file://"; then
			echo "$uri" >> ${DEBIAN_SQUEEZE_NON_GIT_LIST}
		fi
	done
}
