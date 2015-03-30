COREBASE=$(realpath $(dirname ${BASH_SOURCE:-$0}))

if [ ! -d $COREBASE ]; then
	echo "ERROR: $COREBASE not found. Confirm working directory."
	return 2> /dev/null
fi

# BSPSET chooses one bblayers.conf by the following rule:
# BSPSET=xxx/yyy => meta-xxx/meta-yyy/conf/bblayers.conf
BSPSET=$1
if [ -z "$BSPSET" ]; then
	echo "ERROR: missing BSPSET"
	echo "usage: source $COREBASE/setup.sh <BSPSET>"
	return 2> /dev/null
fi
BUILDDIR=build-$(echo "$BSPSET" | tr '/' '-')
BBLAYERS=$COREBASE/meta-$(echo "$BSPSET" | sed "s@/@/meta-@g")/conf/bblayers.conf
if [ ! -f $BBLAYERS ]; then
	echo "ERROR: $BBLAYERS not found"
	return 2> /dev/null
fi

# setup bblayers.conf
mkdir -p $BUILDDIR/conf
cp $BBLAYERS $BUILDDIR/conf/bblayers.conf
sed -i "s|##COREBASE##|$COREBASE|" $BUILDDIR/conf/bblayers.conf

# set poky environment variables
source $COREBASE/oe-init-build-env $BUILDDIR

# disable all proxies to fetch sources only from intranet
export http_proxy=""
export no_proxy=""
export RSYNC_PROXY=""
export GIT_PROXY_COMMAND=""
