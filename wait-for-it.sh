   #!/usr/bin/env bash
   # Use this script to test if a given TCP host/port are available
   # Usage: wait-for-it.sh host:port [-t timeout] [-- command args]
   # The script will wait until the host is reachable or the timeout is reached.
   # If a command is provided, it will be executed after the host is reachable.

   set -e

   TIMEOUT=15
   WAITFORIT_CMD=""

   while [[ $# -gt 0 ]]; do
       case "$1" in
           *:* )
               HOSTPORT=("$1")
               shift
               ;;
           -t)
               TIMEOUT="$2"
               shift 2
               ;;
           --)
               WAITFORIT_CMD=("$@")
               break
               ;;
           *)
               echo "Invalid argument: $1"
               exit 1
               ;;
       esac
   done

   HOST="${HOSTPORT[0]}"
   PORT="${HOSTPORT[1]}"

   echo "Waiting for $HOST:$PORT..."

   for ((i=0; i<TIMEOUT; i++)); do
       nc -z "$HOST" "$PORT" && break
       sleep 1
   done

   if [[ "$i" == "$TIMEOUT" ]]; then
       echo "Timeout occurred after waiting $TIMEOUT seconds for $HOST:$PORT"
       exit 1
   fi

   echo "$HOST:$PORT is available"

   if [[ -n "$WAITFORIT_CMD" ]]; then
       exec "${WAITFORIT_CMD[@]}"
   fi