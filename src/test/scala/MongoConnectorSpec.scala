

import org.specs2.mutable.Specification
import reactivemongo.ReactiveMongoHelper
import reactivemongo.api.MongoConnectionOptions

/**
  *
  */
class MongoConnectorSpec extends Specification {

  "ReactiveMongoHelper" should {
    "create a Mongo connection with the given options" in {

      val helper = ReactiveMongoHelper("mongo-server", Seq("mongo1"), Seq(), None, MongoConnectionOptions(connectTimeoutMS = 1000))

      helper.connection.options.connectTimeoutMS shouldEqual 1000
    }
  }
}
