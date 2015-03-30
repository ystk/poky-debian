inherit kernel-arch

# Git repository name under ${DEBIAN_SQUEEZE_GIT_KERNEL}
LINUX_REPO ?= "linux-poky-debian.git"

# Default branch or tag
LINUX_SRCREV ?= "ltsi-poky-debian"

# Define SRCREV dynamically
# Priority: DEBIAN_SQUEEZE_TAG >> DEBIAN_SQUEEZE_PREFERRED_TAG > LINUX_SRCREV
# When DEBIAN_SQUEEZE_TAG is defined, DEBIAN_SQUEEZE_PREFERRED_TAG and
# LINUX_SRCREV are completely ignored. DEBIAN_SQUEEZE_PREFERRED_TAG
# is used only if it's available. If DEBIAN_SQUEEZE_PREFERRED_TAG is
# undefined or unavailable, LINUX_SRCREV is used instead.
def debian_squeeze_linux_define_srcrev(d):
	import os, re
	tag = bb.data.getVar('DEBIAN_SQUEEZE_TAG', d, 1)
	preferred_tag = bb.data.getVar('DEBIAN_SQUEEZE_PREFERRED_TAG', d, 1)
	default = bb.data.getVar('LINUX_SRCREV', d, 1)
        srcrev = ""
	if tag is not None:
		srcrev = tag
	elif preferred_tag is not None:
		uris = bb.data.getVar('SRC_URI', d, 1).split()
		for uri in uris:
			match = re.match(r'(git://.*)', uri)
			if match is not None:
				break
		repo = re.sub(";.*", "", match.group(1))
		cmd = 'test -n "$(git ls-remote %s %s)"' % (repo, preferred_tag)
		if os.system(cmd) is 0:
			srcrev = preferred_tag
		else:
			srcrev = default
	else:
		srcrev = default
	return srcrev
SRCREV = "${@debian_squeeze_linux_define_srcrev(d)}"

# Target configuration file. And finally,
# "${S}/${LINUX_CONF}" is used if a relative path given
# "${LINUX_CONF}" is used if a absolute path given
LINUX_CONF ?= ""

SRC_URI = "${DEBIAN_SQUEEZE_GIT_KERNEL}${LINUX_REPO};protocol=git"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

DEBIAN_SQUEEZE_GIT_LIST_BASE = "${TMPDIR}/debian-squeeze-git.list"

# information for do_package
# embed just file path to kernel-abiversion as PKG_SRC_VERSION
# because it's installed AFTER kernel build
PKG_SRC_CATEGORY ?= "kernel"
PKG_SRC_NAME ?= "kernel"
PKG_SRC_VERSION ?= "${STAGING_KERNEL_DIR}/kernel-abiversion"
PKG_SRC_URI = ""

die() {
	echo "$1"
	exit 1
}

python do_unpack_append () {
	bb.build.exec_func('output_git_log', d)
}

output_git_log() {
	cd ${S}
	GIT_URI=${DEBIAN_SQUEEZE_GIT_KERNEL}${LINUX_REPO}
	GIT_DIRPATH=$(echo $GIT_URI | sed "s@^.*://[^/]*/@@")
	GIT_SERVER=$(echo $GIT_URI | sed "s@^.*://\([^/]*\)/.*@\1@")
	GIT_COMMITID=$(git log -1 --pretty=format:%H)
	# fetch & checkout log
	echo "$GIT_DIRPATH $GIT_COMMITID" \
		>> ${DEBIAN_SQUEEZE_GIT_LIST_BASE}.$GIT_SERVER
	# used to generate rootfs-summary
	echo "$GIT_URI;${SRCREV};$GIT_COMMITID" >> ${WORKDIR}/.kernel-uri
}
