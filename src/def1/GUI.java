package def1;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI {
	static JMenuBar menu=new JMenuBar();
	static String filename="";
	static File file;
	static JTextArea fname=new JTextArea();
	static JTextArea content=new JTextArea();
	static JLabel sep0=new JLabel("Enter your file name:");
	static JLabel sep2=new JLabel("File content:");
	static JFrame frame = new JFrame("def1");
	
	static void paint(){
		//frame
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	  //menu
	    JMenu fmenu=new JMenu("File");
	    menu.add(fmenu);
	    JMenuItem add = new JMenuItem("Create file");
	    JMenuItem edit = new JMenuItem("Edit");
	    JMenuItem delete = new JMenuItem("Delete");
	    JMenuItem exit = new JMenuItem("Exit");
	    fmenu.add(add);
	    fmenu.add(edit);
	    fmenu.add(delete);
	    fmenu.add(exit);
	    
	    JMenuItem button=new JMenuItem("Confirm");
	    menu.add(button);
	    
	    //adding components
	    addComps(frame);
	    
	    //Display the window.
	    frame.pack();
	    frame.setVisible(true);
	    
	    //ADD listener
	    add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				filename=fname.getText();
				file=new File("C:/root/"+Main.ulogin+"/"+filename);
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Main.writeLog(Main.ulogin+" created a file "+filename+" at "+Main.date.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	    });
	    
	    //EDIT listener
	    edit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(file==null){
					filename=fname.getText();
					file=new File("C:/root/"+Main.ulogin+"/"+filename);
				}
				content.setText("");
				if(file.exists()){
					//name.setText(file.getName());
					Scanner scanner=null;
					try {
						scanner=new Scanner(file);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					String s=null;
					if(scanner.hasNext()) s=scanner.nextLine();
					scanner.close();
					if(s!=null) content.setText(content.getText()+s);
				}
				try {
					Main.writeLog(Main.ulogin+" changed "+filename+" file content at "+Main.date.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	    });
	    
	    //DELETE listener
	    delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(file==null){
					filename=fname.getText();
					file=new File("C:/root/"+Main.ulogin+"/"+filename);
				}
				if(file.exists()) file.delete();
			}
	    });
	    
	    //CONFIRM listener
	    button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(file.exists()){
					PrintWriter writer=null;
					try {
						writer = new PrintWriter(file);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					
						writer.print("");				
						writer.print(content.getText());
					
					writer.close();
				}
				try {
					Main.writeLog(Main.ulogin+" deleted a file "+filename+" at "+Main.date.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
	    });
	    
	    //EXIT listener
	    exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Main.writeLog(Main.ulogin+" signed out at "+Main.date.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);				
			}
	    });
	}

	private static void addComps(final Container pane) {
		final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2,2));
         
        //Add buttons to experiment with Grid Layout
        panel.add(menu);
        panel.add(sep0);
        panel.add(fname);
        panel.add(sep2);
        panel.add(content);     
         
        //Process the Apply gaps button press
        pane.add(panel);
	}
}
