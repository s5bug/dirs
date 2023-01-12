package tf.bug.dirs

import java.nio.file.{Path, Paths}

object MacPlatform extends Mac {
  def home: Option[Path] = Some(Paths.get(os.homedir()))
  def cache: Option[Path] = home.map(h => Paths.get(h + "/Library/Caches"))
  def config: Option[Path] =
    home.map(h => Paths.get(h + "/Library/Application Support"))
  def data: Option[Path] =
    home.map(h => Paths.get(h + "/Library/Application Support"))
  def dataLocal: Option[Path] =
    home.map(h => Paths.get(h + "/Library/Preferences"))
  def preference: Option[Path] =
    home.map(h => Paths.get(h + "/Library/Preferences"))
  def audio: Option[Path] = home.map(h => Paths.get(h + "/Music"))
  def desktop: Option[Path] = home.map(h => Paths.get(h + "/Desktop"))
  def document: Option[Path] = home.map(h => Paths.get(h + "/Documents"))
  def download: Option[Path] = home.map(h => Paths.get(h + "/Downloads"))
  def font: Option[Path] = home.map(h => Paths.get(h + "/Library/)Fonts"))
  def picture: Option[Path] = home.map(h => Paths.get(h + "/Pictures"))
  def public: Option[Path] = home.map(h => Paths.get(h + "/Public"))
  def video: Option[Path] = home.map(h => Paths.get(h + "/Movies"))
}
