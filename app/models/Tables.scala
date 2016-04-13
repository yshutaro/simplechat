package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.H2Driver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = Messages.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Messages
   *  @param messageId Database column MESSAGE_ID SqlType(INTEGER), AutoInc, PrimaryKey
   *  @param messageFromName Database column MESSAGE_FROM_NAME SqlType(VARCHAR)
   *  @param message Database column MESSAGE SqlType(VARCHAR)
   *  @param updateDate Database column UPDATE_DATE SqlType(TIMESTAMP) */
  case class MessagesRow(messageId: Int, messageFromName: String, message: String, updateDate: java.sql.Timestamp)
  /** GetResult implicit for fetching MessagesRow objects using plain SQL queries */
  implicit def GetResultMessagesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[MessagesRow] = GR{
    prs => import prs._
    MessagesRow.tupled((<<[Int], <<[String], <<[String], <<[java.sql.Timestamp]))
  }
  /** Table description of table MESSAGES. Objects of this class serve as prototypes for rows in queries. */
  class Messages(_tableTag: Tag) extends Table[MessagesRow](_tableTag, "MESSAGES") {
    def * = (messageId, messageFromName, message, updateDate) <> (MessagesRow.tupled, MessagesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(messageId), Rep.Some(messageFromName), Rep.Some(message), Rep.Some(updateDate)).shaped.<>({r=>import r._; _1.map(_=> MessagesRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column MESSAGE_ID SqlType(INTEGER), AutoInc, PrimaryKey */
    val messageId: Rep[Int] = column[Int]("MESSAGE_ID", O.AutoInc, O.PrimaryKey)
    /** Database column MESSAGE_FROM_NAME SqlType(VARCHAR) */
    val messageFromName: Rep[String] = column[String]("MESSAGE_FROM_NAME")
    /** Database column MESSAGE SqlType(VARCHAR) */
    val message: Rep[String] = column[String]("MESSAGE")
    /** Database column UPDATE_DATE SqlType(TIMESTAMP) */
    val updateDate: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("UPDATE_DATE")
  }
  /** Collection-like TableQuery object for table Messages */
  lazy val Messages = new TableQuery(tag => new Messages(tag))
}
