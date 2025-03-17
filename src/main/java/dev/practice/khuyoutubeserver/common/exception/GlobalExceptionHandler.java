package dev.practice.khuyoutubeserver.common.exception;

import dev.practice.khuyoutubeserver.common.dto.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // Convertor 에서 바인딩 실패시 발생하는 예외
  @ExceptionHandler(value = {HttpMessageNotReadableException.class})
  public ResponseDto<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    log.error(
        "handleHttpMessageNotReadableException() in GlobalExceptionHandler throw HttpMessageNotReadableException : {}",
        e.getMessage());
    return ResponseDto.fail(new CommonException(ErrorCode.BAD_REQUEST_JSON));
  }

  // 지원되지 않는 HTTP 메소드를 사용할 때 발생하는 예외
  @ExceptionHandler(
      value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
  public ResponseDto<?> handleNoPageFoundException(Exception e) {
    log.error(
        "handleNoPageFoundException() in GlobalExceptionHandler throw NoHandlerFoundException : {}",
        e.getMessage());
    return ResponseDto.fail(new CommonException(ErrorCode.NOT_FOUND_END_POINT));
  }

  // 메소드의 인자 타입이 일치하지 않을 때 발생하는 예외
  @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
  public ResponseDto<?> handleArgumentNotValidException(MethodArgumentTypeMismatchException e) {
    log.error(
        "handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentTypeMismatchException : {}",
        e.getMessage());
    return ResponseDto.fail(e);
  }

  // 필수 파라미터가 누락되었을 때 발생하는 예외
  @ExceptionHandler(value = {MissingServletRequestParameterException.class})
  public ResponseDto<?> handleArgumentNotValidException(MissingServletRequestParameterException e) {
    log.error(
        "handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}",
        e.getMessage());
    return ResponseDto.fail(e);
  }

  // 개발자가 직접 정의한 예외
  @ExceptionHandler(value = {CommonException.class})
  @ResponseBody
  public ResponseDto<?> handleApiException(CommonException e, HttpServletResponse response) {
    log.error(
        "handleApiException() in GlobalExceptionHandler throw CommonException : {}",
        e.getMessage());
    response.setStatus(e.getErrorCode().getHttpStatus().value());
    return ResponseDto.fail(e);
  }

  // 서버, DB 예외
  @ExceptionHandler(value = {Exception.class})
  public ResponseDto<?> handleException(Exception e, HttpServletResponse response) {
    log.error("handleException() in GlobalExceptionHandler throw Exception : {}", e.getMessage());
    e.printStackTrace();
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseDto.fail(new CommonException(ErrorCode.INTERNAL_SERVER_ERROR));
  }
}
