package org.acme.infrastructure;

import org.acme.domain.PaymentsTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("InMemoryPayments Tests")
class InMemoryPaymentsTests implements PaymentsTests.AllTests {

    private InMemoryPayments payments;

    @BeforeEach
    void setUp() {
        payments = new InMemoryPayments(InMemoryPayments.DEFAULT_PARALLEL_STREAM_THRESHOLD);
    }

    @Override
    public Context testContext() {
        return Context.of(payments);
    }

}
