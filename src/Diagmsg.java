import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Diagmsg extends JDialog {
	JLabel lbMsg;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Diagmsg dialog = new Diagmsg();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Diagmsg() {
		setTitle("Mensaje de error");
		setBounds(100, 100, 424, 195);
		getContentPane().setLayout(null);
		
		lbMsg = new JLabel("");
		lbMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lbMsg.setFont(new Font("Arial", Font.BOLD, 17));
		lbMsg.setForeground(Color.BLUE);
		lbMsg.setBounds(10, 29, 373, 54);
		getContentPane().add(lbMsg);
		
		JButton bAceptar = new JButton("Aceptar");
		bAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cerrarDiag();
			}
		});
		bAceptar.setFont(new Font("Arial", Font.BOLD, 15));
		bAceptar.setBounds(149, 95, 126, 34);
		getContentPane().add(bAceptar);

	}

	public void ponerMensaje(String cadena){
		lbMsg.setText(cadena); //para cambiar el mensaje de error segun error
	} 
	
	public void cerrarDiag(){this.setVisible(false);}

}
