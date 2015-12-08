#!/bin/bash


curl -sL https://raw.githubusercontent.com/travis-ci/artifacts/master/install | bash

mkdir jars
mv datanode/build/libs/*.jar ./jars/
mv orchestrator/build/libs/*.jar ./jars/
mv raspnodesim/build/libs/*.jar ./jars/
mv sensorgateway/build/libs/*.jar ./jars/
mv webapp/build/libs/*.jar ./jars/

zip ${TRAVIS_BRANCH}.zip ./jars/*.jar

artifacts upload \
    --s3-region us-west-1 \
    --target-paths build \
    --permissions public-read \
    ${TRAVIS_BRANCH}.zip

