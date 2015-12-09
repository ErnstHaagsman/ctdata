CtData Contributing Guidelines
==============================

To prevent bugs from being introduced, please make sure to follow the following guidelines.

Git Flow
--------

Never, ever, push to master. Always use a pull request. Anyone caught directly pushing to master will provide the rest
of the team with cookies.

Building
--------

All separate deployable packages have their own module. Some things are set up for all projects in the root build.gradle
For example, the Java version is set to 1.7 for all modules. Always build the project from the root directory, specify a
build target to only build a single module:

    gradle webapp:build

Declare all dependencies in your module's build.gradle.

Deployment
----------

Use the docker-compose configuration available from the [ctdata-docker](https://github.com/ErnstHaagsman/ctdata-docker)
repository.

Package Naming
--------------

Please ensure all packages are named correctly:

    net.ctdata.[module-name].etc.etc.etc.

JavaDoc
-------

All code in the 'common' package should be fully documented. In other packages it is not required, but recommended.
Please write enough comments to make sure you'll understand your own code in a couple of weeks.

Code Style
----------

IntelliJ Java defaults.

Exceptions:

-   'Ensure line feed on save' should be set to true. This prevents merge conflicts at the end of the file.

RabbitMQ
--------

All messages on RabbitMQ should have a body which is an object from the net.ctdata.common.Messages package encoded using
the Jackson 2 JSON library (the same version as the one which comes with Spring). 

As Joda DateTime objects are used, please make sure to use Jackson's JodaModule. See the common package's unit tests.
(AssertSerializes class).
