package controllers

import java.util.Calendar

import models.Tables._
import play.api.data.Form
import java.sql.{Date => SDate, Timestamp}

import play.api.mvc._
import play.api.data.Forms._
import play.api.i18n.{MessagesApi, I18nSupport}
import play.api.db.slick._
import javax.inject.Inject

import slick.driver.JdbcProfile

import models.Tables._

/**
  * Created by shutaro-yoshizumi on 2016/05/05.
  */
class AuthController @Inject()(val dbConfigProvider: DatabaseConfigProvider,
                               val messagesApi: MessagesApi) extends Controller with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {

  import AuthController._

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      // エラーの場合。ログイン画面に戻る。
      error => {
        BadRequest(views.html.login(error))
      },
      // OKの場合。メッセージ一覧表示。
      form => Redirect(routes.MessageController.list)
    )
  }

  def logout = TODO

  def matchUserPass(loginForm: LoginForm) = {
    Users.filter(_.name == loginForm.userName).filter(_.password == loginForm.password).exists
  }

}

object AuthController {

  // フォームの値を格納するケースクラス
  case class LoginForm(userName: String, password: String)

  // formから送信されたデータ ⇔ ケースクラスの変換を行う
  val loginForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginForm.apply)(LoginForm.unapply)
    verifying("Invalid UserName or Password", result => result match{
      case _ => true
    })
  )
}
