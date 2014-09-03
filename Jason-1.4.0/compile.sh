#!/bin/bash
#export JAVA_HOME=/usr/lib/jvm/java-1.7.0-openjdk-i386/
cd jason-svn
ant clean
ant jar
ant plugin
ant apidoc
bash bin/jason.sh
