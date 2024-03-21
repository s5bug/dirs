/*
 * Copyright 2023-2024 Aly Cerruti
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
