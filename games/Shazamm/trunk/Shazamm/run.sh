#! /bin/csh
#
# run TestShazamm
#
set LIB_SWT_PATH="/usr/local/eclipse/plugins/org.eclipse.swt.gtk_3.0.2/ws/gtk"
set LIB_SWT="${LIB_SWT_PATH}/swt.jar:${LIB_SWT_PATH}/swt-pi.jar"
set LIB_JARG="/home/dutech/Windaube/Java/lib/jargs.jar"

set CLASS_PATH="${LIB_SWT}:${LIB_JARG}:./bin"

java -classpath ${CLASS_PATH} jeu/shazamm/test/TestShazamm $*



