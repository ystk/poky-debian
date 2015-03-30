#!/usr/bin/env python

import sys
import os
import csv
import commands

output_file_name="packagename-file-list.csv"

argvs = sys.argv
argc = len(argvs)

if(argc != 2):
	print 'Usage: # python %s path-to-build-directory' % argvs[0]
	quit()

# find rootfs-summary.csv
rootfs_summary_path = commands.getoutput(
	"find "+argvs[1]+"/tmp/work/*/core-image-base*/  \
	-name rootfs-summary.csv")
if rootfs_summary_path is "":
	print "ERROR: rootfs-summary not foound\n"
	quit()

# initialize output file.
with open(output_file_name, "wb") as f:
	f.write("PackageName,File\n")
		
# open rootfs-summary.csv.
with open(rootfs_summary_path, 'rb') as f:
    reader = csv.reader(f)
    for row in reader:

	# ignore first row
	if row[0] != 'PackageName':
	
		deb_package_name = row[0]+"_"+row[1]+"_*.deb"

		# find deb file
		deb_file_path = commands.getoutput(
			"find "+argvs[1]+"/tmp/deploy/deb/ -name "
			+deb_package_name)

		# update deb_package_name correctly
		deb_package_name = row[0]
		
		if deb_file_path is not "":
			check = commands.getoutput("dpkg-deb --contents "+deb_file_path)
			for line in check.splitlines():
				filelist = line.split()
				# filename is fillist[5] and remove first char "."
				filename = filelist[5][1:]
				with open(output_file_name,'a') as f:
					f.write(deb_package_name+","+filename+"\n")

