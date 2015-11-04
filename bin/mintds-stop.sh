#!/bin/bash
ps ax | grep -i 'MintDsDaemon' | grep java | grep -v grep | awk '{print $1}' | xargs kill -SIGTERM