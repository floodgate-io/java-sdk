# Deployment Instructions

First the prerequisites

## Prerequisites

Modern JDK installed and on PATH, ideally JAVA_HOME set
```bash
$ java --version
# Should output a version number that is at least 11

$ echo $JAVA_HOME
# some path
```

Maven should be installed and on PATH
```bash
$ mvn --version
# expecting >= 3.6.3
```

GPG is installed and on PATH (to sign packages for release to maven central)
```bash
$ gpg2 --version
# Could be just gpg on your system

```

## Build etc

Navigate to project root (contains pom.xml and src/)

You don't need to run these all in order, they will call their prerequisite tasks.
e.g package will run compile & test first

```bash
$ mvn clean # Cleans ./target dir
$ mvn compile # Builds project
$ mvn test # Runs tests
$ mvn package # Creates deployment artifacts in ./target
$ mvn install # Install packages to local maven cache e.g. ~/.m2/
```

## Deployment to ossrh

We don't push directly to maven central, instead we push to ossrh and releases are synced from there to central.

To deploy to ossrh you will need to do the following

1. Sign up to the ossrh Jira https://issues.sonatype.org
1. Be granted privileges to deploy io.floodgate.sdk, raise issue or append to https://issues.sonatype.org/browse/OSSRH-58183
1. create/update maven settings.xml ~/.m2/settings.xml (C:\\Users\\{name}\\.m2\\\settings.xml)
1. add ossrh jira credentials

```xml
<settings>
    <servers>
        <server>
            <id>ossrh</id>
            <username>your_username_here</username>
            <password>your_password_here</password>
        </server>
    </servers>
</settings>
```

Additionally, packages require signing with a gpg key to verify Author.

```bash
$ gpg --list-secret-keys
# Should have entry for Floodgate <support@floodgate.io>
```

To deploy a snapshot to ossrh (version string ends in -SNAPSHOT in pom.xml)

```bash
$ mvn clean deploy
``` 

To promote release remove snapshot from version string
```bash
$ mvn versions:set -DnewVersion=x.x.x
$ mvn clean deploy
```

Verify everything OK with release using the staging repo before promoting to release repo
If anything is wrong with the release you can drop from staging repo with 
```bash
$ mvn nexus-staging:drop
```

If everything is OK you can promote release
```bash
$ mvn nexus-staging:release
```

All done, further details can be found at the following

1. https://central.sonatype.org/pages/apache-maven.html
1. https://central.sonatype.org/pages/ossrh-guide.html#deployment
1. https://maven.apache.org/repository/guide-central-repository-upload.html