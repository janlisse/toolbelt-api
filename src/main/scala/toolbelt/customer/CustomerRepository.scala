package toolbelt.customer

import zio.*

trait CustomerRepository:
  def create(user: Customer): Task[Long]
  def get(id: Long): Task[Option[Customer]]
  def list: Task[Vector[Customer]]
object CustomerRepository:
  def create(user: Customer): ZIO[CustomerRepository, Throwable, Long] =
    ZIO.serviceWithZIO[CustomerRepository](_.create(user))

  def get(id: Long): ZIO[CustomerRepository, Throwable, Option[Customer]] =
    ZIO.serviceWithZIO[CustomerRepository](_.get(id))

  def list: ZIO[CustomerRepository, Throwable, Vector[Customer]] =
    ZIO.serviceWithZIO[CustomerRepository](_.list)
