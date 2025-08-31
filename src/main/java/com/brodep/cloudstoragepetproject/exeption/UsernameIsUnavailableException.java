package com.brodep.cloudstoragepetproject.exeption;

public class UsernameIsUnavailableException extends RuntimeException {
  public UsernameIsUnavailableException(String message) {
    super(message);
  }
}
