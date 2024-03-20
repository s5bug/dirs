package tf.bug.dirs
import java.lang.foreign.MemoryLayout.PathElement
import java.lang.foreign.{AddressLayout, Arena, FunctionDescriptor, Linker, MemoryLayout, MemorySegment, SequenceLayout, SymbolLookup, ValueLayout}
import java.nio.{ByteBuffer, CharBuffer}
import java.nio.charset.StandardCharsets
import java.nio.file.{Path, Paths}
import java.util.UUID
import scala.util.Using

private[dirs] object WindowsPlatform extends Windows {

  private final val linker = Linker.nativeLinker()
  private final val arena = Arena.ofShared()
  private final val kernel32 = SymbolLookup.libraryLookup("kernel32", arena)
  private final val lstrlenwAddress = kernel32.find("lstrlenW").orElseThrow()
  private final val lstrlenwHandle = linker.downcallHandle(
    lstrlenwAddress,
    FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)
  )
  private final val shell32 = SymbolLookup.libraryLookup("shell32", arena)
  private final val getKnownFolderPathAddress = shell32.find("SHGetKnownFolderPath").orElseThrow()
  private final val getKnownFolderPathHandle = linker.downcallHandle(
    getKnownFolderPathAddress,
    FunctionDescriptor.of(
      ValueLayout.JAVA_INT,
      ValueLayout.ADDRESS,
      ValueLayout.JAVA_INT,
      ValueLayout.ADDRESS,
      ValueLayout.ADDRESS
    )
  )
  private final val ole32 = SymbolLookup.libraryLookup("Ole32", arena)
  private final val coTaskMemFreeAddress = ole32.find("CoTaskMemFree").orElseThrow()
  private final val coTaskMemFreeHandle = linker.downcallHandle(
    coTaskMemFreeAddress,
    FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)
  )

  private final val guidLayout =
    MemoryLayout.structLayout(
      ValueLayout.JAVA_INT.withName("data1"),
      ValueLayout.JAVA_SHORT.withName("data2"),
      ValueLayout.JAVA_SHORT.withName("data3"),
      MemoryLayout.sequenceLayout(8L, ValueLayout.JAVA_BYTE).withName("data4")
    )

  private final def allocateGuid(guid: String): MemorySegment = {
    val seg = arena.allocate(guidLayout)
    val uuid = UUID.fromString(guid)
    guidLayout.varHandle(PathElement.groupElement("data1"))
      .set(seg, 0L, ((uuid.getMostSignificantBits & 0xFFFFFFFF00000000L) >>> 32).toInt)
    guidLayout.varHandle(PathElement.groupElement("data2"))
      .set(seg, 0L, ((uuid.getMostSignificantBits & 0x00000000FFFF0000L) >>> 16).toShort)
    guidLayout.varHandle(PathElement.groupElement("data3"))
      .set(seg, 0L, ((uuid.getMostSignificantBits & 0x000000000000FFFFL) >>> 0).toShort)
    (0L to 7L).foreach { idx =>
      val shift = (7L - idx) * 8L
      val mask = 0xFFL << shift
      guidLayout.varHandle(PathElement.groupElement("data4"), PathElement.sequenceElement(idx))
        .set(seg, 0L, ((uuid.getLeastSignificantBits & mask) >>> shift).toByte)
    }
    seg
  }

  override final type KnownFolderId = MemorySegment

  override final lazy val folderIdDesktop: MemorySegment =
    allocateGuid("B4BFCC3A-DB2C-424C-B029-7FE99A87C641")

  override final lazy val folderIdDocuments: MemorySegment =
    allocateGuid("FDD39AD0-238F-46AF-ADB4-6C85480369C7")

  override final lazy val folderIdDownloads: MemorySegment =
    allocateGuid("374DE290-123F-4565-9164-39C4925E467B")

  override final lazy val folderIdLocalAppData: MemorySegment =
    allocateGuid("F1B32785-6FBA-4FCF-9D55-7B8E7F157091")

  override final lazy val folderIdMusic: MemorySegment =
    allocateGuid("4BD8D571-6D19-48D3-BE97-422220080E43")

  override final lazy val folderIdPictures: MemorySegment =
    allocateGuid("33E28130-4E1E-4676-835A-98395C3BC3BB")

  override final lazy val folderIdProfile: MemorySegment =
    allocateGuid("5E6C858F-0E22-4760-9AFE-EA3317B67173")

  override final lazy val folderIdPublic: MemorySegment =
    allocateGuid("DFDF76A2-C82A-4D63-906A-5644AC457385")

  override final lazy val folderIdRoamingAppData: MemorySegment =
    allocateGuid("3EB685DB-65F9-4CF6-A03A-E3EF65729F3D")

  override final lazy val folderIdTemplates: MemorySegment =
    allocateGuid("A63293E8-664E-48DB-A079-DF759E0509F7")

  override final lazy val folderIdVideos: MemorySegment =
    allocateGuid("18989B1D-99B5-455B-841C-AB7C74E4DDFC")

  override def shGetKnownFolderPath(rfid: MemorySegment): Option[Path] =
    Using(Arena.ofConfined()) { arena =>
      val strBox: MemorySegment = arena.allocate(ValueLayout.ADDRESS)
      val ret: Int = getKnownFolderPathHandle.invoke(rfid, 0, MemorySegment.NULL, strBox)
      val strAddr = strBox.get(ValueLayout.ADDRESS, 0)
      val result = if (ret == 0) {
        val strLen: Int = lstrlenwHandle.invoke(strAddr)
        val strLayout = MemoryLayout.sequenceLayout(strLen, ValueLayout.JAVA_CHAR)
        val strBoxLayout = ValueLayout.ADDRESS.withTargetLayout(strLayout)
        val byteBuf: ByteBuffer = strBox.get(strBoxLayout, 0).asByteBuffer()
        val decode: CharBuffer = StandardCharsets.UTF_16LE.decode(byteBuf)
        Some(Paths.get(decode.toString))
      } else {
        None
      }
      coTaskMemFreeHandle.invoke(strAddr)
      result
    }.get

}
