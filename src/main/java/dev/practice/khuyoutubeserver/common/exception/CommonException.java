package dev.practice.khuyoutubeserver.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException {

  private final ErrorCode errorCode;

  @Override
  public String getMessage() {
    return errorCode.getMessage();
  }
}
