package tf.bug.dirs

object MacPlatform extends Mac {
  def home: Option[Path] = System.getProperty("user.home")
  def cache: Option[Path] = home.map(h => h + "/Library/Caches")
  def config: Option[Path] = home.map(h => h + "/Library/Application Support")
  def data: Option[Path] = home.map(h => h + "/Library/Application Support")
  def dataLocal: Option[Path] = home.map(h => h + "/Library/Preferences" )
  def preference: Option[Path] = home.map(h => h + "/Library/Preferences")
  def audio: Option[Path] = home.map(h => h + "/Music")
  def desktop: Option[Path] = home.map(h => h + "/Desktop")
  def document: Option[Path] = home.map(h => h + "/Documents")
  def download: Option[Path] = home.map(h => h + "/Downloads")
  def font: Option[Path] = home.map(h => h + "/Library/Fonts")
  def picture: Option[Path] = home.map(h => h + "/Pictures")
  def public: Option[Path] = home.map(h => h + "/Public")
  def video: Option[Path] = home.map(h => h + "/Movies")
}
