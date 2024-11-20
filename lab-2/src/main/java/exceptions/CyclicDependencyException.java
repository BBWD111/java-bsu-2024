package exceptions;

public class CyclicDependencyException extends RuntimeException {
  public CyclicDependencyException() {
    super("Cyclic dependency detected");
  }
}
