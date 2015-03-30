DESCRIPTION = "Non-interactive ssh password authentication \
SSH's (secure shell) most common authentication mode is called "interactive \
keyboard password authentication", so called both because it is typically \
done via keyboard, and because openssh takes active measures to make sure \
that the password is, indeed, typed interactively by the keyboard. Sometimes, \
however, it is necessary to fool ssh into accepting an interactive password \
non-interactively. This is where sshpass comes in."

HOMEPAGE = "http://sourceforge.net/projects/sshpass"
SECTION = "utils"
LICENSE = "GPL-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
PR = "r0"

inherit debian-squeeze autotools
