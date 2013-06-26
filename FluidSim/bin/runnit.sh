#!/bin/sh
# $Id: runnit.sh,v 1.2 2007/08/12 08:36:01 marco Exp $

# Remove "#" from following two lines, if you'd like to use j2sdk.
# set JAVA_HOME=C:\j2sdk1.4.2_08
# set PATH=%JAVA_HOME%\bin

# run
cd ..
java -jar "lib/fluid-sim.jar"
cd bin
