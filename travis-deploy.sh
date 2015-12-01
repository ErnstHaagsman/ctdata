#!/bin/bash

# if [[ $TRAVIS_BRANCH == 'master' ]]

    curl -sL https://raw.githubusercontent.com/travis-ci/artifacts/master/install | bash

    mkdir jars
    mv datanode/build/libs/*.jar ./jars/
    mv orchestrator/build/libs/*.jar ./jars/
    mv raspnodesim/build/libs/*.jar ./jars/
    mv sensorgateway/build/libs/*.jar ./jars/
    mv webapp/build/libs/*.jar ./jars/

    zip jars.zip ./jars/*.jar

    export ARTIFACTS_DEBUG=1


    artifacts upload \
        --s3-region us-west-1 \
        --target-paths build \
        --permissions public-read \
        jars.zip

# fi
