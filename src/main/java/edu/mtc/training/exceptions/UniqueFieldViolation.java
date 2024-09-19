package edu.mtc.training.exceptions;

public class UniqueFieldViolation extends RuntimeException {

  public UniqueFieldViolation() {
  }

  public UniqueFieldViolation(String message) {
    super(message);
  }
}
