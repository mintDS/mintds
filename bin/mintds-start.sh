#!/bin/sh

if [ $# -lt 1 ];
then
	echo "USAGE: $0 [-daemon] server.yaml"
	exit 1
fi

base_dir=$(dirname $0)

exec $base_dir/kafka-run-class.sh   $@