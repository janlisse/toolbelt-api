package toolbelt.customer

import zio.json.*

case class Customer(
    id: Long,
    name: String,
    shortName: String,
    street: String,
    streetNumber: String,
    zipCode: String,
    city: String,
  )

object Customer:
  given JsonEncoder[Customer] =
    DeriveJsonEncoder.gen[Customer]
  given JsonDecoder[Customer] =
    DeriveJsonDecoder.gen[Customer]
