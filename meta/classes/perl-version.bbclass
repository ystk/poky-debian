#
# Debian source package version of perl
#
# perl recipe uses PV in some build points.
# If PV=git, Debian recipe need to get source package version
# from debian/changelog dynamically. But this dynamic variable
# cannot be used in FILES_*. So we define a static version
# variable "PV_SRCPKG" in this file.
#
# NOTE: Need to update this value whenever perl is updated
#

export PERL_PV = "5.10.1-17"
