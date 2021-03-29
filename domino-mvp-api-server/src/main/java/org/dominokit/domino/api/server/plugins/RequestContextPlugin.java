/*
 * Copyright © 2019 Dominokit
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
package org.dominokit.domino.api.server.plugins;

import com.google.auto.service.AutoService;
import org.dominokit.domino.api.server.DominoLoaderPlugin;
import org.dominokit.rest.DominoRestConfig;

@AutoService(DominoLoaderPlugin.class)
public class RequestContextPlugin extends BaseDominoLoaderPlugin {
  @Override
  protected void applyPlugin(CompleteHandler completeHandler) {
    DominoRestConfig.initDefaults();
    completeHandler.onCompleted();
  }

  @Override
  public int order() {
    return 25;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
