#
# debian-squeeze-fetch.bbclass
#

# Fetch "srcuri" by using core fetch function in bibake libraries.
# This function is shared by Debian bbclass files.
def debian_squeeze_fetch(d, srcuri):
	srcuri_array = (srcuri or "").split()
	if len(srcuri_array) == 0:
		return

	# Call fetch action (based on "base_do_fetch")
	localdata = bb.data.createCopy(d)
	bb.data.update_data(localdata)
	try:
		fetcher = bb.fetch2.Fetch(srcuri_array, localdata)
		fetcher.download()
	except bb.fetch2.BBFetchException, e:
		raise bb.build.FuncFailed(e)
