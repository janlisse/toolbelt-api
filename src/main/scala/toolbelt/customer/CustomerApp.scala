package toolbelt.customer

import zhttp.http.*
import zio.*
import zio.json.*

object CustomerApp:
  def apply(): Http[CustomerRepository, Throwable, Request, Response] =
    Http.collectZIO[Request] {
      case req @ (Method.POST -> !! / "customers") =>
        for
          json <- req.body.asString
          _    <- ZIO.logInfo(json)
          u = json.fromJson[Customer]
          r <- u match
            case Left(e)  =>
              ZIO
                .debug(s"Failed to parse the input: $e")
                .as(Response.text(e).setStatus(Status.BadRequest))
            case Right(u) =>
              CustomerRepository
                .create(u)
                .map(id => Response.text("Ok"))
        yield r

      case Method.GET -> !! / "customers" / id =>
        CustomerRepository.get(id.toLong).map {
          case Some(user) =>
            Response.json(user.toJson)
          case None       =>
            Response.status(Status.NotFound)
        }
      case Method.GET -> !! / "customers"      =>
        CustomerRepository.list.map(response => Response.json(response.toJson))
    }
