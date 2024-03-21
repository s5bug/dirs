/*
 * Copyright 2024 Aly Cerruti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tf.bug.dirs

import java.nio.file.Path

object Dirs {

  private sealed trait Platform
  private object Platform {
    case object Windows extends Platform
    case class Unknown(osName: String) extends Platform
  }

  private lazy val windows: Windows = WindowsPlatform

  private lazy val platform: Platform = {
    val osName = System.getProperty("os.name")
    if (osName.contains("Windows")) {
      Platform.Windows
    } else {
      Platform.Unknown(osName)
    }
  }

  def audio: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdMusic)
    case Platform.Unknown(osName) => ???
  }

  def cache: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdLocalAppData)
    case Platform.Unknown(osName) => ???
  }

  def config: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdRoamingAppData)
    case Platform.Unknown(osName) => ???
  }

  def configLocal: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdLocalAppData)
    case Platform.Unknown(osName) => ???
  }

  def data: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdRoamingAppData)
    case Platform.Unknown(osName) => ???
  }

  def dataLocal: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdLocalAppData)
    case Platform.Unknown(osName) => ???
  }

  def desktop: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdDesktop)
    case Platform.Unknown(osName) => ???
  }

  def document: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdDocuments)
    case Platform.Unknown(osName) => ???
  }

  def download: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdDownloads)
    case Platform.Unknown(osName) => ???
  }

  def executable: Option[Path] = platform match {
    case Platform.Windows         => None
    case Platform.Unknown(osName) => ???
  }

  def font: Option[Path] = platform match {
    case Platform.Windows         => None
    case Platform.Unknown(osName) => ???
  }

  def home: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdProfile)
    case Platform.Unknown(osName) => ???
  }

  def picture: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdPictures)
    case Platform.Unknown(osName) => ???
  }

  def preference: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdRoamingAppData)
    case Platform.Unknown(osName) => ???
  }

  def public: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdPublic)
    case Platform.Unknown(osName) => ???
  }

  def runtime: Option[Path] = platform match {
    case Platform.Windows         => None
    case Platform.Unknown(osName) => ???
  }

  def state: Option[Path] = platform match {
    case Platform.Windows         => None
    case Platform.Unknown(osName) => ???
  }

  def template: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdTemplates)
    case Platform.Unknown(osName) => ???
  }

  def video: Option[Path] = platform match {
    case Platform.Windows =>
      this.windows.shGetKnownFolderPath(this.windows.folderIdVideos)
    case Platform.Unknown(osName) => ???
  }

}
