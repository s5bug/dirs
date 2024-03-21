/*
 * Copyright 2023 Aly
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

import java.nio.charset.StandardCharsets
import java.nio.file.{Path, Paths}
import java.util.UUID
import scala.scalanative.unsafe.Nat._8
import scala.scalanative.unsafe.*
import scala.scalanative.unsigned.UnsignedRichLong
import scala.scalanative.windows.DWord

private[dirs] object WindowsPlatform extends Windows {

  private final implicit val zone: Zone = Zone.open()

  private final type Guid = CStruct4[
    CUnsignedInt,
    CUnsignedShort,
    CUnsignedShort,
    CArray[CUnsignedChar, _8]
  ]

  @link("Shell32")
  @extern
  private object shell32 {
    @name("SHGetKnownFolderPath")
    def shGetKnownFolderPath(
      rfid: Ptr[Guid],
      dwFlags: DWord,
      hToken: Ptr[Byte],
      ppszPath: Ptr[Ptr[CWideChar]]
    ): CInt = extern
  }

  @link("Ole32")
  @extern
  private object ole32 {
    @name("CoTaskMemFree")
    def coTaskMemFree(
      address: Ptr[Byte]
    ): Unit = extern
  }

  private final def allocateGuid(guid: String): Ptr[Guid] = {
    val ptr: Ptr[Guid] = alloc[Guid]()
    val uuid = UUID.fromString(guid)
    !ptr.at1 = ((uuid.getMostSignificantBits & 0xFFFFFFFF00000000L) >>> 32).toUInt
    !ptr.at2 = ((uuid.getMostSignificantBits & 0x00000000FFFF0000L) >>> 16).toUShort
    !ptr.at3 = ((uuid.getMostSignificantBits & 0x000000000000FFFFL) >>> 0).toUShort
    (0L to 7L).foreach { idx =>
      val shift = (7L - idx) * 8L
      val mask = 0xFFL << shift
      !ptr.at4.at(idx.toInt) = ((uuid.getLeastSignificantBits & mask) >>> shift).toUByte
    }
    ptr
  }

  override final type KnownFolderId = Ptr[Guid]

  override final lazy val folderIdDesktop: Ptr[Guid] =
    allocateGuid("B4BFCC3A-DB2C-424C-B029-7FE99A87C641")

  override final lazy val folderIdDocuments: Ptr[Guid] =
    allocateGuid("FDD39AD0-238F-46AF-ADB4-6C85480369C7")

  override final lazy val folderIdDownloads: Ptr[Guid] =
    allocateGuid("374DE290-123F-4565-9164-39C4925E467B")

  override final lazy val folderIdLocalAppData: Ptr[Guid] =
    allocateGuid("F1B32785-6FBA-4FCF-9D55-7B8E7F157091")

  override final lazy val folderIdMusic: Ptr[Guid] =
    allocateGuid("4BD8D571-6D19-48D3-BE97-422220080E43")

  override final lazy val folderIdPictures: Ptr[Guid] =
    allocateGuid("33E28130-4E1E-4676-835A-98395C3BC3BB")

  override final lazy val folderIdProfile: Ptr[Guid] =
    allocateGuid("5E6C858F-0E22-4760-9AFE-EA3317B67173")

  override final lazy val folderIdPublic: Ptr[Guid] =
    allocateGuid("DFDF76A2-C82A-4D63-906A-5644AC457385")

  override final lazy val folderIdRoamingAppData: Ptr[Guid] =
    allocateGuid("3EB685DB-65F9-4CF6-A03A-E3EF65729F3D")

  override final lazy val folderIdTemplates: Ptr[Guid] =
    allocateGuid("A63293E8-664E-48DB-A079-DF759E0509F7")

  override final lazy val folderIdVideos: Ptr[Guid] =
    allocateGuid("18989B1D-99B5-455B-841C-AB7C74E4DDFC")

  override def shGetKnownFolderPath(rfid: Ptr[Guid]): Option[Path] = {
    val strBox: Ptr[Ptr[CWideChar]] = stackalloc[Ptr[CWideChar]]()
    val ret: Int = shell32.shGetKnownFolderPath(rfid, 0.toUInt, null, strBox)
    val strAddr: Ptr[CWideChar] = !strBox
    val result = if(ret == 0) {
      val str = fromCWideString(strAddr, StandardCharsets.UTF_16LE)
      Some(Paths.get(str))
    } else {
      None
    }
    ole32.coTaskMemFree(strAddr.asInstanceOf[Ptr[Byte]])
    result
  }

}
