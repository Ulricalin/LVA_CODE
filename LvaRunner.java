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
 import javax.swing.*;
import java.awt.Color;
import java.io.File;

public class LvaRunner extends JFrame implements ActionListener{
	JFrame f1 = new JFrame();
	JFrame f2 = new JFrame();
	JButton jb = new JButton("打开文件");
	JButton start = new JButton("start");
	JRadioButton radioBtn01 = new JRadioButton("analysis all method");
    JRadioButton radioBtn02 = new JRadioButton("analysis one method");
    JTextField methodNameText = new JTextField(20);
    ButtonGroup btnGroup = new ButtonGroup();
    String classPath;
    String mainClass;
	public static void main(String[] args) {

		new LvaRunner();
	}
	public LvaRunner(){

		f1.setTitle("活跃变量");
		f1.setSize(333, 288);
		f1.setLocation(200,200);

		f1.setVisible(true);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jb.setActionCommand("open");
		jb.setBackground(Color.GREEN);
		f1.getContentPane().add(jb, BorderLayout.SOUTH);

		jb.addActionListener(this);



		f2.setTitle("活跃变量");
		f2.setSize(333, 288);
		f2.setLocation(200,200);
		//f2.setVisible(true);
		f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		JPanel panel = new JPanel();    
        // 添加面板
        f2.add(panel);
		// 创建按钮组，把两个单选按钮添加到该组
        btnGroup.add(radioBtn01);
        btnGroup.add(radioBtn02);

        // 设置第一个单选按钮选中
        radioBtn01.setSelected(true);

        panel.add(radioBtn01);
        panel.add(radioBtn02);

        methodNameText.setBounds(100,20,165,25);
        methodNameText.setEditable(false);
        panel.add(methodNameText);

        radioBtn01.addActionListener(this);
        radioBtn02.addActionListener(this);

        start.setActionCommand("start");
        start.setBackground(Color.GREEN);
        panel.add(start, BorderLayout.SOUTH);
        start.addActionListener(this);
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
			//String classPath;
			if (os.startsWith("win")) {
				classPath = s.substring(0, s.lastIndexOf("\\"));
			} else {
				classPath = s.substring(0, s.lastIndexOf("/"));
			}
			mainClass = f.getName();
			mainClass = mainClass.substring(0, mainClass.lastIndexOf("."));
			JOptionPane.showMessageDialog(this, classPath, "活跃变量",JOptionPane.WARNING_MESSAGE); 

			//new LvaMain(classPath,mainClass,true,"fun1");
			f2.setVisible(true);
			f1.setVisible(false);
		} 
		else if (e.getActionCommand().equals("start")){
			if (radioBtn01.isSelected()) {
				new LvaMain(classPath,mainClass,true,"");
			} else {
				new LvaMain(classPath,mainClass,false,methodNameText.getText());
			}
			f1.setVisible(true);
			f2.setVisible(false);
		}
		else if (e.getSource()==radioBtn01) {
            methodNameText.setEditable(false);
        }
        else if(e.getSource()==radioBtn02){
            methodNameText.setEditable(true);
        }
	}
}