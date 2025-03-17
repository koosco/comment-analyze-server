package dev.practice.khuyoutubeserver.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.practice.khuyoutubeserver.common.exception.CommonException;
import dev.practice.khuyoutubeserver.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public record ResponseDto<T>(
    @JsonIgnore HttpStatus httpStatus,
    Boolean success,

    @Nullable
    T data,

    @Nullable
    ExceptionDto error) {

    public static <T> ResponseDto<T> ok(@Nullable final T data) {
        return new ResponseDto<>(HttpStatus.OK, true, data, null);
    }

    public static <T> ResponseDto<T> created(@Nullable final T data) {
        return new ResponseDto<>(HttpStatus.CREATED, true, data, null);
    }

    public static ResponseDto<Object> fail(final MissingServletRequestParameterException e) {
        return new ResponseDto<>(
            HttpStatus.BAD_REQUEST, false, null, ExceptionDto.of(ErrorCode.MISSING_REQUEST_PARAMETER));
    }

    public static ResponseDto<Object> fail(final MethodArgumentTypeMismatchException e) {
        return new ResponseDto<>(
            HttpStatus.INTERNAL_SERVER_ERROR,
            false,
            null,
            ExceptionDto.of(ErrorCode.INVALID_PARAMETER_FORMAT));
    }

    public static ResponseDto<Object> fail(final CommonException e) {
        return new ResponseDto<>(
            e.getErrorCode().getHttpStatus(), false, null, ExceptionDto.of(e.getErrorCode()));
    }
}
