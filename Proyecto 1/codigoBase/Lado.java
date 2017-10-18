/**
 * 
 */

//library
import java.util.*;

public abstract class Lado
{
  private String id;
  private double peso;

  public Lado(String id, double peso) {
  	this.id = id;
  	this.peso = peso;
  }

  public String getId() {
  	return this.id;
  }

  public double getPeso() {
  	return this.peso;
  }

  public abstract String toString();
}