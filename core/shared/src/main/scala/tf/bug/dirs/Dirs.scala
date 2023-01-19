package tf.bug.dirs

import java.nio.file.Path

object Dirs {

  sealed trait Platform
  object Platform {
    case object Windows extends Platform
    case object Mac extends Platform
    case class Unknown(osName: String) extends Platform
  }

  lazy val windows: Windows = WindowsPlatform
  lazy val mac: Mac = MacPlatform

  lazy val platform: Platform = Target.platform

  def audio: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdMusic)
    case Platform.Mac => this.mac.audio
    case Platform.Unknown(osName) => ???
  }

  def cache: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdLocalAppData)
    case Platform.Mac => this.mac.cache
    case Platform.Unknown(osName) => ???
  }

  def config: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdRoamingAppData)
    case Platform.Mac => this.mac.config
    case Platform.Unknown(osName) => ???
  }

  def data: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdRoamingAppData)
    case Platform.Mac => this.mac.data
    case Platform.Unknown(osName) => ???
  }

  def dataLocal: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdLocalAppData)
    case Platform.Mac => this.mac.dataLocal
    case Platform.Unknown(osName) => ???
  }

  def desktop: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdDesktop)
    case Platform.Mac => this.mac.desktop
    case Platform.Unknown(osName) => ???
  }

  def document: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdDocuments)
    case Platform.Mac => this.mac.document
    case Platform.Unknown(osName) => ???
  }

  def download: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdDownloads)
    case Platform.Mac => this.mac.download
    case Platform.Unknown(osName) => ???
  }

  def executable: Option[Path] = platform match {
    case Platform.Windows => None
    case Platform.Mac => None
    case Platform.Unknown(osName) => ???
  }

  def font: Option[Path] = platform match {
    case Platform.Windows => None
    case Platform.Mac => this.mac.font
    case Platform.Unknown(osName) => ???
  }

  def home: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdProfile)
    case Platform.Mac => this.mac.home
    case Platform.Unknown(osName) => ???
  }

  def picture: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdPictures)
    case Platform.Mac => this.mac.picture
    case Platform.Unknown(osName) => ???
  }

  def preference: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdRoamingAppData)
    case Platform.Mac => this.mac.preference
    case Platform.Unknown(osName) => ???
  }

  def public: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdPublic)
    case Platform.Mac => this.mac.public
    case Platform.Unknown(osName) => ???
  }

  def runtime: Option[Path] = platform match {
    case Platform.Windows => None
    case Platform.Mac => None
    case Platform.Unknown(osName) => ???
  }

  def state: Option[Path] = platform match {
    case Platform.Windows => None
    case Platform.Mac => None
    case Platform.Unknown(osName) => ???
  }

  def template: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdTemplates)
    case Platform.Mac => None
    case Platform.Unknown(osName) => ???
  }

  def video: Option[Path] = platform match {
    case Platform.Windows => this.windows.shGetKnownFolderPath(this.windows.folderIdVideos)
    case Platform.Mac => this.mac.video
    case Platform.Unknown(osName) => ???
  }

}
