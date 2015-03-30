Minnowboard
2014 (c) Daniel Sangorrin <daniel.sangorrin@toshiba.co.jp>
-------------------------------------------------------------------------------

Ref: http://www.minnowboard.org/
Ref: http://elinux.org/Minnowboard:Physical_Computing (using GPIO on circuits)

0) Overview
   ========

Minnowboard is Open-Hardware including:

- ORCAD design file (Schematics in Pdf)
- Allegro board design file
- Gerber files
- Bill of materials

Minnowboard is Open-Software including:
- Flash UEFI firmware (except for initialization blobs)
- Yocto-based Amnstrong distro  
  Note: comes installed in the SDCard (kernel 3.8)

Shield hardware:
- Pin headers for the expansion port (for free with the coupon)
- Lure shields (in development)

1) Technical specs 
   ---------------

Ref: http://www.minnowboard.org/technical-features/
Ref: http://ark.intel.com/products/52493/Intel-Atom-Processor-E640-512K-Cache-1_00-GHz

Note: [] means integrated, -> means connected

Intel Atom E640 1GHz 32bit (1 core with 2 threads)
[Intel Graphics Media Accelerator 600] -> Sil1362 -> DVI over HDMI connector
-> GPIO ---> 8 buffered 3.3V/10mA lines (10 pin header with 3.3V and GND pins)
        `--> 2 user LEDs (+3 LEDs for pwr/cpuhot/pwrok)
        `--> 4 user button switches (+2 switches for pwr/reset)
-> JTAG connector
-> Audio (ALC262) -> 1/8 In/Out jacks
-> SPI 4MB Flash -> program header (can be reprogrammed)
-> RAM: 1GB DDR2
-> DAC6011 (clocks and power management)
-> Expansion connector (LVSD LCD, HD Audio, PCIe(2:1))
-> Intel Platform Controller hub EG20T (by PCIe[0])
----> Micro SD
----> AR8031_AL1A 10/100/1000 Ethernet
----> Micro USB-B (device)
----> 2xUSB (host)
----> UART[0] FT230XS -> Mini USB-B
----> SATA2 (3Gb/s) port
----> Expansion connector (smp, can, sata1, ubs[4], uart[2:1], i2c0,
      spi, gpio[7:0], sdio[1])
Note: there is a extra hw for the expansion connector which is free with coupon

2) Quickstart
   ==========

The SDCard comes with an Amnstrong linux.

- Insert the SDCard
- Connect power/ethernet/screen(DVI)/serial(115200) cables
- Username is 'root' and there is no password.

Also you can use ssh:

# ssh root@133.196.174.178

Use the mouse and buttons for the graphical interface.

# halt
The system is going down for system halt NOW!
root@minnow:~# [  376.634768] Power down. <--- takes some time!! (check LED0 until it does not blink)

3) Updating the UEFI firmware through SPI Flash
   ============================================

Ref: http://www.elinux.org/Minnowboard:SPI_Boot_flash

The original UEFI version was:

UEFI Interactive Shell v2.0. UEFI v2.31 (Firmware Version 0.93, 0x000000A0). Revision 1.02

Download the latest firmware image from:

http://uefidk.intel.com/content/minnowboard-uefi-firmware-download

Extract the zip into a USB pendrive (FAT32) and connect it to the Minnow. Then,
remove the SDCard and reboot.

Shell> fs0:
Shell> FirmwareUpdate -f MINNOW.fd

After reset you should see:

UEFI Interactive Shell v2.0. UEFI v2.31 (Firmware Version 1.00, 0x4E278000). Revision 1.02

Connect the SDCard. Now we need to configure the bootable partition with 
the system firmware from EFI shell:

shell> connect -r
shell> map -r
shell> bcfg boot add 0 fs0:\efi\boot\bootia32.efi "Default Boot"
shell> reset

TODO: check the development kit and see if i can play with the uefi.
There are also tutorials and videos.

4) Re-burn the Amnstrong rootfs
   ============================
   
Ref: http://elinux.org/Minnowboard:Preparing_microSD_Card
Ref: http://elinux.org/Minnowboard:Building_Angstrom_Linux_for_the_MinnowBoard

Copy the root filesystem onto the SDCard:

# wget http://files.minnowboard.org/os-images/angstrom/latest/Angstrom-systemd-GNOME-image-v2013.06-2014.01.02-minnow.hddimg.xz
# umount /dev/sdf2
# xzcat Angstrom-development-GNOME-image-eglibc-ipk-v2012.12-minnow-2013.07.10.img.xz | dd of=/dev/sdf

Configure the bootable partition with the system firmware from EFI shell:

shell> connect -r
shell> map -r
shell> bcfg boot add 0 fs0:\efi\boot\bootia32.efi "Default Boot"
shell> reset

Then in GRUB select:

- "boot" for booting with the rootfs as a loop device
- "install" for creating a filesystem on the sdcard. (didn't work!!)

Update packages (only if you selected install):

# opkg update
# opkg upgrade

Install new packages:

# opkg list | grep "*xxxx*"
# opkg install xxxx

5) Update to the LTSI 3.10.y kernel 
   ================================

Ref: https://www.linux.com/learn/tutorials/764780-how-to-install-the-ltsi-310-kernel-on-raspberry-pi-and-minnowboard

First we prepare the LTSI kernel sources:

$ git clone http://git.linuxfoundation.org/ltsi-kernel.git
$ git clone git://github.com/ystk/linux-poky-debian.git
$ cd linux-poky-debian/
$ git checkout -b v3.10.31 tags/v3.10.31
$ export QUILT_PATCHES=/home/dsl/kernel/ltsi-patches/
$ quilt push -a
$ git add --all
$ git commit -a

Alternatively use LTSI tree:

[ALT] $ cd linux-poky-debian/
[ALT] $ git checkout -b linux-3.10.y-ltsi-rc1 remotes/origin/linux-3.10.y-ltsi-rc1

Get the original config:

minnow# scp /proc/config.gz dsl@133.196.174.159:/home/dsl
vmware# zcat ../config.gz > config-minnow

Use it for compiling the kernel:

$ cd linux-poky-debian/
$ cp ../config-minnow .config
$ vi answers.txt 
1
yes n
$ make oldconfig < answers.txt
[Alt] $ make olddefconfig
[Alt] use config-minnow-3.10.31-ltsi
$ make menuconfig
Remove unneeded things
$ make <---- ARCH is not necessary because my host is 32 bit too.
[Alt] nohup make 1>/tmp/log.txt 2>&1 &  <--- 2>&1 means redirect stderr(2) to stdout(&1).
[Alt] tail -f /tmp/log.txt
$ make modules

Install the modules in the rootfs:

# chmod a+rwx rootfs.img
# mount -o loop rootfs.img /home/dsl/tmp/
# make INSTALL_MOD_PATH=/home/dsl/tmp/ modules_install

If it gives an error of "read-only": use a fresh rootfs.img.
If it gives an error of "no space available":

[Opt] Compile the bzimage without modules (must be bellow 10MB!) <-- WORKS!
      Note: I prepared a config file.
[Opt] Create a new image file of around 1GB and do it there.
[Opt] Use the yocto-built filesystem wich is not a rootfs.img but installed in
      the SDCard (well, a rootfs.img is also available). <-- WORKS!

Note: if modules are required at boot time, install them in initrd too
[Opt] # cd ../initramdisk/
[Opt] # gunzip < ../initrd | cpio -i --make-directories
[Opt] # make INSTALL_MOD_PATH=/home/dsl/initramdisk/ modules_install
[Opt] # find . | cpio --create --format='newc' > /tmp/newinitrd
[Opt] # gzip /tmp/newinitrd
[Opt] # mv /tmp/newinitrd.gz  /home/dsl/newinitrd

Install the kernel in the EFI partition:

# cp arch/arm/boot/zImage /media/Anstrong/vmlinuz

6) Yocto build
   ===========

Ref: http://git.yoctoproject.org/cgit/cgit.cgi/meta-minnow/tree/README?h=master
Ref: http://www.yoctoproject.org/docs/current/mega-manual/mega-manual.html

Install build dependencies:

$ sudo apt-get install gawk wget git-core diffstat unzip texinfo gcc-multilib \
     build-essential chrpath libsdl1.2-dev xterm

[Alt] $ sudo apt-get install gawk wget git-core diffstat unzip build-essential chrpath libsdl1.2-dev xterm unzip texinfo \
 git texi2html subversion ncurses-dev sed cvs git-core coreutils docbook-utils python-pysqlite2 help2man make \
 gcc g++ desktop-file-utils chrpath dosfstools kpartx
     
Download the source:

$ git clone https://git.yoctoproject.org/git/poky
$ cd poky
$ git clone https://git.yoctoproject.org/git/meta-minnow
$ git clone https://git.yoctoproject.org/git/meta-intel

Create a template build target:

$ source ./oe-init-build-env
poky/build$ tree
.
└── conf
    ├── bblayers.conf
    ├── local.conf
    └── templateconf.cfg

Configure your target:

$ vi conf/bblayers.conf
BBLAYERS = " \
  /home/dsl/pokys/poky/meta \
  /home/dsl/pokys/poky/meta-yocto \
  /home/dsl/pokys/poky/meta-yocto-bsp \
  /home/dsl/pokys/poky/meta-intel \
  /home/dsl/pokys/poky/meta-minnow \
  "

$ vi conf/local.conf
MACHINE = "minnow"
LICENSE_FLAGS_WHITELIST = "license_emgd-driver-bin"
[Alt] LICENSE_FLAGS_WHITELIST = "license_emgd-driver-bin commercial" <- video
NOISO = "1"

Proxy related configuration:

$ export ftp_proxy=http://proxyhost:8080
[TODO] not working.

Build the minnow layer:

$ bitbake core-image-minimal <-- WORKS at home! (not inside proxy)
[Alt] $ bitbake core-image-sato <-- includes X visual graphics

After completion it generates:

tmp/deploy/images# tree
├── bootia32.efi
├── bzImage--3.10.33+git0+6e0e756d51_21df0c8486-r0-minnow-20140327123837.bin
├── core-image-minimal-initramfs-minnow-20140327123837.rootfs.cpio.gz
├── core-image-minimal-initramfs-minnow-20140327123837.rootfs.manifest
├── core-image-minimal-minnow-20140327123837.hddimg
├── core-image-minimal-minnow-20140327123837.rootfs.ext3
├── core-image-minimal-minnow-20140327123837.rootfs.manifest
├── modules--3.10.33+git0+6e0e756d51_21df0c8486-r0-minnow-20140327123837.tgz
└── README_-_DO_NOT_DELETE_FILES_IN_THIS_DIRECTORY.txt

Then you can burn the image as:

Ref: https://github.com/openembedded/oe-core/blob/master/scripts/contrib/mkefidisk.sh

# umount /media/Angstrom
# chmod +x mkefidisk.sh
# ./mkefidisk.sh /dev/sdf core-image-minimal-minnow-20140327123837.hddimg /dev/mmcblk0

The scripts creates 3 partitions:

/dev/sdf1 -> /media/efi vfat (bootia32.efi, grub.cfg, vmlinuz)
/dev/sdf2 -> /media/root ext3 (root filesystem)
/dev/sdf3 -> swap

Reboot and update the firmware table:

shell> connect -r
shell> map -r
shell> bcfg boot add 0 fs0:\efi\boot\bootia32.efi "Default Boot"
shell> reset

Password is 'root'.

For mounting the EFI partition:

# mount /dev/mmcblk0p1 /mnt/

Copy the config file (3.10.33 LTSI):

# cp /proc/config.gz /mnt/

Problem "Invalid MAC address, interface disabled"

# ifconfig eth0 down
# ifconfig eth0 hw ether 00:13:20:fe:2e:8a
# ifconfig eth0 up
# ifconfig eth0 192.168.0.55 netmask 255.255.255.0

Or burn it from the UEFI Shell. Remove the SDCard, and insert USB pendrive 
with FirmwareUpdate.efi.

Shell> fs0:  
Shell> FirmwareUpdate -m 001320FE2E8A

7) Build and install poky-debian
   ==============================

# . poky-debian/setup minnow
# bitbake core-image-base

TODO: the console doesn't work!!

8) Re-build the Amstrong distro
   ============================

Ref: http://elinux.org/Minnowboard:Building_Angstrom_Linux_for_the_MinnowBoard
Ref: http://www.angstrom-distribution.org

TODO

9) Install Debian
   ==============

Ref: http://elinux.org/Minnowboard:Debian_Bare_Minimum_Bootstrapping

TODO

10) Tests
   =====

a) Test the user switches (buttons)
   --------------------------------

Out of the box, the minnowboard_keys driver maps the GPIO buttons to 
keystrokes via the gpio-keys-polled driver. To access them via sysfs, 
you must unload the driver and export the GPIO lines:

# modprobe -r minnowboard_keys
# cd /sys/class/gpio
# echo 0 > export
# echo 1 > export
# echo 2 > export
# echo 3 > export
# cat gpio0/value
1 <-- not pressed
# cat gpio0/value
0 <-- pressed

b) Test the two user LEDs
   ----------------------

Ref: http://elinux.org/Minnowboard:Toggling_LED

Functions of the LED(s) on the MinnowBoard
LED	 Function
D11	 Heartbeat/USER LED* <-- heartbeat: blinks at regular interval
D12	 microSD card activity/USER LED* <-- blinks when using SDCard
D13	 PWR <-- power on?
D14	 CPU HOT <-- the CPU is too hot! switch it off before damage!
D15	 PWROK <-- the power supply tolerance is ok

echo none > /sys/class/leds/minnow_led0/trigger <-- disables heartbeat
echo 1 > /sys/class/leds/minnow_led0/brightness
echo 0 > /sys/class/leds/minnow_led0/brightness

echo none > /sys/class/leds/minnow_led1/trigger <-- disables SDCard blink
echo 1 > /sys/class/leds/minnow_led1/brightness
echo 0 > /sys/class/leds/minnow_led1/brightness

c) Use other GPIO pins 
   -------------------
   
Ref: http://elinux.org/Minnowboard:GPIO_Programming

The jumper J9 has 1..10 pins:

GPIO on the MinnowBoard
pin	gpio	 Default Mode	 Default Value
1	 N.A.	 N.A.	 3.3V
2	 N.A.	 N.A.	 GND
3	 244	 INPUT	 HIGH
4	 245	 INPUT	 HIGH
5	 246	 INPUT	 HIGH
6	 247	 INPUT	 HIGH
7	 248	 INPUT	 HIGH
8	 249	 INPUT	 HIGH
9	 250	 INPUT	 HIGH
10	 251	 INPUT	 HIGH

Example: J9:pin3 will be referenced as gpio-244 in kernel.

Connect a LED to J9:pin3(gpio244) and J9:pin2(GND)

# echo out > /sys/class/gpio/gpio244/direction
# echo 1 > /sys/class/gpio/gpio244/value
# echo 0 > /sys/class/gpio/gpio244/value

 
