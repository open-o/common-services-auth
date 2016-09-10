#!/bin/bash
SCRIPT_DIR=`dirname "$0"`
export APP_ROOT=`cd "$SCRIPT_DIR"/../; pwd`

if [ -z "$JAVA_HOME" ]
then
    echo "There is no JAVA_HOME"
    exit 1
fi

if [ -z "$CATALINA_HOME" ]
then
    echo "There is no CATALINA_HOME"
    exit 1
fi

if [ -z "$APP_ROOT" ]
then
    echo "There is no APP_ROOT"
    exit 1
fi

export CATALINA_BASE=$APP_ROOT

LOG_DIR=$CATALINA_BASE/logs
if [ ! -d "$LOG_DIR" ]; then
  mkdir $LOG_DIR
fi
$CATALINA_HOME/bin/catalina.sh start


