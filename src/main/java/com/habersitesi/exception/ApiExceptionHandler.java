package com.habersitesi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntime(RuntimeException ex, HttpServletRequest request) {
        ApiErrorResponse err = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "İşlem Hatası",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(KullaniciBulunamadiException.class)
    public ResponseEntity<ApiErrorResponse> handleKullanici(KullaniciBulunamadiException ex, HttpServletRequest req) {
        return errorResponse(req, ex, HttpStatus.NOT_FOUND, "Kullanıcı Hatası");
    }

    @ExceptionHandler(HaberBulunamadiException.class)
    public ResponseEntity<ApiErrorResponse> handleHaber(HaberBulunamadiException ex, HttpServletRequest req) {
        return errorResponse(req, ex, HttpStatus.NOT_FOUND, "Haber Hatası");
    }

    @ExceptionHandler(KategoriBulunamadiException.class)
    public ResponseEntity<ApiErrorResponse> handleKategori(KategoriBulunamadiException ex, HttpServletRequest req) {
        return errorResponse(req, ex, HttpStatus.NOT_FOUND, "Kategori Hatası");
    }

    @ExceptionHandler(YetkisizIslemException.class)
    public ResponseEntity<ApiErrorResponse> handleYetkisiz(YetkisizIslemException ex, HttpServletRequest req) {
        return errorResponse(req, ex, HttpStatus.FORBIDDEN, "Yetki Hatası");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenel(Exception ex, HttpServletRequest req) {
        return errorResponse(req, ex, HttpStatus.INTERNAL_SERVER_ERROR, "Beklenmeyen Hata");
    }

    private ResponseEntity<ApiErrorResponse> errorResponse(HttpServletRequest req, Exception ex, HttpStatus status, String hataTipi) {
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        status.value(),
                        hataTipi,
                        ex.getMessage(),
                        req.getRequestURI()
                ),
                status
        );
    }
}
