#!/bin/sh
# Requirements: sudo apt-get install seabios qemu-system
# Exit qemu: Ctrl-A + X (Screen: use Ctrl-A + A + X)
ARCH=`uname -m`

if [ ! -e ../../sysroots/${ARCH}-linux/usr/share/qemu/linuxboot.bin ]
then
ln -s /usr/share/seabios/optionrom/linuxboot.bin ../../sysroots/${ARCH}-linux/usr/share/qemu/
fi
if [ ! -e ../../sysroots/${ARCH}-linux/usr/share/qemu/vgabios-cirrus.bin ]
then
ln -s /usr/share/qemu/vgabios-cirrus.bin ../../sysroots/${ARCH}-linux/usr/share/qemu/
fi
if [ ! -e ../../sysroots/${ARCH}-linux/usr/share/qemu/pxe-e1000.bin ]
then
ln -s /usr/share/qemu/pxe-e1000.bin ../../sysroots/${ARCH}-linux/usr/share/qemu/
fi

if [ "$ARCH" == "x86_64" ]; then
    QEMU=../../sysroots/${ARCH}-linux/usr/bin/qemu-system-x86_64
else
    QEMU=../../sysroots/${ARCH}-linux/usr/bin/qemu-system-x86
fi

$QEMU -kernel bzImage-qemux86.bin -hda core-image-base-qemux86.ext3 -append "root=/dev/sda rw console=ttyS0,115200n8" -bios /usr/share/seabios/bios.bin -nographic
