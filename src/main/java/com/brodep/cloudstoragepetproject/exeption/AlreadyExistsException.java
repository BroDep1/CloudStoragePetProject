package com.brodep.cloudstoragepetproject.exeption;

public class AlreadyExistsException extends RuntimeException {
  public AlreadyExistsException(String message) {
    super(message);
  }
}
