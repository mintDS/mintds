#!/bin/sh

if [ $# -lt 1 ];
then
	echo "USAGE: $0 server.yaml"
	exit 1
fi

base_dir=$(dirname $0)

exec $base_dir/mintds-run-class.sh com.arturmkrtchyan.mintds.server.MintDsDaemon $@