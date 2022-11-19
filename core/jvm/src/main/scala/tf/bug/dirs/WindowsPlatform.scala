package tf.bug.dirs

import java.nio.{ByteBuffer, CharBuffer}
import java.nio.charset.StandardCharsets
import java.nio.file.{Path, Paths}
import java.util.UUID
import java.lang.foreign.MemoryLayout.PathElement
import java.lang.foreign._
import scala.util.Using

object WindowsPlatform extends Windows {
  private val linker: Linker = Linker.nativeLinker()
  private val kernel32Symbols: SymbolLookup = SymbolLookup.libraryLookup("kernel32", MemorySession.global())
  private val lstrlena: MemorySegment = kernel32Symbols.lookup("lstrlenW").get()
  private val lstrlenh = linker.downcallHandle(
    lstrlena,
    FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)
  )
  private val shell32Symbols: SymbolLookup = SymbolLookup.libraryLookup("shell32", MemorySession.global())
  private val gkfa: MemorySegment = shell32Symbols.lookup("SHGetKnownFolderPath").get()
  private val gkfh = linker.downcallHandle(
    gkfa,
    FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)
  )

  private val guidLayout: GroupLayout =
    MemoryLayout.structLayout(
      ValueLayout.JAVA_INT.withName("data1"),
      ValueLayout.JAVA_SHORT.withName("data2"),
      ValueLayout.JAVA_SHORT.withName("data3"),
      MemoryLayout.sequenceLayout(8L, ValueLayout.JAVA_BYTE).withName("data4")
    )

  private def toGuid(str: String, session: MemorySession): MemorySegment = {
    val seg = MemorySegment.allocateNative(guidLayout, session)
    val uuid = UUID.fromString(str)
    guidLayout.varHandle(PathElement.groupElement("data1"))
      .set(seg, ((uuid.getMostSignificantBits & 0xFFFFFFFF00000000L) >>> 32).toInt)
    guidLayout.varHandle(PathElement.groupElement("data2"))
      .set(seg, ((uuid.getMostSignificantBits & 0x00000000FFFF0000L) >>> 16).toShort)
    guidLayout.varHandle(PathElement.groupElement("data3"))
      .set(seg, ((uuid.getMostSignificantBits & 0x000000000000FFFFL) >>> 0).toShort)
    (0L to 7L).foreach { idx =>
      val shift = (7L - idx) * 8L
      val mask = 0xFFL << shift
      guidLayout.varHandle(PathElement.groupElement("data4"), PathElement.sequenceElement(idx))
        .set(seg, ((uuid.getLeastSignificantBits & mask) >>> shift).toByte)
    }
    seg
  }

  override type KnownFolderId = MemorySegment

  override def folderIdDesktop: MemorySegment =
    toGuid("B4BFCC3A-DB2C-424C-B029-7FE99A87C641", MemorySession.global())

  override def folderIdDocuments: MemorySegment =
    toGuid("FDD39AD0-238F-46AF-ADB4-6C85480369C7", MemorySession.global())

  override def folderIdDownloads: MemorySegment =
    toGuid("374DE290-123F-4565-9164-39C4925E467B", MemorySession.global())

  override def folderIdLocalAppData: MemorySegment =
    toGuid("F1B32785-6FBA-4FCF-9D55-7B8E7F157091", MemorySession.global())

  override def folderIdMusic: MemorySegment =
    toGuid("4BD8D571-6D19-48D3-BE97-422220080E43", MemorySession.global())

  override def folderIdPictures: MemorySegment =
    toGuid("33E28130-4E1E-4676-835A-98395C3BC3BB", MemorySession.global())

  override def folderIdProfile: MemorySegment =
    toGuid("5E6C858F-0E22-4760-9AFE-EA3317B67173", MemorySession.global())

  override def folderIdPublic: MemorySegment =
    toGuid("DFDF76A2-C82A-4D63-906A-5644AC457385", MemorySession.global())

  override def folderIdRoamingAppData: MemorySegment =
    toGuid("3EB685DB-65F9-4CF6-A03A-E3EF65729F3D", MemorySession.global())

  override def folderIdTemplates: MemorySegment =
    toGuid("A63293E8-664E-48DB-A079-DF759E0509F7", MemorySession.global())

  override def folderIdVideos: MemorySegment =
    toGuid("18989B1D-99B5-455B-841C-AB7C74E4DDFC", MemorySession.global())

  override def shGetKnownFolderPath(rfid: MemorySegment): Option[Path] =
    Using(MemorySession.openConfined()) { session =>
      val strBox: MemorySegment = MemorySegment.allocateNative(ValueLayout.ADDRESS, session)
      val ret: Int = gkfh.invoke(rfid.address(), 0, MemoryAddress.NULL, strBox.address())
      val strAddr = strBox.get(ValueLayout.ADDRESS, 0)
      if (ret == 0) {
        val strLen: Int = lstrlenh.invoke(strAddr)
        val byteSize: Long = ValueLayout.JAVA_CHAR.byteSize() * strLen
        val byteArray: Array[Byte] = MemorySegment.ofAddress(strAddr, byteSize, session).toArray(ValueLayout.JAVA_BYTE)
        val decode: CharBuffer = StandardCharsets.UTF_16LE.decode(ByteBuffer.wrap(byteArray))
        Some(Paths.get(decode.toString))
      } else {
        None
      }
    }.get
}
