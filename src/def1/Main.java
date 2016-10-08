package def1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Main extends JFrame{
	static String ulogin, password;
	static File file = new File("access.xml");
	static Date date=new Date();
	static File logfile=new File("log.txt");
	
	public static void main(String[] args) {
		signIn();
	}
	
	
	public static void signIn(){
		final Main m=new Main();
		final JPanel login=new JPanel();
		
		final JTextField name=new JTextField(28);
		final JPasswordField pass=new JPasswordField(28);
		final JTextField captcha=new JTextField(28);
		
		name.setToolTipText("Name");
		pass.setToolTipText("Password");
		captcha.setToolTipText("Captcha");
		
		JButton ok=new JButton("OK");
		
		login.add(new JLabel("Name:"));
		login.add(name);
		login.add(new JLabel("Password:"));
		login.add(pass);
		final int c1=(int) (Math.random()*10);
		final int c2=(int) (Math.random()*10);
		login.add(new JLabel("Captcha:    "+c1+"+"+c2));
		login.add(captcha);
		
		login.add(ok);
		m.setContentPane(login);
		
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setBounds(200,90,350,250);
		m.setResizable(false);
		m.setVisible(true);
		
		ok.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ulogin=name.getText();
				int capt=Integer.parseInt(captcha.getText());
				String []arr;
				boolean access=false;
				
				Scanner scanner=null;
				try {
					scanner=new Scanner(file);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				String s=null;
				
				while(scanner.hasNext()){
					s=scanner.nextLine();
					arr = s.split(" "); 
					
					if(ulogin.equals(arr[0]) && pass.getText().equals(arr[1]) && capt==c1+c2){
						File directory=new File("C:/root/"+ulogin);
						access=true;
						if(!directory.exists())	
							directory.mkdir();
						try {
							Runtime.getRuntime().exec("attrib +H "+"C:/root/"+ulogin);
						} catch (IOException e) {
							e.printStackTrace();
						}
						try {
							writeLog(ulogin+" signed in at "+date.toString());
						} catch (IOException e) {
							e.printStackTrace();
						}
						m.dispose();
						GUI.paint();
					}
				}
				if(access==false){
					scanner.close();
					JOptionPane.showMessageDialog(GUI.frame, "Authentication failed", "ERROR", JOptionPane.ERROR_MESSAGE);
					try {
						writeLog("An attempt of unauthorised access at "+date.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					System.exit(0);	
				}
				scanner.close();
			}
		});
	}
	
	
	public static void writeLog(String s) throws IOException{
		PrintWriter writer=null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(logfile, true)));
		}catch (IOException e) {
			e.printStackTrace();
		}			
		writer.println(s);
		writer.close();
	}
}
