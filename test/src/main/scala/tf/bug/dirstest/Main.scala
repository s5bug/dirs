package tf.bug.dirstest

import tf.bug.dirs.Dirs

object Main {

  def main(args: Array[String]): Unit = {
    List(
      "Audio" -> Dirs.audio,
      "Cache" -> Dirs.cache,
      "Config" -> Dirs.config,
      "Data" -> Dirs.data,
      "Data Local" -> Dirs.dataLocal,
      "Desktop" -> Dirs.desktop,
      "Document" -> Dirs.document,
      "Download" -> Dirs.download,
      "Executable" -> Dirs.executable,
      "Font" -> Dirs.font,
      "Home" -> Dirs.home,
      "Picture" -> Dirs.picture,
      "Preference" -> Dirs.preference,
      "Public" -> Dirs.public,
      "Runtime" -> Dirs.runtime,
      "State" -> Dirs.state,
      "Template" -> Dirs.template,
      "Video" -> Dirs.video,
    ).foreach {
      case (name, path) => println(s"$name: $path")
    }
  }

}
