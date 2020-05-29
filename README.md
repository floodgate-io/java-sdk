# FloodGate SDK for Java

## Overview

This is the Java SDK for [Floodgate](https://floodgate.io), a feature rollout service which provides a centralized management console for managing remote feature flags.

## Compatibility

The FloodGate Java SDK is currently compatible with JDK 11+

## Installing

Install the Floodgate Java SDK

### Maven

```xml
<dependency>
  <groupId>io.floodgate</groupId>
  <artifactId>sdk</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle
```groovy
// TODO: build.gradle example
```

## Usage

Below is a simple example of how you can use the Java SDK to check on the status of a flag.

Add required imports

```java
import io.floodgate.sdk.FloodGateClientFactory;
```

Create a FloodGate Client instance

```java
var client = FloodGateClientFactory.create("YOUR-API-KEY");
```

Retrieve your flag value

```java
var myFeatureFlag = client.getValue("my-feature-flag", false);

if (myFeatureFlag) {
    System.out.println("my-feature-flag enabled");
} else {
    System.out.println("my-feature-flag not yet enabled");
}
```

## Submitting issues

Sometimes everyone has issues! The Floodgate teams tracks all issues submitted to this [issue tracker](https://github.com/floodgate-io/java-sdk/issues). You are encouraged to use the issue tracker to report any bugs or submit your general feedback and feature enhancements. We will do our best to respond as quickly as possible.

## Contributing

We are always looking for talented engineers to join the Floodgate team. If you would like to contribue to our projects feel free to fork this project and when ready issue a PR back for review.

## About Floodgate

Floodgate is a remote feature management system designed to help engineering teams and product teams work independently. Using feature flags managed by Floodgate you will dramatically reduce the risks software companies face when releasing and deploying new features.

With Floodgate you can use fine grained user targeting to test out new features in your production environment with minimal impact and risk to your existing systems and customers. Floodgate provides a simple to use percentage rollout facility to allow you to perform canary releases with just a few clicks.

To learn more about Floodgate, visit us at https://floodgate.io or contact hello@floodgate.io. To get started with feature flags for free at https://app.floodgate.io/signup.

Floodgate has currently developed following SDKs.

* .Net [GitHub](https://github.com/floodgate-io/dotnet-sdk)
* Java [GitHub](https://github.com/floodgate-io/java-sdk)
* JavaScript [GitHub](https://github.com/floodgate-io/javascript-sdk)
* Node [GitHub](https://github.com/floodgate-io/node-sdk)
* PHP [GitHub](https://github.com/floodgate-io/php-sdk)

## Contributing a New SDK

If you would like to contribute to Floodgate's library of SDKs and create an SDK for a new language, feel free to drop us an email at contribute@floodgate.io
