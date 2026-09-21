#!/bin/sh

APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
elif command -v java >/dev/null 2>&1; then
    JAVACMD="java"
else
    echo "ERROR: JAVA_HOME is not set and no 'java' command could be found." >&2
    exit 1
fi

exec "$JAVACMD" $DEFAULT_JVM_OPTS -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
