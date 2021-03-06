// Laboratorio 9
// Ricardo Di Zio 11-11274
// Fabio Suarez   12-10578

import java.lang.*;
import java.util.*;
import java.text.DecimalFormat;

public class AEstrella{
	
	private List<Vertice> listadeVertices;
	public Set<Vertice> visitados = new HashSet<Vertice>();
	public int cantidadNoVisitados;
	public int cantidadAbiertos;
	public int cantidadCerrados;
	public LinkedList<Stack<Vertice>> caminos;
	private int lados[];
	private Double costos[];
	private DecimalFormat df;
	private List<Vertice> openSet;
	private  List<Vertice> closedSet;
	public  Stack<Vertice> total_path;
	private Double fScore[];
	private Double gScore[];
	private Vertice cameFrom[];
	private Vertice goal;
	private Vertice ver;
	// s start vertice y g goal vertice
	// Falta hacer el conteo de los lados y probar si funciona la implementacion
	public AEstrella(GrafoNoDirigido G, String s, String g)
	{
		df = new DecimalFormat("0.0#");
		Double inf = Double.MAX_VALUE;
		Double tentative_gScore = 0.0;

		this.cantidadNoVisitados = 0;
		this.cantidadAbiertos = 1;
		this.cantidadCerrados = 0;
		this.caminos = new LinkedList<Stack<Vertice>>();
		this.closedSet = new ArrayList<Vertice> ();
		this.openSet = new ArrayList<Vertice> ();
		this.gScore = new Double[G.numeroDeVertices()];
		this.costos = new Double[G.numeroDeVertices()];
		//this.caminos = new String[G.numeroDeVertices()];
		this.lados = new int[G.numeroDeVertices()]; 
		this.cameFrom = new Vertice[G.numeroDeVertices()];
		this.fScore = new Double[G.numeroDeVertices()];


		listadeVertices = G.vertices();
		this.ver = G.MapaDeVertices.get(s);
		this.goal = G.MapaDeVertices.get(g);
		for (Vertice v: listadeVertices)
		{
			Vertice nulo = new Vertice("null",0.0);
			this.fScore[Integer.valueOf(v.getId())] = inf;
			this.gScore[Integer.valueOf(v.getId())] = inf;
			this.cameFrom[Integer.valueOf(v.getId())] = null;
			this.lados[Integer.valueOf(v.getId())] = 0;
			if ( s.equals(v.getId()))
			{
				this.gScore[Integer.valueOf(this.ver.getId())] = 0.0;
				openSet.add(this.ver);
				visitados.add(ver);				
			}

		}
		this.fScore[Integer.valueOf(this.ver.getId())] = costo(this.ver, this.goal);

		// Ordenamos el array list (openSet de prioridad)
		Comparator<Vertice> comp = (Vertice a, Vertice b) -> {
    		return this.fScore[Integer.valueOf(a.getId())].compareTo(this.fScore[Integer.valueOf(b.getId())]);
		};
		Vertice x;
		while (openSet.size() > 0)
		{
			Collections.sort(openSet,comp);
			x = openSet.get(0);

			if (x.getId().equals(g)) {
				this.reconstructPath(this.cameFrom,x);
				break;
			}
			openSet.remove(0);
			closedSet.add(x);
			visitados.add(x);
			this.cantidadCerrados = this.cantidadCerrados + 1;

			for (Vertice v1: x.getListaDeAdyacencias()) 
			{
				if (closedSet.contains(v1)) {
					continue;
				}

				if (!openSet.contains(v1)) {
					openSet.add(v1);
					visitados.add(v1);
					this.cantidadAbiertos = this.cantidadAbiertos + 1;
				}

				tentative_gScore = gScore[stringToInt(x)] + costo(x, v1);
            	if (tentative_gScore >= gScore[stringToInt(v1)]){
            		continue;
            	}

				cameFrom[stringToInt(v1)] = x;
				gScore[stringToInt(v1)] = tentative_gScore;
				fScore[stringToInt(v1)] = gScore[stringToInt(v1)] + costo(v1,goal);		
			}
		}
		//System.out.println("VISITADOS--------------------");
		//System.out.println(visitados.size());
		cantidadNoVisitados = G.numeroDeVertices() - visitados.size();		
	}

	public int stringToInt(Vertice v)
	{
		String str = v.getId();
		Integer pos = Integer.valueOf(str);
		return pos;
	}

	public Double costo(Vertice x, Vertice y){
		Double sumX = 0.0;
		Double sumY = 0.0;
		Double distEuclid = 0.0;
		sumX = Math.pow((x.ejeX - y.ejeX),2);
		sumY = Math.pow((x.ejeY - y.ejeY),2);
		distEuclid = Math.sqrt(sumX + sumY);
		return distEuclid;
	}

	public void reconstructPath(Vertice[] cameFrom,Vertice current){
		total_path = new Stack<Vertice>();
		this.total_path.push(current);
    	while(cameFrom[Integer.valueOf(current.getId())] != null)
    	{
        	current = cameFrom[Integer.valueOf(current.getId())];
        	this.total_path.push(current);

    	}
    	this.caminos.add(this.total_path);
    }
}