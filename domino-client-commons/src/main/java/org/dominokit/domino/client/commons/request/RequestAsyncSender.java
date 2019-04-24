package org.dominokit.domino.client.commons.request;

import org.dominokit.domino.api.shared.request.ServerRequest;

@FunctionalInterface
public interface RequestAsyncSender {
    void send(ServerRequest request);
}
