package tf.bug.dirs

import java.nio.file.Path

private[dirs] trait Windows {
  type KnownFolderId

  def folderIdDesktop: KnownFolderId
  def folderIdDocuments: KnownFolderId
  def folderIdDownloads: KnownFolderId
  def folderIdLocalAppData: KnownFolderId
  def folderIdMusic: KnownFolderId
  def folderIdPictures: KnownFolderId
  def folderIdProfile: KnownFolderId
  def folderIdPublic: KnownFolderId
  def folderIdRoamingAppData: KnownFolderId
  def folderIdTemplates: KnownFolderId
  def folderIdVideos: KnownFolderId

  def shGetKnownFolderPath(rfid: KnownFolderId): Option[Path]
}
