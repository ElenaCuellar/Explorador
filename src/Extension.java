import java.io.File;
import java.io.FilenameFilter;

public class Extension implements FilenameFilter {
	
	String extension;
	
	public Extension(String tipo) {
		if(tipo.charAt(0)=='.') //por si el usuario ha introducido el punto al escribir la extension
			extension=tipo;
		else
			extension = "."+tipo;
	}
	@Override
	//Debemos implementar el metodo accept
	public boolean accept(File f, String nombre){
		//Devuelve true si el archivo es de la extension deseada
		return nombre.endsWith(extension);
	}

}
