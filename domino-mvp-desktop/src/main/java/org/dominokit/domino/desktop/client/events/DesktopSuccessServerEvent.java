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
package org.dominokit.domino.desktop.client.events;

import org.dominokit.domino.api.client.ClientApp;
import org.dominokit.rest.shared.Event;
import org.dominokit.rest.shared.request.Request;
import org.dominokit.rest.shared.request.ServerRequest;

public class DesktopSuccessServerEvent<T> implements Event {
  private final ServerRequest request;
  private final T responseBean;

  public DesktopSuccessServerEvent(ServerRequest request, T responseBean) {
    this.request = request;
    this.responseBean = responseBean;
  }

  @Override
  public void fire() {
    ClientApp.make().getAsyncRunner().runAsync(this::process);
  }

  @Override
  public void process() {
    request.applyState(new Request.ServerResponseReceivedStateContext(makeSuccessContext()));
  }

  private Request.ServerSuccessRequestStateContext makeSuccessContext() {
    return new Request.ServerSuccessRequestStateContext(responseBean);
  }
}
