package controllers

import java.util.Calendar
import java.sql.{  Date => SDate }
import java.sql.Timestamp

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.i18n.{MessagesApi, I18nSupport}
import play.api.db.slick._
import slick.driver.JdbcProfile
import models.Tables._
import javax.inject.Inject
import scala.concurrent.Future
import slick.driver.H2Driver.api._

class MessageController @Inject()(val dbConfigProvider: DatabaseConfigProvider,
                               val messagesApi: MessagesApi) extends Controller
  with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  import MessageController._
  /**
    * 一覧表示
    */
  def list = Action.async { implicit rs =>
    db.run(Messages.sortBy(t => t.messageId).result).map { messages =>
      Ok(views.html.message.list(messageForm,messages))
    }
  }

  /**
    * 登録実行
    */
  def create = Action.async { implicit rs =>
    // リクエストの内容をバインド
    messageForm.bindFromRequest.fold(
      // エラーの場合
      error => {
        db.run(Messages.sortBy(t => t.messageId).result).map { messages =>
          BadRequest(views.html.message.list(error,messages))
        }
      },
      // OKの場合
      form  => {
        val now = new Timestamp(Calendar.getInstance().getTimeInMillis())
        // メッセージを登録
        val message = MessagesRow(0, form.messageFromName, form.message,now)
        db.run(Messages += message).map { _ =>
          // 一覧画面へリダイレクト
          Redirect(routes.MessageController.list)
        }
      }
    )
  }

  /**
    * 削除実行
    */
  def remove(id: Long) = TODO

}

object MessageController {
  // フォームの値を格納するケースクラス
  case class MessageForm(messageId: Option[Long], messageFromName: String, message: String)

  // formから送信されたデータ ⇔ ケースクラスの変換を行う
  val messageForm = Form(
    mapping(
      "messageId"        -> optional(longNumber),
      "messageFromName"        -> nonEmptyText(maxLength = 50),
      "message"      -> nonEmptyText(maxLength = 200)
    )(MessageForm.apply)(MessageForm.unapply)
  )
}