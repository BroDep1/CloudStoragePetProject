package com.brodep.cloudstoragepetproject.exeption;

public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
