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

import java.nio.file.Path
import munit.FunSuite
import scala.collection.SortedMap

class DirsSuite extends FunSuite {

  test("Dirs should not produce exceptions or null") {
    DirsSuite.all.foreach {
      case (name, producer) =>
        val result = producer()
        println(s"$name: $result")
        assert(result.isInstanceOf[Option[Path]])
    }
  }

}

object DirsSuite {
  private final lazy val all: SortedMap[String, () => Option[Path]] = SortedMap(
    "audio" -> (() => Dirs.audio),
    "cache" -> (() => Dirs.cache),
    "config" -> (() => Dirs.config),
    "configLocal" -> (() => Dirs.configLocal),
    "data" -> (() => Dirs.data),
    "dataLocal" -> (() => Dirs.dataLocal),
    "desktop" -> (() => Dirs.desktop),
    "document" -> (() => Dirs.document),
    "download" -> (() => Dirs.download),
    "executable" -> (() => Dirs.executable),
    "font" -> (() => Dirs.font),
    "home" -> (() => Dirs.home),
    "picture" -> (() => Dirs.picture),
    "preference" -> (() => Dirs.preference),
    "public" -> (() => Dirs.public),
    "runtime" -> (() => Dirs.runtime),
    "state" -> (() => Dirs.state),
    "template" -> (() => Dirs.template),
    "video" -> (() => Dirs.video),
  )
}