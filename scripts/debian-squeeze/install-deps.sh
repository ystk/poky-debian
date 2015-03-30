#!/bin/sh

#
# install-deps.sh
#
# Install all essential packages needed to run poky-debian.
# Please run this script as root user.
#

POKY_EDISON=" \
sed wget cvs subversion git-core coreutils \
unzip texi2html texinfo libsdl1.2-dev docbook-utils gawk \
python-pysqlite2 diffstat help2man make gcc build-essential \
g++ desktop-file-utils chrpath libgl1-mesa-dev libglu1-mesa-dev \
mercurial autoconf automake groff libtool xterm \
"

POKY_DEBIAN_SQUEEZE=" \
quilt \
dpatch \
lsb-release \
debhelper \
ruby \
python-gobject \
python-gtk2 \
screen \
tcl8.5 \
flex \
realpath \
fakeroot \
"

apt-get install --no-install-recommends $POKY_EDISON $POKY_DEBIAN_SQUEEZE
