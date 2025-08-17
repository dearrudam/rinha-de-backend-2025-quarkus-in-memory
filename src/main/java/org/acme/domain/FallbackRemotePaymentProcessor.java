package org.acme.domain;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "fallback-payment-processor")
public interface FallbackRemotePaymentProcessor extends
        RemotePaymentProcessor {
}
