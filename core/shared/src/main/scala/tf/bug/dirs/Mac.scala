package tf.bug.dirs

import java.nio.file.Path

trait Mac {
  def home: Option[Path]
  def cache: Option[Path]
  def config: Option[Path]
  def data: Option[Path]
  def dataLocal: Option[Path]
  def preference: Option[Path]
  def executable: Option[Path]
  def runtime: Option[Path]
  def state: Option[Path]
  def audio: Option[Path]
  def desktop: Option[Path]
  def document: Option[Path]
  def download: Option[Path]
  def font: Option[Path]
  def picture: Option[Path]
  def public: Option[Path]
  def template: Option[Path]
  def video: Option[Path]
}
