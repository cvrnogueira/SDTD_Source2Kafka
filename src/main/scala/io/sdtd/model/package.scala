package io.sdtd

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.{Date, Locale}

import io.circe.{Decoder, Encoder, HCursor, Json}

package object model {

  implicit val encodeFoo: Encoder[TwitterPayload] = (a: TwitterPayload) => Json.obj(
    ("userId", Json.fromString(a.userId.toString)),
    ("createdAt", Json.fromLong(a.createdAt)),
    ("tweet", Json.fromString(a.tweet)),
    ("location", Json.fromString(if (a.location.get == null) "Empty" else a.location.get.toString))
  )

  case class TwitterPayload(
    userId: Long,
    createdAt: Long,
    tweet: String,
    location: Option[String] = None
  )
}
