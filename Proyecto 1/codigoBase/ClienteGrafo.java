/**
 *
 */

import java.util.*;

public class ClienteGrafo {
  public static void main(String [] args) {


    GrafoNoDirigido g = new GrafoNoDirigido();
    g.cargarGrafo(args[0]);
    //g.eliminarArista("arista1");
    //g.eliminarArista("arista2");
    //g.eliminarArista("arista3");
    //g.eliminarArista("arista4");
    //g.eliminarArista("arista5");
    //g.eliminarVertice("v5");
    g.eliminarVertice("v1");
    System.out.println(g.toString());


/*
  	Vertice x = new Vertice("1",1);
  	Vertice y = new Vertice("2" ,2);	
  	String id = x.getId();
  	Double peso = x.getPeso();
  	String id2 = x.getId();
  	Double peso2 = x.getPeso();
  	
  	System.out.println("el id es "+id+"\n"+"el peso es "+peso);
  	System.out.println("el id es "+id2 +"\n"+"el peso es "+ peso2);
  	
  	Arista arista = new Arista("arista1", 5.0, x, y);
  	StdOut.print("\n"+arista.toString());

  	Arco arco = new Arco("arco1",6.0,x,y);
  	StdOut.print("\n"+arco.toString());


*/
  }
}