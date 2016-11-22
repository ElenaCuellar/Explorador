import java.io.File;

public class Explorador {
	String ruta; //ruta al directorio
	String mensaje; //mensaje de error que se mostrara en un dialog
	File[] ficheros;
	
	public Explorador(String ruta){
		this.ruta=ruta;
		mensaje="";
		ficheros=null;
	}
	
	public void setRuta(String r){ruta=r;}
	
	public boolean mostrarArchivos(){ //si no existe o no es un directorio devuelve false
		File f;
		try{
			f = new File(ruta); //se crea el objeto con la ruta indicada
			if (f.exists()){
				if(f.isDirectory()){
					ficheros = f.listFiles();//se llena el array con el contenido de la carpeta		
					return true;
				} else {mensaje="No es un directorio";}
			} else {mensaje="No existe la ruta";}
		}catch(NullPointerException e){
			System.err.println("No existe la ruta");
		}catch(SecurityException e){
			System.err.println("Error de seguridad");
		}
		return false;
	}
}
