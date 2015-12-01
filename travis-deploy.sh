#!/bin/bash

# if [[ $TRAVIS_BRANCH == 'master' ]]

    curl -sL https://raw.githubusercontent.com/travis-ci/artifacts/master/install | bash

    mkdir jars
    mv datanode/build/libs/*.jar ./jars/
    mv orchestrator/build/libs/*.jar ./jars/
    mv raspnodesim/build/libs/*.jar ./jars/
    mv sensorgateway/build/libs/*.jar ./jars/
    mv webapp/build/libs/*.jar ./jars/

    artifacts upload jars/

# fi
