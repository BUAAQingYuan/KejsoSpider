#! /bin/bash

host=
config=
jdbc=
cmd=

if [ $# -ne 4 ]
then
	echo " Usage: ./buildspiderchain.sh host config jdbc command ."
	exit 1
fi

host=$1
config=$2
jdbc=$3
cmd=$4

java -Djava.rmi.server.hostname=$host -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8999 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -jar BuildSpiderChain.jar  $config $jdbc $cmd


