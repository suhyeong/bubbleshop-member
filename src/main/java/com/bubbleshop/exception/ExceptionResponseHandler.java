package com.bubbleshop.exception;

import com.bubbleshop.constants.ResponseCode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

import static com.bubbleshop.constants.StaticValues.RESULT_CODE;
import static com.bubbleshop.constants.StaticValues.RESULT_MESSAGE;

@Slf4j
@RestControllerAdvice
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    private HttpHeaders getErrorHeader(ResponseCode responseCode, String customMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(RESULT_CODE, responseCode.getResponseCode());
        headers.add(RESULT_MESSAGE, URLEncoder.encode(StringUtils.defaultIfBlank(customMessage, responseCode.getMessage()), StandardCharsets.UTF_8));
        return headers;
    }

    private HttpHeaders getErrorHeader(String resultCode, String resultMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(RESULT_CODE, resultCode);
        headers.add(RESULT_MESSAGE, URLEncoder.encode(resultMessage, StandardCharsets.UTF_8));
        return headers;
    }

    private HttpHeaders getErrorHeader(ResponseCode responseCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(RESULT_CODE, responseCode.getResponseCode());
        headers.add(RESULT_MESSAGE, URLEncoder.encode(responseCode.getMessage(), StandardCharsets.UTF_8));
        return headers;
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        ResponseCode responseCode = ResponseCode.INVALID_PARAMETER;
        AtomicReference<String> message = new AtomicReference<>(responseCode.getMessage());
        BindingResult bindingResult = ex.getBindingResult();

        bindingResult.getAllErrors().forEach(error -> {
            log.error("MethodArgumentNotValidException 에러 발생, 메세지 : {}", error.getDefaultMessage());
            if (StringUtils.isNotBlank(error.getDefaultMessage()))
                message.set(error.getDefaultMessage());
        });

        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .responseCode(responseCode.getResponseCode())
                .responseMessage(message.get()).build();

        return new ResponseEntity<>(responseDto, getErrorHeader(responseCode, message.get()), responseCode.getStatus());
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        String message = StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : ResponseCode.INVALID_PARAMETER.getMessage();
        return ResponseEntity.badRequest().headers(getErrorHeader(ResponseCode.INVALID_PARAMETER, message)).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ExceptionResponseDto> handleException(ConstraintViolationException exception) {
        log.error("ConstraintViolationException 에러 발생, 메세지 : {}", exception.getMessage());
        ResponseCode responseCode = ResponseCode.INVALID_PARAMETER;
        String message = responseCode.getMessage();

        if(StringUtils.isNotBlank(exception.getMessage())) {
            String[] messages = exception.getMessage().split(":");
            message = messages[messages.length-1].trim();
        }

        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .responseCode(responseCode.getResponseCode())
                .responseMessage(message).build();

        return new ResponseEntity<>(responseDto, getErrorHeader(responseCode, message), responseCode.getStatus());
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Void> handler(ApiException apiException) {
        log.error("ApiException error code : {}, error message : {}", apiException.getResultCode(), apiException.getResultMessage());
        return new ResponseEntity<>(null, getErrorHeader(apiException.getResultCode(), apiException.getResultMessage()), apiException.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Void> handler(Exception exception) {
        log.error("Exception error : ", exception);
        return new ResponseEntity<>(null, getErrorHeader(ResponseCode.SERVER_ERROR), ResponseCode.SERVER_ERROR.getStatus());
    }

}
