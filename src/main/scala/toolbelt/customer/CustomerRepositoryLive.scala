package toolbelt.customer

import zio.*
import doobie.{Query0, Transactor, Update0}
import doobie.implicits._
import zio.interop.catz._

import java.util.UUID
import javax.sql.DataSource
import doobie.util.ExecutionContexts

case class CustomerRepositoryLive(tnx: Transactor[Task]) extends CustomerRepository:
  override def create(customer: Customer): Task[Long] =
    sql"""INSERT INTO CUSTOMER (name, short_name, street, street_number, zip_code, city) VALUES 
    (${customer.name}, ${customer.shortName}, ${customer.street}, ${customer.streetNumber}, ${customer.zipCode}, ${customer.city})"""
      .update
      .withUniqueGeneratedKeys[Long]("id")
      .transact(tnx)

  override def get(id: Long): Task[Option[Customer]] =
    sql"""SELECT * FROM CUSTOMER WHERE ID = $id """
      .query[Customer]
      .option
      .transact(tnx)

  override def list: Task[Vector[Customer]] =
    sql"""SELECT * FROM CUSTOMER"""
      .query[Customer]
      .to[Vector]
      .transact(tnx)
object CustomerRepositoryLive:
  def layer: ZLayer[DataSource & Scope, Throwable, CustomerRepositoryLive] =
    ZLayer {
      for {
        ec <- ExecutionContexts.fixedThreadPool[Task](4).toScopedZIO
        ds <- ZIO.service[DataSource]
      } yield (
        CustomerRepositoryLive(Transactor.fromDataSource[Task](ds, ec)),
      )
    }
