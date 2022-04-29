#!/bin/bash

JAVA=/usr/lib/jvm/java-11-openjdk-amd64/bin/java

dir=$1
commit=$2
cmd=$3

function tellName() {
  return "Refactor First"
  return 0
}

function tellApplicable() {
  echo "true"
  return 0
}

function tellVersion() {
  echo "4"
  return 0
}

function run() {
  RESULTS='./target/site/refactor-first-data.json'
  mvn org.hjug.refactorfirst.plugin:refactor-first-maven-plugin:0.3.1-SNAPSHOT:jsonreport >> ./refactor_first_logs

  if [[ -f "$RESULTS" ]]
  then
    echo "{ \"refactor-first\": $(cat $RESULTS) }"
    return 0
  else
    echo "{ \"refactor-first\": { \"errors\": [\"failed to produce results for refactor-first\"] } }"
    # echo "{ \"refactor-first\": { \"errors\": [\"failed to produce results for refactor-first\"], \"logs\": \"$(cat ./refactor_first_logs)\" } }"
    return 1
  fi
}

if [[ "$cmd" = "name" ]] ; then
  tellName
fi

if [[ "$cmd" = "run" ]] ; then
        run
fi
if [[ "$cmd" = "applicable" ]] ; then
        tellApplicable
fi
if [[ "$cmd" = "version" ]] ; then
        tellVersion
fi
