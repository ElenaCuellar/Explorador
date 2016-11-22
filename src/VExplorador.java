import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.io.File;
import java.io.FilenameFilter;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

public class VExplorador extends JFrame {

	private JPanel contentPane;
	private JTextField tfDir, tfExt;
	private JLabel lbInfo, lbNumAr;
	private DefaultTableModel modelo;
	private Explorador explorador;
	private Diagmsg diagmsg;
	private JTable tabla;
	private String sistemaOp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VExplorador frame = new VExplorador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VExplorador() {
		setTitle("Explorador de archivos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 535);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbDirectorio = new JLabel("Directorio: ");
		lbDirectorio.setFont(new Font("Arial", Font.BOLD, 15));
		lbDirectorio.setBounds(10, 30, 88, 14);
		contentPane.add(lbDirectorio);
		
		JLabel lblExtension = new JLabel("Extension: ");
		lblExtension.setFont(new Font("Arial", Font.BOLD, 15));
		lblExtension.setBounds(10, 75, 88, 14);
		contentPane.add(lblExtension);
		
		tfDir = new JTextField();
		//Evento al pulsar intro en el texto de busqueda = misma funcionalidad que el boton busqueda
		tfDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verBusqueda();
			}
		});		
		tfDir.setBounds(98, 28, 402, 20);
		contentPane.add(tfDir);
		tfDir.setColumns(10);
		
		tfExt = new JTextField();
		//Evento al pulsar intro en el texto de extension
		tfExt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filtrarExtension();
			}
		});		
		tfExt.setColumns(10);		
		tfExt.setBounds(98, 73, 402, 20);
		contentPane.add(tfExt);
		
		JButton bBuscarD = new JButton("search");
		//bBuscarD.setIcon(new ImageIcon(getClass().getResource("lupa-icono-3813-128.gif")));
		//Evento del boton de busqueda
		bBuscarD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verBusqueda();
			}
		});
		bBuscarD.setBounds(510, 24, 46, 35);
		contentPane.add(bBuscarD);
		
		JButton bBuscarE = new JButton("filter");
		//bBuscarE.setIcon(new ImageIcon(getClass().getResource("lupa-icono-3813-128.gif")));
		//Evento del boton de extension
		bBuscarE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filtrarExtension();
			}
		});
		bBuscarE.setBounds(510, 69, 46, 35);
		contentPane.add(bBuscarE);
		
		JButton bLimpiar = new JButton("Limpiar");
		//Evento del boton Limpiar
		bLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiar();
			}
		});		
		bLimpiar.setFont(new Font("Arial", Font.BOLD, 15));
		bLimpiar.setBounds(441, 436, 126, 35);
		contentPane.add(bLimpiar);
		
		lbNumAr = new JLabel(System.getProperty("0 archivos"));
		lbNumAr.setHorizontalAlignment(SwingConstants.CENTER);
		lbNumAr.setFont(new Font("Arial", Font.PLAIN, 15));
		lbNumAr.setBounds(52, 447, 379, 24);
		contentPane.add(lbNumAr);
		
		lbInfo = new JLabel("Ningun directorio");
		lbInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		lbInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lbInfo.setBounds(10, 104, 545, 23);
		contentPane.add(lbInfo);
		
		//Creación de la tabla
		modelo = new DefaultTableModel(new Object[][]{},new String[]{"Nombre","Extension","Tamanyo","F/D"});		
		tabla = new JTable();
		tabla.setBackground(Color.WHITE);
		tabla.setBounds(10, 136, 557, 289);
		tabla.setModel(modelo);
		
		JScrollPane scrollPane = new JScrollPane(tabla);
		tabla.setFillsViewportHeight(true);
		tabla.setForeground(Color.BLACK);
		tabla.setRowHeight(30);
		scrollPane.setBounds(10, 136, 557, 289);
		contentPane.add(scrollPane);	
		
		diagmsg = new Diagmsg(); //se crea el dialog
		explorador= new Explorador(tfDir.getText()); //se crea un objeto Explorador
		sistemaOp = System.getProperty("os.name"); //Informacion del OS
	}
	
	public void llenarTabla(File[] fs){
		limpiarFilas(); //se vacia la tabla
		//Se saca la informacion de los distintos ficheros en la tabla
		int cont=0;
		for(int i=0; i<fs.length;i++){ 
			try{
				File f2 = new File(fs[i].getAbsolutePath());
				String[] fila = new String[]{
					f2.getName(),mostrarExt(f2),mostrarSize(f2),mostrarTipo(f2)
				};
				cont++; //contar el numero de archivos
				modelo.addRow(fila);
			}catch(NullPointerException e){System.err.println("Error al obtener archivo");}
		}
		lbNumAr.setText(cont+" archivos");
	}
	
	public void verBusqueda(){
		boolean sinError=true;
		//Si no se escribe ruta, se muestra por defecto "c:" o "/home/"
		if(tfDir.getText().equals("")){
			if(sistemaOp.startsWith("Windows") || sistemaOp.startsWith("Microsoft"))
				tfDir.setText("c:");
			else
				tfDir.setText("/home/");
		}
		//Se extrae la ruta del campo de texto y se escribe en el objeto Explorador
		explorador.setRuta(tfDir.getText());
		//Llena el array de archivos en caso de que el directorio sea correcto=exista y sea una carpeta
		sinError=explorador.mostrarArchivos(); 
		if(sinError){
			llenarTabla(explorador.ficheros);
			lbInfo.setText("Listando todos los archivos");
		}
		else{
			lbInfo.setText("No existe el directorio");
			mostrarError();
		}
	}
	
	//Metodo para la columna de extension
	public String mostrarExt(File fil){
		String archivo = fil.getName();
		String cad="", cadFinal="";
		char c;
		int cont=archivo.length()-1;
		if(fil.isFile()){ //Si no es un archivo, no devuelve extension
			do{ //Empezamos por el final hasta el primer punto, que sera la extension con total seguridad
				c=archivo.charAt(cont);
				cad+=c;
				cont--;
			}while(c!='.' && cont>=0);
			//Dar la vuelta a la cadena resultante
			for(int i=cad.length()-1;i>=0;i--)
				cadFinal+=cad.charAt(i);
			return cadFinal;
		}
		return "";
	}
	
	//Metodo para la columna de tamaño
	public String mostrarSize(File fil){ 
		return Long.toString(fil.length())+" bytes";
	}
	
	//Metodo para la columna de tipo (directorio o archivo)
	public String mostrarTipo(File fil){ 
		if(fil.isDirectory())
			return "Es un directorio";
		return "Es un archivo";
	}
	
	//Metodo del boton para filtrar por extension
	public void filtrarExtension(){
		FilenameFilter ext;
		File dir;
		File[] arch;
		//Si el directorio no esta vacio y se ha escrito algo en el textfield de extension...
		if(modelo.getRowCount()>0 && !(tfExt.getText().equals(""))){
			ext = new Extension(tfExt.getText()); //polimorfismo con FilenameFilter y nuestra clase Extension
			//Se crea un array con los archivos de la extension deseada
			try{
				dir = new File(explorador.ruta);
				arch = dir.listFiles(ext);
				//Se llena de nuevo la tabla con el filtro
				lbInfo.setText("Listando archivos con extension "+tfExt.getText());
				llenarTabla(arch);
			}catch(NullPointerException e){
				System.err.println("No existe la ruta");
			}catch(SecurityException e){
				System.err.println("Error de seguridad");
			}
		}
		else{
			explorador.mensaje="Sin contenido o ruta";
			mostrarError();
		}
	}
	
	//Metodo para vaciar la tabla
	public void limpiarFilas(){modelo.setRowCount(0);}
	
	//Metodo para mostrar el dialog con los distintos mensajes de error del objeto explorador
	public void mostrarError(){
		diagmsg.ponerMensaje(explorador.mensaje);
		diagmsg.setVisible(true);
		
	} 
	
	//Metodo para limpiar datos
	public void limpiar(){
		tfDir.setText("");
		tfExt.setText("");
		lbInfo.setText("Ningun directorio");
		lbNumAr.setText("0 archivos");
		modelo.setRowCount(0);
	}
}
