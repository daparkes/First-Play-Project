package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index: Action[AnyContent] = Action {
    Ok(views.html.index("Hello World!"))
  }

  def help: Action[AnyContent] = Action {
    Redirect("https://www.google.com/")
  }

  def infinite: Action[AnyContent] = Action {
    Redirect("/infinite")
  }
  
  def todo: Action[AnyContent] = TODO

  def hello(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
    implicit val MyCustomCharset: Codec = Codec.javaSupported(
      "iso-8859-1")
    Ok(request.session.get("connected").getOrElse("User is not logged in"))

  }

  def helloHTML:Action[AnyContent] = Action {
    Ok(<h1>Hello World!</h1>).as(HTML)
  }
  def helloTextHTML: Action[AnyContent] = Action {
    Ok(<h1>Hello World!</h1>).as("text/html")
  }

  def cookies:Action[AnyContent] = Action {
    Ok("Hello World!").withCookies(
      Cookie("colour", "blue")
    )
    implicit request: Request[AnyContent] =>
      request.cookies.get("colour") match {
        case Some(cookie) => Ok(s"Your cookie value is: ${cookie.value}")
        case _ => Ok("Cookie not found")
      }
  }

  def read() = Action { implicit request: Request[AnyContent] =>
    Ok(request.flash.get("success").getOrElse("Something went wrong while redirecting"))
  }

  def write() = Action {
    Redirect("/read").flashing("success" -> "You have been successfully redirected.")
  }
}
