# modify sshd_config
do_install_append() {
	# Server settings
	sed -i "s/[#]*\(PermitEmptyPasswords\) .*/\1 yes/" ${D}${sysconfdir}/ssh/sshd_config
	sed -i "s/[#]*\(PermitRootLogin\) .*/\1 yes/" ${D}${sysconfdir}/ssh/sshd_config

	# Client (scp) settings
	echo "StrictHostKeyChecking no" >> ${D}${sysconfdir}/ssh/ssh_config
	echo "IdentityFile /etc/ssh/ssh_host_rsa_key" >> ${D}${sysconfdir}/ssh/ssh_config

	# All sshd subprocess should be generated with oom_adj=0
	mkdir -p ${D}${sysconfdir}/default
	echo "echo 0 > /proc/self/oom_adj" >> ${D}${sysconfdir}/default/ssh
}
