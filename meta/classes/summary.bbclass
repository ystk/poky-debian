#
# gen_summary generates summary information of deb packages
# installed in rootfs or sdk
#
# usage: gen_summary <summary> <available>
#   <summary>: a file path of the summary file
#   <available>: a file that has information about
#                all packages installed in the target
#

gen_summary() {
	SUMMARY_FILE=$1
	AVAILABLE=$2
	if [ -z "${SUMMARY_FILE}" -o -z "${AVAILABLE}" ]; then
		bbfatal "gen_summary: missing arguments"
	fi
	if [ ! -r "${AVAILABLE}" ]; then
		bbfatal "gen_summary: cannot read ${AVAILABLE}"
	fi

	rm -f ${SUMMARY_FILE}.*

	echo "gen_summary: generating summary information from ${AVAILABLE} ..."
	touch ${SUMMARY_FILE}.csv.tmp
	pkg=""
	sed "/^ /d" ${AVAILABLE} | while read line; do
		# "Package" field of a new package must be appear in this line
		if [ -z "$pkg" ]; then
			pkg=$(echo "$line" | sed "s@^Package: \(.*\)@\1@")
			if [ -z "$pkg" ]; then
				bbfatal "failed to parse a new package in ${AVAILABLE}"
			fi
			echo -n "$pkg" >> ${SUMMARY_FILE}.csv.tmp
		fi
		# other fields of $pkg or null (terminator) must be appear in this line

		# terminator
		if [ -z "$line" ]; then
			echo "" >> ${SUMMARY_FILE}.csv.tmp
			pkg=""
			continue
		fi
		# confirm line
		if ! echo "$line" | grep -q "^[^ ]*: "; then
			bbfatal "an invalid line found in ${AVAILABLE}: $line"
		fi
		# parse a field
		# fn: Field Name
		# fd: Field Data
		fn=$(echo "$line" | sed "s@^\([^:]*\): .*@\1@")
		fd=$(echo "$line" | sed "s@^[^:]*: \(.*\)@\1@")
		case "$fn" in
			"")
				bbfatal "an invalid line found in ${AVAILABLE}: $line"
				;;
			"Version"|"SourceCategory"|"SourceName"|"SourceURI"|"License")
				if [ -z "$fd" ]; then
					bbfatal "$fn of $pkg is null"
				fi
				echo -n ",$fd" >> ${SUMMARY_FILE}.csv.tmp
				;;
			"SourceVersion")
				if [ -z "$fd" ]; then
					bbfatal "$fn of $pkg is null"
				fi
				# kernel source version is defined in kernel-abiversion
				if [ $(basename "$fd") = "kernel-abiversion" ]; then
					echo -n ",$(cat $fd)" >> ${SUMMARY_FILE}.csv.tmp
				else
					echo -n ",$fd" >> ${SUMMARY_FILE}.csv.tmp
				fi
				;;
			*) ;;
		esac
	done
	sort ${SUMMARY_FILE}.csv.tmp | uniq > ${SUMMARY_FILE}.csv.tmp.sort
	mv ${SUMMARY_FILE}.csv.tmp.sort ${SUMMARY_FILE}.csv.tmp
	echo "PackageName,PackageVersion,SourceCategory,SourceName,SourceVersion,SourceURI,License" \
		> ${SUMMARY_FILE}.header
	cat ${SUMMARY_FILE}.header ${SUMMARY_FILE}.csv.tmp > ${SUMMARY_FILE}.csv
	rm -f ${SUMMARY_FILE}.header ${SUMMARY_FILE}.csv.tmp*
	sed "s@,@\t\t@g" ${SUMMARY_FILE}.csv > ${SUMMARY_FILE}.txt

	echo "gen_summary: done, see ${SUMMARY_FILE}.csv"
}
