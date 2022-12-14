package tf.bug.dirs

import java.nio.file.Path

object Dirs {

  sealed trait Platform
  object Platform {
    case object Windows extends Platform
    case class Unknown(osName: String) extends Platform
  }

  lazy val windows: Windows = WindowsPlatform

  lazy val platform: Platform = {
    val osName = System.getProperty("os.name")
    if(osName.contains("Windows")) {
      Platform.Windows
    } else {
      Platform.Unknown(osName)
    }
  }

  def audio: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdMusic)
    case Platform.Unknown(osName) => ???
  }

  def cache: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdLocalAppData)
    case Platform.Unknown(osName) => ???
  }

  def config: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdRoamingAppData)
    case Platform.Unknown(osName) => ???
  }

  def data: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdRoamingAppData)
    case Platform.Unknown(osName) => ???
  }

  def dataLocal: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdLocalAppData)
    case Platform.Unknown(osName) => ???
  }

  def desktop: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdDesktop)
    case Platform.Unknown(osName) => ???
  }

  def document: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdDocuments)
    case Platform.Unknown(osName) => ???
  }

  def download: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdDownloads)
    case Platform.Unknown(osName) => ???
  }

  def executable: Option[Path] = platform match {
    case Platform.Windows => None
    case Platform.Unknown(osName) => ???
  }

  def font: Option[Path] = platform match {
    case Platform.Windows => None
    case Platform.Unknown(osName) => ???
  }

  def home: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdProfile)
    case Platform.Unknown(osName) => ???
  }

  def picture: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdPictures)
    case Platform.Unknown(osName) => ???
  }

  def preference: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdRoamingAppData)
    case Platform.Unknown(osName) => ???
  }

  def public: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdPublic)
    case Platform.Unknown(osName) => ???
  }

  def runtime: Option[Path] = platform match {
    case Platform.Windows => None
    case Platform.Unknown(osName) => ???
  }

  def state: Option[Path] = platform match {
    case Platform.Windows => None
    case Platform.Unknown(osName) => ???
  }

  def template: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdTemplates)
    case Platform.Unknown(osName) => ???
  }

  def video: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdVideos)
    case Platform.Unknown(osName) => ???
  }

}
