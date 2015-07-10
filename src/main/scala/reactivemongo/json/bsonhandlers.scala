/*
 * Copyright 2015 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package reactivemongo.json

import play.api.libs.json._
import reactivemongo.bson._

object `package` {
  implicit object JsObjectDocumentWriter // Identity writer
    extends JSONSerializationPack.Writer[JsObject] {
    def writes(obj: JsObject): JSONSerializationPack.Document = obj
  }
}


import play.api.libs.json.{JsObject, JsValue}
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

/**
 * Implicit BSON Handlers (BSONDocumentReader/BSONDocumentWriter for JsObject)
 */
object ImplicitBSONHandlers extends ImplicitBSONHandlers

trait ImplicitBSONHandlers extends LowerImplicitBSONHandlers {
  implicit object JsObjectWriter extends BSONDocumentWriter[JsObject] {
    def write(obj: JsObject): BSONDocument =
      BSONFormats.BSONDocumentFormat.bson(obj)
  }

  implicit object JsObjectReader extends BSONDocumentReader[JsObject] {
    def read(document: BSONDocument) =
      BSONFormats.BSONDocumentFormat.writes(document).as[JsObject]
  }

  implicit object BSONDocumentWrites extends OWrites[BSONDocument] {
    def writes(bson: BSONDocument): JsObject =
      BSONFormats.BSONDocumentFormat.json(bson)
  }
}

trait LowerImplicitBSONHandlers {
  import reactivemongo.bson.{BSONElement, Producer}

  implicit def jsWriter[A <: JsValue, B <: BSONValue] = new BSONWriter[A, B] {
    def write(js: A): B = BSONFormats.toBSON(js).get.asInstanceOf[B]
  }

  implicit object BSONValueWrites extends Writes[BSONValue] {
    def writes(bson: BSONValue): JsValue = BSONFormats.toJSON(bson)
  }

  implicit def JsFieldBSONElementProducer[T <: JsValue](jsField: (String, T)): Producer[BSONElement] = Producer.nameValue2Producer(jsField)

}
