package org.acme.api;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.domain.NewPaymentRequest;
import org.acme.domain.PaymentWorker;

import java.math.BigDecimal;

@Path("/payments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RunOnVirtualThread
public class PaymentsResource {

    private final PaymentWorker paymentWorker;

    public PaymentsResource(PaymentWorker paymentWorker) {
        this.paymentWorker = paymentWorker;
    }

    public record PaymentRequest(
            String correlationId,
            BigDecimal amount) {
        public NewPaymentRequest toNewPayment() {
            return new NewPaymentRequest(correlationId(), amount());
        }

        @JsonbCreator
        public static PaymentRequest of(
                @JsonbProperty("correlationId") String correlationId,
                @JsonbProperty("amount") BigDecimal amount) {
            return new PaymentRequest(correlationId, amount);
        }

    }

    @POST
    public Response process(PaymentRequest request) {
        if (paymentWorker.accept(request.toNewPayment())) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.TOO_MANY_REQUESTS).build();
    }

}

