package tf.bug.dirs



object JVMTarget extends Target {
  def platform: Platform = {
    val osName = System.getProperty("os.name")
    if (osName.contains("Windows")) {
      Platform.Windows
    } else {
      Platform.Unknown(osName)
    }
  }

  def homeDir: Option[Path] = Some(Paths.get(System.getProperty("user.home")))
}
