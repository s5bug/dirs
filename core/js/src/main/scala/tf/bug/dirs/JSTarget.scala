package tf.bug.dirs
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** Necessary facades around the Node.js `os` module for discovering dirs
  */
object os {
  @js.native
  @JSImport("os", "homedir")
  def homedir(): String = js.native

  @js.native
  @JSImport("process", "platform")
  def platform(): String = js.native

}
object JSTarget extends Target {
  def homedir: Option[Path] = Some(Path.get(os.homedir()))
  def platform: Platform = {
    val osName = jsPlatform()
    osName match {
      case "win32"  => Platform.Windows
      case "darwin" => Platform.Mac
      case _        => Platform.Unknown(osName)
    }
  }
}
