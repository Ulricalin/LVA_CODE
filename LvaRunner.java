package lva;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
 
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
 
import java.awt.Color;
import java.io.File;

public class LvaRunner extends JFrame implements ActionListener{
	JButton jb = new JButton("打开文件");
	public static void main(String[] args) {

		new LvaRunner();
	}
	public LvaRunner(){
		jb.setActionCommand("open");
		jb.setBackground(Color.GREEN);
		this.getContentPane().add(jb, BorderLayout.SOUTH);

		jb.addActionListener(this);
		this.setTitle("活跃变量");
		this.setSize(333, 288);
		this.setLocation(200,200);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("open")){
			JFileChooser jf = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Class文件(*.class)", "class");
			jf.setFileFilter(filter);
			jf.showOpenDialog(this);
			File f =  jf.getSelectedFile();
			String s = f.getAbsolutePath();
			String os = System.getProperty("os.name").toLowerCase();
			String classPath;
			if (os.startsWith("win")) {
				classPath = s.substring(0, s.lastIndexOf("\\"));
			} else {
				classPath = s.substring(0, s.lastIndexOf("/"));
			}
			String mainClass = f.getName();
			mainClass = mainClass.substring(0, mainClass.lastIndexOf("."));
			JOptionPane.showMessageDialog(this, classPath, "活跃变量",JOptionPane.WARNING_MESSAGE); 

			new LvaMain(classPath,mainClass,true,"fun1");
		}
	}
}