**[DEPRECATED] Please be aware that this library is no longer maintained. Moving forwared, please use [simple-reactivemongo](https://github.com/hmrc/simple-reactivemongo) or [Play-ReactiveMongo](https://github.com/hmrc/play-reactivemongo) version 6.0.0 or higher were appropriate. If you wish to use an alternative to this library, please use the officially maintained upstream version that is now used in the platform libraries: [ReactiveMongo-Play-Json](https://github.com/reactivemongo/reactivemongo-play-json)**

# reactivemongo-json

[![Build Status](https://travis-ci.org/hmrc/reactivemongo-json.svg)](https://travis-ci.org/hmrc/reactivemongo-json) [ ![Download](https://api.bintray.com/packages/hmrc/releases/reactivemongo-json/images/download.svg) ](https://bintray.com/hmrc/releases/reactivemongo-json/_latestVersion)

This library is an extraction of the classes from [Play-ReactiveMongo](https://github.com/ReactiveMongo/Play-ReactiveMongo) allowing the versions of [play-json](https://www.playframework.com/documentation/2.3.x/ScalaJson) and [ReactiveMongo](https://github.com/ReactiveMongo/ReactiveMongo) to be set by the consuming library.

The original classes can be found here:

* [bsonhandlers](https://github.com/ReactiveMongo/Play-ReactiveMongo/blob/master/src/main/scala/play/modules/reactivemongo/bsonhandlers.scala)
* [json](https://github.com/ReactiveMongo/Play-ReactiveMongo/blob/master/src/main/scala/play/modules/reactivemongo/json.scala)
* [jsoncollection](https://github.com/ReactiveMongo/Play-ReactiveMongo/blob/master/src/main/scala/play/modules/reactivemongo/jsoncollection.scala)

### Installing

Include the following dependency in your SBT build

```scala
resolvers += Resolver.bintrayRepo("hmrc", "releases")

libraryDependencies += "uk.gov.hmrc" %% "reactivemongo-json" % "[INSERT_VERSION]"
```

*For Java 7 and Play 2.3.x use versions <=2.2.0*

## License ##
 
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
