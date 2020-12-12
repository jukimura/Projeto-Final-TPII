public class ComunicadoDeInicio implements Comunicado
{
	private boolean podeComecar;
	
	public ComunicadoDeInicio(boolean comecar)
	{
		this.podeComecar = comecar;
	}
	
	public boolean getPodeComecar()
	{
		return this.podeComecar;
	}
}
