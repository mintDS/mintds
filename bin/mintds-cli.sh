#!/bin/sh

HOST=localhost
PORT=7657

while [ $# -gt 0 ]; do
  COMMAND=$1
  case $COMMAND in
    --host)
      HOST=$2
      shift 2
      ;;
    --port)
      PORT=$2
      shift 2
      ;;
    *)
      break
      ;;
  esac
done

base_dir=$(dirname $0)

exec $base_dir/mintds-run-class.sh com.arturmkrtchyan.mintds.cli.MintDsTerminal $HOST $PORT