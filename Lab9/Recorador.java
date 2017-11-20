public class Recorador
{
	public static void main(String [] args) {
		
		String input_txt = args[0];

		// Vertice de partida con id cocina
		String cocina = new String (args[1]);

		GrafoNoDirigido gnd = new GrafoNoDirigido();

		gnd.cargarGrafo(input_txt);

		AEstrella costo;

		for( Vertice c :gnd.vertices())
		{

			if(cocina != c.getId())
			{
				costo = new AEstrella(gnd,cocina,c.getId());
			}
		}
	}
}