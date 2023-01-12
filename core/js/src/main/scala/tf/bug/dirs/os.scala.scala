package tf.bug.dirs

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/**
  * Necessary facades around the Node.js `os` module for discovering dirs
  */
object os {
  @js.native
  @JSImport("os", "homedir")
  def homedir(): String = js.native
}
