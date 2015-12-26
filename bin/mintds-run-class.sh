#!/bin/bash

if [ $# -lt 1 ];
then
  echo "USAGE: $0  classname"
  exit 1
fi

base_dir=$(dirname $0)/..

MINTDS_JAR=$base_dir/target/mintDS-0.1.0-SNAPSHOT-all.jar
CLASSPATH=$MINTDS_JAR

# Which java to use
if [ -z "$JAVA_HOME" ]; then
  JAVA="java"
else
  JAVA="$JAVA_HOME/bin/java"
fi

# Memory options
if [ -z "$MINTDS_HEAP_OPTS" ]; then
  MINTDS_HEAP_OPTS="-Xms512m -Xmx2048m"
fi

# JVM performance options
if [ -z "$MINTDS_JVM_PERFORMANCE_OPTS" ]; then
  MINTDS_JVM_PERFORMANCE_OPTS="-server -verbose:gc -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:InitiatingHeapOccupancyPercent=35 -XX:+DisableExplicitGC -Djava.awt.headless=true"
fi

#echo "$@"

exec $JAVA $MINTDS_HEAP_OPTS $MINTDS_JVM_PERFORMANCE_OPTS -cp $CLASSPATH "$@"
