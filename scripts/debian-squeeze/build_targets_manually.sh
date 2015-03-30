#!/bin/sh
#
# USAGE:
#
#   (1) modify the TARGETS variable for building the targets you want.
#   (2) set the variable REMOVE_IF_SUCCESS to true or false.
#   (3) execute ./scripts/debian-squeeze/build_targets_manually.sh
#   (4) after completion the following logs will exist:
#      a) log.txt: contains a summary of the logs for each target.
#      b) log.xxx.txt: contains the bitbake log for each target.
#
# Copyright (C) 2014 TOSHIBA CORPORATION
#
# Last Modified 20-Jun-2014 
# Authors: <daniel.sangorrin@toshiba.co.jp>

# [IMPORTANT] modify TARGETS with the targets you want to build.
TARGETS="minnow
minnowfast
pandaboard/minimal
pandaboard/network
pandaboard/security
beagleboard
qemuarm
qemuppc
qemux86
qemux86-64
"

# Remove the build folder to save space (16GB!) if succesful
# [IMPORTANT] set to false if you want to conserve the result files
REMOVE_IF_SUCCESS=true

# # check that we are being executed from the right place
folder=$(basename `pwd`)
if [ "$folder" != "poky-debian" ]; then
	echo "[ERROR] execute this script inside folder poky-debian"
	echo "[ERROR] e.g.: ./scripts/build_targets_manually.sh"
	exit 1
fi

# Remove previous logs (stored at poky-debian/../)
rm -f ../log.*

# Start by logging the latest commit
echo -e "LAST COMMIT\n" > ../log.txt
git show --quiet HEAD >> ../log.txt
cd ..

# Build each target sequentially
for target in $TARGETS
do
   build_folder="build-$(echo $target | tr '/' '-')"
   target_log="log.$(echo $target | tr '/' '.').txt"
   echo -e "\nBuilding $target\n" >> log.txt
   yes | rm -Rf $build_folder
   . poky-debian/setup.sh $target
   bitbake core-image-base > ../$target_log
   cd ..
   if [ $(tail -n 1 $target_log | grep -c "didn't need to be rerun and 0 failed" ) != 0 ]
   then
      # [build succesful]
      tail -n 1 $target_log >> log.txt
      if [ "$REMOVE_IF_SUCCESS" = true ]; then
         yes | rm -Rf $build_folder
      fi
   else
      tail -n 20 $target_log >> log.txt
   fi
done
