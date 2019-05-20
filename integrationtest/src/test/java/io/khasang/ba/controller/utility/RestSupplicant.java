package io.khasang.ba.controller.utility;

/**
 * Simple functional interface used, to wrap code, designed to send request to and to get back response
 * from REST resource
 *
 * @param <T> body, returned from response, typically - entity class
 */
@FunctionalInterface
public interface RestSupplicant<T> {
    T getResponseBody();
}
