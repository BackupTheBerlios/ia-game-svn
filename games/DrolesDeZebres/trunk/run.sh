#! /bin/csh
#
set LIB_SWT_PATH="/usr/lib/eclipse/plugins"
#set LIB_SWT="${LIB_SWT_PATH}/org.eclipse.swt_3.3.2.v3347.jar:${LIB_SWT_PATH}/org.eclipse.swt.gtk.linux.x86_3.3.2.v3347.jar"
set LIB_SWT="${LIB_SWT_PATH}/org.eclipse.swt_3.2.2.v3236b.jar:${LIB_SWT_PATH}/org.eclipse.swt.gtk.linux.x86_3.2.2.v3236.jar"
set LIB_JARG="/usr/share/java/jargs.jar"

set CLASS_PATH="${LIB_SWT}:${LIB_JARG}:./bin"

java -classpath ${CLASS_PATH} -Djava.library.path=/usr/lib/jni AutoZebre $*



