#! /bin/csh
#
set LIB_SWT_PATH="/usr/local/eclipse/plugins"
set LIB_SWT="${LIB_SWT_PATH}/org.eclipse.swt_3.2.0.v3232o.jar:${LIB_SWT_PATH}/org.eclipse.swt.gtk.linux.x86_3.2.0.v3232m.jar"
set LIB_JARG="/home/dutech/Windaube/Java/lib/jargs.jar"

set CLASS_PATH="${LIB_SWT}:${LIB_JARG}:./bin"

java -classpath ${CLASS_PATH} AutoZebre $*



