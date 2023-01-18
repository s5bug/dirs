package tf.bug.dirs

import java.nio.file.Path

trait Target {
  def homeDir: Option[Path]
  def platform: Platform
}
