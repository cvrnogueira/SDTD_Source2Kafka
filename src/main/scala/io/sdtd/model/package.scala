package io.sdtd

import io.circe.{Encoder, Json}

package object model {

  implicit val encodeTwitterPayload: Encoder[TwitterPayload] = (a: TwitterPayload) => Json.obj(
    ("userId", Json.fromString(a.userId.toString)),
    ("createdAt", Json.fromLong(a.createdAt)),
    ("tweet", Json.fromString(a.tweet)),
    ("location", Json.fromString(if (a.location.isEmpty) "Empty" else a.location.get))
  )

  case class TwitterPayload(
    userId: Long,
    createdAt: Long,
    tweet: String,
    location: Option[String] = None
  )
}
