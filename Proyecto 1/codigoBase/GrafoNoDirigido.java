/**
 * 
 */

import java.util.*;

public class GrafoNoDirigido implements Grafo
{
    private int numeroDeVertices;
    private int numeroDeLados;
    //private List<Vertice> lista_de_vertices;
    //private List<Arista> lista_de_aristas;
    private HashMap<String, Arista> MapaDeAristas;
    private HashMap<String, Vertice> MapaDeVertices;


    public GrafoNoDirigido() 
    {
        numeroDeVertices = 0;
        numeroDeLados = 0;
        MapaDeAristas = new HashMap <String,Arista>();
        MapaDeVertices = new HashMap <String, Vertice>();
        //lista_de_vertices = new LinkedList<Vertice>();
        //lista_de_aristas = new LinkedList<Arista>();  
    }

    public boolean cargarGrafo(String dirArchivo) {
        // Continuar despues de implementar las funciones del grafo ya que son necesarias para terminar de cargar el grafo

        In in = new In(dirArchivo);
        int cantidad_de_nodos = in.readInt();
        int cantidad_de_aristas = in.readInt();

        for (int i=0;i<cantidad_de_nodos;i++) {
            String id_del_vertice = in.readString();
            double peso_del_vertice = in.readDouble();
            
            agregarVertice(id_del_vertice, peso_del_vertice);
        }

        for (int i=0;i<cantidad_de_aristas;i++) {
            String id_de_arista = in.readString();
            String id_vertice_de_Salida = in.readString();
            String id_vertice_de_Llegada = in.readString();
            double peso_de_arista = in.readDouble();
            
            agregarArista(id_de_arista, peso_de_arista,id_vertice_de_Salida, id_vertice_de_Llegada);
            
        }
        return true;

    }
    
    public int numeroDeVertices() {
        //Verificar si mantienen numero de vertices la misma cantidad de elementos que size 
        return numeroDeVertices;
    }

    public int numeroDeLados() {
        return numeroDeLados;
    }
   
    public boolean agregarVertice(Vertice v) {  
        // Si el id del vertice ya se encuentra en la lista devuelve un falso, por lo tanto no procede a agregarlo  
        if(MapaDeVertices.get(v.getId()) != null)
        {
            System.out.println("El vertice con identificador "+v.getId()+
                    " ya se encuentra en el grafo.");
            return false;
        }

        MapaDeVertices.put(v.getId(),v);
        numeroDeVertices++;
        return true;
        
    }

    public boolean agregarVertice(String id, double peso) {
        boolean booleano;
        Vertice v = new Vertice(id,peso);
        booleano = agregarVertice(v);
        
        return booleano;
    }
    
    public Vertice obtenerVertice(String id) throws NoSuchElementException {
        
        if(MapaDeVertices.get(id)!= null)
        {
            return MapaDeVertices.get(id); 
        }  
        
        throw new NoSuchElementException("El vertice con el idenficador: " 
            +id+ " no se encuentra en el Grafo");
    }

    public boolean estaVertice(String id) {
        
        /*if(MapaDeVertices.get(id)!= null)
        {
            return true; 
        }  
        return false;*/
        //Returns true if this map maps one or more keys to the specified value.
        return MapaDeVertices.containsKey(id);
    }

    public boolean estaLado(String u, String v){        
        //revisar
          // Mas rapida porque aprovecha los ids de los vertices para buscarlos con el HashMap dentro de la lista de vertices y luego en la lista de adyacentes busca el otro id
        // ESTA RELACION DEBERIA SER PARA AMBOS LADOS por ser GND,verificar            
        if (estaVertice(u) == true && estaVertice(v) == true ) {

            Vertice v1 = MapaDeVertices.get(u);

            Vertice v2 = MapaDeVertices.get(v);

            for(Vertice x : v1.getListaDeAdyacencias()){
                if(v1.getId().equals(u) && x.getId().equals(v)){
                    return true;
                }
            
            }
            for(Vertice x : v2.getListaDeAdyacencias()){
                if(v2.getId().equals(v)  && x.getId().equals(u)){
                    return true;
                }
            
            }
        }

        return false;
                
    

        
        // 2da MEJOR MANERA IMPLEMENTADA Si tuviesemos el id del lado fuese la mejor opcion
        /* for (Arista l: MapaDeAristas.values()) {
            if ((l.getEstremo1() == u && l.getExtremo2() == v) || (l.getEstremo1() == v && l.getExtremo2() == u)) {
                return true;
            }
        }
        return false;
        */
        //Arista arista = new Arista(u,v);
        //containsValue()
    }

    public boolean eliminarVertice(String id) {

        List<Vertice> temp_lista_adyacencia = new LinkedList<Vertice>();
        List<Arista> temp_lista_incidencia = new LinkedList<Arista>();

        if (estaVertice(id)) {

            Vertice verticeTemp = MapaDeVertices.get(id);
            //Arista arista = MapaDeAristas.

            // MapaDeVertices.get(id).getListaDeIncidencias()

            for(Lado l : verticeTemp.getListaDeIncidencias()){
                    Arista arista = (Arista)l;
                    //Arista aristaTemp = MapaDeAristas.get(a.getId());
                    temp_lista_incidencia.add(arista);
            }

            // Borrando Incidencias
            for (Lado arista: temp_lista_incidencia) {

                int posicion = verticeTemp.getListaDeIncidencias().indexOf(arista);
                //eliminarArista(arista.getId());
                if (posicion != -1) {
                    eliminarArista(verticeTemp.getListaDeIncidencias().get(posicion).getId());                     
                }
                else{
                    continue;
                }
                             
                
            }
            //temporal de vertices lista de adyacencia
            for(Vertice vertice : verticeTemp.getListaDeAdyacencias()){
                    temp_lista_adyacencia.add(vertice);
            }

            //Borrando Adyacencias
            for (Vertice v: temp_lista_adyacencia) {

                int pos = verticeTemp.getListaDeAdyacencias().indexOf(v);
                
                if (pos != -1) {
                   
                    MapaDeVertices.get(verticeTemp.getListaDeAdyacencias().get(pos).getId()).getListaDeAdyacencias().remove(verticeTemp);

                }
                else{
                    continue;
                }
            }

            /*for (Lado l: verticeTemp.getListaDeIncidencias()) {

                Arista a = (Arista)l;
                Arista aristaTemp = MapaDeAristas.get(a.getId());
                
                MapaDeVertices.get(a.getExtremo1().getId()).getListaDeIncidencias().remove(aristaTemp);
                MapaDeVertices.get(a.getExtremo2().getId()).getListaDeIncidencias().remove(aristaTemp);

                MapaDeAristas.remove(a);
                numeroDeLados--;
            }*/
            MapaDeVertices.remove(id);
            numeroDeVertices--;

            return true;
        }

        else {

            return false;
        }

    }

            /*for(Arista arista1 : temp_lista_arista){
                if (MapaDeVertices.containsKey(aristaTemp.getExtremo1().getId()) && MapaDeVertices.containsKey(aristaTemp.getExtremo2().getId())) {
                    
                    if((arista1.getExtremo1().getId().equals(id)) || (arista1.getExtremo2().getId().equals(id))){
                        
                        verticeTemp.getListaDeIncidencias().remove(arista1);
                        aristaTemp.getExtremo2().getListaDeIncidencias().remove(aristaTemp);
                        numeroDeLados--;

                // Procedemos a borrar los nodos en la lista de adyacencias de cada nodo , devolver excepcion
                MapaDeVertices.get(arista1.getExtremo1().getId()).getListaDeAdyacencias().remove(arista1.getExtremo2());
                MapaDeVertices.get(arista1.getExtremo2().getId()).getListaDeAdyacencias().remove(arista1.getExtremo1());

                // Procedemos a borrar el lado de la lista de incidencia de ambos nodos
                aristaTemp.getExtremo1().getListaDeIncidencias().remove(aristaTemp);
                aristaTemp.getExtremo2().getListaDeIncidencias().remove(aristaTemp);
            
                //Borramos del HashMap de aristas

                MapaDeAristas.remove(aristaTemp);
                numeroDeLados--;
                    }
                    else{
                        continue;
                    }             
                
            }

            for (Vertice v: verticeTemp.getListaDeAdyacencias()) {

                MapaDeVertices.get(v.getId()).getListaDeAdyacencias().remove(verticeTemp);
                //.remove(verticeTemp);
            }-----------------------------------------------------------------------------------------------------------*/

            /*for (Lado l: verticeTemp.getListaDeIncidencias()) {

                Arista a = (Arista)l;
                Arista aristaTemp = MapaDeAristas.get(a.getId());
                
                MapaDeVertices.get(a.getExtremo1().getId()).getListaDeIncidencias().remove(aristaTemp);
                MapaDeVertices.get(a.getExtremo2().getId()).getListaDeIncidencias().remove(aristaTemp);

                MapaDeAristas.remove(a);
                numeroDeLados--;
            }*/
    public List<Vertice> vertices() {

        List<Vertice> return_list_vertices = new LinkedList<Vertice>();

        for (Vertice v : MapaDeVertices.values()) {
            return_list_vertices.add(v);
        }

        return return_list_vertices;
    }

    public List<Lado> lados() {

        List<Lado> return_list_lados = new LinkedList<Lado>();
        for (Arista l : MapaDeAristas.values()) {
            return_list_lados.add(l);
        }

        return return_list_lados;
    }

    public int grado(String id) {

        if(MapaDeVertices.get(id)!= null)
        {
            return MapaDeVertices.get(id).getListaDeIncidencias().size(); 
        }    
        throw new NoSuchElementException("El vertice con el idenficador: " 
            +id+ " no se encuentra en el Grafo");
    }

    public List<Vertice> adyacentes(String id) {
        
        if(MapaDeVertices.get(id)!= null)
        {
            return MapaDeVertices.get(id).getListaDeAdyacencias(); 
        }    
        throw new NoSuchElementException("El vertice con el idenficador: " 
            +id+ " no se encuentra en el Grafo");
    }
 
    public List<Lado> incidentes(String id) {

        /*List<Arista> lista_de_incidentes = new LinkedList();
        for (Arista arista: MapaDeAristas.values()) {
            if (arista.getExtremo1().getId() == id || arista.getExtremo2().getId() == id ) {
                
            }
            
        }*/
        return MapaDeVertices.get(id).getListaDeIncidencias();

    }
    
    public Object clone() {
        GrafoNoDirigido x = new GrafoNoDirigido();
        return x;
    }
    
    public String toString() {

        StringBuilder sb = new StringBuilder();
        HashSet setVertice = new HashSet();
        HashSet setArista = new HashSet();

        for (Vertice vertice: MapaDeVertices.values()) {
            sb.append("\n");
            sb.append("Vertice id ");
            sb.append(vertice.getId() + " - " + vertice.getPeso()+ " ADYACENCIAS --------------> ");
            sb.append(" [ ");
            if(vertice.getListaDeAdyacencias().size() >= 1){

                for (Vertice v: vertice.getListaDeAdyacencias()) {

                    setVertice.add(v.getId());                  
                }

                Iterator iterator = setVertice.iterator();
                while (iterator.hasNext()) {
                    sb.append(iterator.next() + ", ");
                }

                sb.append(" ] ");
            }

            else if (vertice.getListaDeAdyacencias().size() == 0) {
                sb.append(" No hay elementos adyacentes al vertice ");    
            }
                       
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append("Vertice id ");
            sb.append( vertice.getId() + "       INCIDENCIAS -------------->");
            sb.append("  [ ");
            
            if(vertice.getListaDeIncidencias().size() >= 1){

                for(Lado l: vertice.getListaDeIncidencias()){
                    
                    setArista.add(l.getId());
                }

                Iterator iterator = setArista.iterator();
                while (iterator.hasNext()) {
                    sb.append(iterator.next() + ", ");
                }

                sb.append("  ] ");
            }

            else if (vertice.getListaDeIncidencias().size() == 0) {
                sb.append(" No hay elementos incidentes al vertice ");    
            }

            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            
            // Limpiamos las listas para que no nos afecte en la impresion de las variables temporales
            setVertice.clear();
            setArista.clear();

        }

        return sb.toString();

    }

    public boolean agregarArista(Arista a) {
        
        int temp = numeroDeLados;

        /*for(Arista arista : MapaDeAristas.values())
        {
            if(arista.getId().equals(a.getId()))
            {
                System.out.println("El arista con el identificador '"
                    +arista.getId()+"' ya se encuentra en el grafo.");
                return false;
            }
        }*/
        if ((MapaDeAristas.containsKey(a.getId()))) {
            
            System.out.println("El arista con el identificador '"+a.getId()+"' ya se encuentra en el grafo.");
            return false;
            
        }

        /*for(Vertice vertice1 : MapaVertices.values()) {
            
            if(vertice1.getId().equals(a.getExtremoInicial().getId())){
                
                vertice1.getListaDeAdyacencias().add(a.getExtremoFinal()); 
                numeroDeLados++;
                lista_de_aristas.add(a);                
            }
        }*/ 
        if ((estaVertice(a.getExtremo1().getId())) && ( estaVertice(a.getExtremo2().getId()))) {
            // En GD debe agregar a un vertice en los sucesores y en el otro en los predecesores
            // para este caso agrega en los adyacentes
            obtenerVertice(a.getExtremo1().getId()).adyacencias.add(a.getExtremo2());
            obtenerVertice(a.getExtremo2().getId()).adyacencias.add(a.getExtremo1());
            //agregar incidencias
            obtenerVertice(a.getExtremo1().getId()).incidencias.add(a);
            obtenerVertice(a.getExtremo2().getId()).incidencias.add(a);

            numeroDeLados++;
            MapaDeAristas.put(a.getId(), a);

        }

        if(temp < numeroDeLados) {
            return true;
        }
        return false;
    }

    public boolean agregarArista(String id, double peso, String u, String v) {

        if(estaVertice(u) && estaVertice(v))
        {
            Vertice v1 = MapaDeVertices.get(u);
            Vertice v2 = MapaDeVertices.get(v);
            Arista arista = new Arista(id,peso,v1,v2);
            return agregarArista(arista);
        }
        return false;
    }

    public boolean eliminarArista(String id) {
        // Capaz tambien hay que verificar si estan ambo nodos de la arista dentro del grafo
        if (MapaDeAristas.containsKey(id)) {
            
            Arista aristaTemp = MapaDeAristas.get(id);
            // Y distintos de null?

            if (MapaDeVertices.containsKey(aristaTemp.getExtremo1().getId()) && MapaDeVertices.containsKey(aristaTemp.getExtremo2().getId())) {

                // Procedemos a borrar los nodos en la lista de adyacencias de cada nodo , devolver excepcion
                MapaDeVertices.get(aristaTemp.getExtremo1().getId()).getListaDeAdyacencias().remove(aristaTemp.getExtremo2());
                MapaDeVertices.get(aristaTemp.getExtremo2().getId()).getListaDeAdyacencias().remove(aristaTemp.getExtremo1());

                // Procedemos a borrar el lado de la lista de incidencia de ambos nodos
                aristaTemp.getExtremo1().getListaDeIncidencias().remove(aristaTemp);
                aristaTemp.getExtremo2().getListaDeIncidencias().remove(aristaTemp);
            
                //Borramos del HashMap de aristas

                MapaDeAristas.remove(aristaTemp);
                numeroDeLados--;

                return true;
            }

            
            // procedemos a borrar el lado de la lista de incidencia de ambos nodos

            //MapaDeVertices.get(aristaTemp.getExtremo1().getId).getListaDeIncidencias;

            /*for (Lado arista: MapaDeVertices.get(aristaTemp.getExtremo1().getId()).getListaDeIncidencias()) {

                if (arista.getId().equals(id)) {

                    //MapaDeAristas.remove(id);
                    arista.getExtremo1().getListaDeIncidencias().remove(arista);
                    
                }
                
            }*/           

            /*for (Lado (Arista)arista: MapaDeVertices.get(aristaTemp.getExtremo2().getId()).getListaDeIncidencias()) {

                if (arista.getId().equals(id)) {

                    //MapaDeAristas.remove(id);
                    arista.getExtremo2().getListaDeIncidencias().remove(arista);
                    
                }
                
            } */

            // procedemos a borrar el lado de la lista de incidencia de ambos nodos
            //Arista aristaTemp = MapaDeAristas.get(id);
            //if (aristaTemp.getId().equals(id)) {
            

            //MapaDeVertices.get(aristaTemp.getExtremo1().getId()).getListaDeIncidencias().remove(id);
            //MapaDeVertices.get(aristaTemp.getExtremo2().getId()).getListaDeIncidencias().remove(id);
            //aristaTemp.getExtremo1().getListaDeIncidencias().remove(id);
            //aristaTemp.getExtremo2().getListaDeIncidencias().remove(id);
            
            //Borramos del HashMap de aristas

            //MapaDeAristas.remove(id);
            return true;
        }

        else{

            return false;
        }
    }

    public Arista obtenerArista(String id) 
    {

         /* for(Arista arista : MapaDeAristas.key())
        {
            if(arista.equals(id);
            {
                System.out.println("La Arista con el identificador '"
                    +arista.getId()+"' ya se encuentra en el grafo.");
                return false;
            }
        }
        */

        return MapaDeAristas.get(id);
    }
}