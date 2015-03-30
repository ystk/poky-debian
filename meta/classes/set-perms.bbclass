#
# debian-squeeze
#
# set_perms is used to set permission or owner of any files
# after all packages installed to rootfs.
#
# NOTE: "files/fs-perms.txt" should be used in poky,
# but it has a problem that all perms for directories
# are ignored because of populate_package().
# set_perms is implemented to replace fs-perms.txt.
#

# format: "<item1> <item2> ..."
# item: "file,options,permission,uid,gid"
# permission, uid, or gid can be "-" (means no change)
IMAGE_SET_PERMS ?= ""

set_perms() {
	for item in ${IMAGE_SET_PERMS}; do
		file=${IMAGE_ROOTFS}/$(echo $item | cut -d ',' -f 1)
		opts=$(echo $item | cut -d ',' -f 2)
		perm=$(echo $item | cut -d ',' -f 3)
		uid=$(echo $item | cut -d ',' -f 4)
		gid=$(echo $item | cut -d ',' -f 5)

		if [ ! -e $file ]; then
			echo "set_perms: $file not found"
			exit 1
		fi

		if [ "$perm" != "-" ]; then
			echo "set_perms: chmod $opts $perm $file"
			chmod $opts $perm $file || exit 1
		fi
		if [ "$uid" != "-" -a "$gid" != "-" ]; then
			echo "set_perms: chown $opts $uid:$gid $file"
			chown $opts $uid:$gid $file || exit 1
		fi
	done
}
