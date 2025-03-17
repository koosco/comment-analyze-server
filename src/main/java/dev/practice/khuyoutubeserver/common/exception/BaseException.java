package dev.practice.khuyoutubeserver.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends Exception {

  private BaseResponseStatus baseResponseStatus;
}
