Minnowboard fastboot
2014 (c) Daniel Sangorrin <daniel.sangorrin@toshiba.co.jp>
-------------------------------------------------------------------------------

Bootloader
==========

Ref: https://www.happyassassin.net/2014/01/25/uefi-boot-how-does-that-actually-work-then/
Ref: http://software.intel.com/en-us/articles/uefi-boot-manager-1
Ref: http://software.intel.com/en-us/articles/uefi-shell
Ref: http://www.ondatechnology.org/wiki/index.php?title=Booting_the_Linux_Kernel_without_a_bootloader
Ref: https://www.kernel.org/doc/Documentation/efi-stub.txt
Ref: https://wiki.ubuntu.com/EFIBootLoaders
Ref: https://wiki.archlinux.org/index.php/EFISTUB
Ref: http://www.rodsbooks.com/efi-bootloaders/efistub.html
Ref: https://wiki.archlinux.org/index.php/Unified_Extensible_Firmware_Interface
REf: http://download.intel.com/support/motherboards/server/sb/efi_whitepaper.pdf
Ref: http://download.intel.com/support/motherboards/server/sb/efi_instructions.pdf (startup.nsh)
Ref: http://software.intel.com/en-us/articles/efi-shells-and-scripting/ (commands and startup.nsh)
Ref: http://www.uefi.org/node/670/download/0bba3585ecbc8128512b1c7b9a86c041 (uefi spec with commands etc)
Ref: https://code.google.com/p/pentoo/wiki/UEFI#UEFI_EFI_shell_with_startup.nsh

a) Using GRUB2
   -----------

GRUB2 is an efi application located at:

/media/efi/EFI/BOOT/bootia32.efi
/media/efi/EFI/BOOT/grub.cfg

# vi grub.cfg
terminal_input console
default=theramdisk
timeout=0

menuentry 'theramdisk'{
linux /vmlinuz root=/dev/ram0 ro rootwait quiet console=ttyPCH0,115200
initrd /initrd.img
}

menuentry 'thesdcard'{
linux /vmlinuz root=/dev/mmcblk0p2 rw rootwait quiet console=ttyPCH0,115200
}

GRUB can be started as:

Shell> fs0:\EFI\BOOT\bootia32.efi

But we want to automatize, without even entering the shell:

[opt] shell> bcfg boot dump -v
[opt] shell> bcfg boot rm 0 <-- delete current boot entry
[opt] shell> reset

shell> connect -r
shell> map -r
shell> bcfg boot add 0 fs0:\efi\boot\bootia32.efi "Default Boot"
shell> reset

Note: CONFIG_BLK_DEV_RAM and CONFIG_BLK_DEV_INITRD must be enabled for 
      using initrd.

b) Booting from the Shell
   ----------------------

We can boot from the Shell directly as follows:

shell> fs0:
shell> list
Directory of: FS0:\
03/31/2014  23:45 <DIR>         2,048  EFI
04/01/2014  01:37           1,910,608  initrd.img
04/01/2014  17:05           1,478,672  vmlinuz.efi
          3 File(s)   4,868,016 bytes
shell> vmlinuz.efi root=/dev/ram0 ro initrd=\initrd.img rootwait quiet console=ttyPCH0,115200
[alt] shell> vmlinuz.efi root=/dev/mmcblk0p2 rw rootwait quiet console=ttyPCH0,115200

We can also automatize by preparing a startup.nsh file:

# vi /media/efi/startup.nsh
echo -off
cls
echo "Booting Debian with initramdisk"
fs0:\vmlinuz.efi root=/dev/ram0 ro initrd=\initrd.img rootwait quiet console=ttyPCH0,115200
[alt] fs0:\vmlinuz.efi root=/dev/mmcblk0p2 rw rootwait quiet console=ttyPCH0,115200

c) Booting directly from the UEFI boot manager
   -------------------------------------------

We can actually avoid the Shell at all, but in that case we need to specify
the boot parameters inside the kernel and we cannot use initrd.img:

CONFIG_RELOCATABLE=y
CONFIG_EFI=y
CONFIG_EFI_STUB=y
CONFIG_EFI_VARS=n
CONFIG_EFI_PARTITION=y
[opt] CONFIG_FB_EFI=y
[opt] CONFIG_FRAMEBUFFER_CONSOLE=y
[opt] CONFIG_EFIVAR_FS=y
CONFIG_CMDLINE="root=/dev/mmcblk0p2 rw rootwait quiet console=ttyPCH0,115200"

After configuring the kernel with the above options, we just need to 
set it as the default (0) boot option.

Remove the SDCard from Minnow and enter UEFI shell:

[opt] shell> bcfg boot dump -v
[opt] shell> bcfg boot rm 0 <-- delete current boot entry
[opt] shell> reset
shell> connect -r
shell> map -r
shell> bcfg boot add 0 fs0:\vmlinuz.efi "Default Boot"
shell> reset

User land
=========

inittab: 
::sysinit:/etc/init.d/rc S
::once:/bin/cat /proc/uptime
::respawn:-/bin/sh
::shutdown:/etc/init.d/rc E
#::respawn:/sbin/getty 38400 tty1
#::respawn:/sbin/getty 115200 ttyPCH0
#::ctrlaltdel:/sbin/reboot
#::once:/etc/init.d/load-keys

etc/:
.
├── default
├── fstab
├── group
├── init.d
│   ├── mountall
│   ├── rc
│   └── umountall
├── inittab
├── ld.so.cache
├── ld.so.conf
├── network
│   ├── if-down.d
│   ├── if-post-down.d
│   ├── if-pre-up.d
│   ├── if-up.d
│   └── interfaces
├── passwd
├── rcE.d
│   └── K90umountall -> ../init.d/umountall
├── rcS.d
│   └── S05mountall -> ../init.d/mountall
├── debian_squeeze_name
├── debian_squeeze_version
└── timestamp

busybox:
remove most of the functionality (leave a shell, ls, dmesg, mkdir, init).

Initram
=======

$ mkdir ramdisk
$ cd ramdisk/
# tar zxvf ../images/core-image-base-xeonboard-20140203081254.rootfs.tar.gz 

# ln -s /sbin/init .
# mknod -m 660 dev/ram0 b 1 1
# chown root.disk dev/ram0
# find . | cpio --create --format='newc' > /tmp/newinitrd
# gzip /tmp/newinitrd
# mv /tmp/newinitrd.gz /media/efi_/initrd.img

Points
======

- Initramfs: it 100ms extra for loading the image, mounting the SDCard is 
             about 700ms, so booting from initramfs is faster.
- quiet: makes the kernel to boot silently (we can check messages with dmseg).
         That improves kernel boot time from 3s to 0.6s.
- Kernel configuration:
        + Remove accounting/bug/legacy options.
        + Remove unused API: message queues, sysctl, fhandle, inotify...
        + Remove Networking/USB/Audio.
        + Filesystems: EXT2 and VFAT only. (ext3 is ext2+journal)
        + ACPI: Thermal only.
        + Remove Virtualization/Cryptography/Security
        + Remove Swap
        + Remove character encoding (only ASCII and ISO-xxx-1 latin)

Evaluation
==========

Total boot time: ~4.3s
  - EFI initialiation: ~4s
  - Kernel initialization: ~286ms
  - Userland initialization: ~10ms

Troubleshouting
===============

Q: I deleted the shell and cannot start it anymore.
A: boot from pxe using firmware.efi as the application.


