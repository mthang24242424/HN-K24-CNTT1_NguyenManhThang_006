package org.springframework.http;

public class ResponseEntity<T> {
    private final T body;
    private final HttpStatus status;

    public ResponseEntity(T body, HttpStatus status) {
        this.body = body;
        this.status = status;
    }

    public T getBody() {
        return body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static <T> ResponseEntity<T> ok(T body) {
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> status(HttpStatus status, T body) {
        return new ResponseEntity<>(body, status);
    }

    public static <T> ResponseEntity<T> badRequest() {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
