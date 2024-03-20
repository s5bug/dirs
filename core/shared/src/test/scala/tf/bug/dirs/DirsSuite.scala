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

class DirsSuite extends FunSuite {

  test("Dirs should not produce exceptions or null") {
    assert(Dirs.audio.isInstanceOf[Option[Path]])
    assert(Dirs.cache.isInstanceOf[Option[Path]])
    assert(Dirs.config.isInstanceOf[Option[Path]])
    assert(Dirs.configLocal.isInstanceOf[Option[Path]])
    assert(Dirs.data.isInstanceOf[Option[Path]])
    assert(Dirs.dataLocal.isInstanceOf[Option[Path]])
    assert(Dirs.desktop.isInstanceOf[Option[Path]])
    assert(Dirs.document.isInstanceOf[Option[Path]])
    assert(Dirs.download.isInstanceOf[Option[Path]])
    assert(Dirs.executable.isInstanceOf[Option[Path]])
    assert(Dirs.font.isInstanceOf[Option[Path]])
    assert(Dirs.home.isInstanceOf[Option[Path]])
    assert(Dirs.picture.isInstanceOf[Option[Path]])
    assert(Dirs.preference.isInstanceOf[Option[Path]])
    assert(Dirs.public.isInstanceOf[Option[Path]])
    assert(Dirs.runtime.isInstanceOf[Option[Path]])
    assert(Dirs.state.isInstanceOf[Option[Path]])
    assert(Dirs.template.isInstanceOf[Option[Path]])
    assert(Dirs.video.isInstanceOf[Option[Path]])
  }

}
