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
import javax.swing.JScrollPane;
import javax.swing.*;
import java.awt.Color;
import java.io.File;
import java.io.BufferedReader;  
import java.io.InputStreamReader;
import java.io.BufferedInputStream; 
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.lang.ProcessBuilder;
public class LvaRunner extends JFrame implements ActionListener{
	JFrame f1 = new JFrame();
	JFrame f2 = new JFrame();
	JButton jb = new JButton("打开文件");
	//start analysis
	JButton start = new JButton("start");
	//return f1 to choose file
	JButton back = new JButton("back");
	//get the cfg
	JButton cfg = new JButton("getCFG");
	//save
	JButton save = new JButton("save");
	JRadioButton radioBtn01 = new JRadioButton("analysis all method");
    JRadioButton radioBtn02 = new JRadioButton("analysis one method");
    ButtonGroup btnGroup = new ButtonGroup();

    JTextField methodNameText = new JTextField(20);
    //show the java source file
    JTextArea javaTxt;
    //show the lva result
    JTextArea result;
    //the path of file(java class) to analysis
    String classPath;
    //the java class's name
    String mainClass;
    //the lva result
    String output;
    JSplitPane jSplitPane1 =new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	JSplitPane jSplitPane2 =new JSplitPane();

	public static void main(String[] args) {

		new LvaRunner();
	}
	public LvaRunner(){
		javaTxt = new JTextArea();
		result = new JTextArea();
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
		f2.setSize(1280,1024);
		f2.setLocation(200,200);
		//f2.setVisible(true);
		f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		JPanel panel1 = new JPanel();

		back.setActionCommand("back");
        back.setBackground(Color.BLUE);
		panel1.add(back);
        back.addActionListener(this);

		//panel.setLayout(new BorderLayout());
        // 添加面板
        //f2.add(panel);
		// 创建按钮组，把两个单选按钮添加到该组
        btnGroup.add(radioBtn01);
        btnGroup.add(radioBtn02);

        // 设置第一个单选按钮选中
        radioBtn01.setSelected(true);

        panel1.add(radioBtn01,BorderLayout.NORTH);
        panel1.add(radioBtn02,BorderLayout.NORTH);

        methodNameText.setBounds(100,20,165,25);
        methodNameText.setEditable(false);
        panel1.add(methodNameText,BorderLayout.NORTH);

        radioBtn01.addActionListener(this);
        radioBtn02.addActionListener(this);

        start.setActionCommand("start");
        start.setBackground(Color.GREEN);
        panel1.add(start);
        start.addActionListener(this);

        

        cfg.setActionCommand("cfg");
        cfg.setBackground(Color.YELLOW);
		panel1.add(cfg);
        cfg.addActionListener(this);

        save.setActionCommand("save");
        save.setBackground(Color.PINK);
		panel1.add(save);
        save.addActionListener(this);
        save.setEnabled(false);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.add(new JScrollPane(javaTxt),BorderLayout.CENTER);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        panel3.add(new JScrollPane(result),BorderLayout.CENTER);


        jSplitPane1.setTopComponent(panel1);//布局中添加组件 ，面板1
        jSplitPane1.setBottomComponent(jSplitPane2);//添加面板2
        jSplitPane2.setLeftComponent(panel2);
        jSplitPane2.setRightComponent(panel3);
        jSplitPane2.setDividerLocation(600);
        f2.setContentPane(jSplitPane1);
	}
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("open")){
			JFileChooser jf = new JFileChooser();
			jf.setCurrentDirectory(new File("."));
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
			//JOptionPane.showMessageDialog(this, classPath, "活跃变量",JOptionPane.WARNING_MESSAGE); 
			showJavaTxt();
			//new LvaMain(classPath,mainClass,true,"fun1");
			f2.setVisible(true);
			f1.setVisible(false);
		} 
		else if (e.getActionCommand().equals("start")){
			Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象 
			String cmd = "java lva.LvaMain ";
			if (radioBtn01.isSelected()) {
				cmd += classPath+" "+mainClass+" true "+"all";
			} else {
				if (methodNameText.getText().equals("")) {
					System.out.print("no method input!\n");
					return;
				}
				cmd += classPath+" "+mainClass+" false "+ methodNameText.getText();
			}
			
			output = "";
	        try {  
	            Process p = run.exec(cmd);// 启动另一个进程来执行命令  
	            BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
	            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
	            String lineStr;  
	            while ((lineStr = inBr.readLine()) != null)  
	                //获得命令执行后在控制台的输出信息  
	                output += lineStr+"\n";
	            //检查命令是否执行失败。  
	            if (p.waitFor() != 0) {  
	                if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束  
	                    System.err.println("命令执行失败!");  
	            }  
	            inBr.close();  
	            in.close();  
	        } catch (Exception e1) {  
	            e1.printStackTrace();  
	        } 
			// LvaMain lva;
			// if (radioBtn01.isSelected()) {
			// 	lva = new LvaMain(classPath,mainClass,true,"");
			// } else {
			// 	lva = new LvaMain(classPath,mainClass,false,methodNameText.getText());
			// }
			// output = lva.getOutput();
			result.setText(output);
			save.setEnabled(true);
			//f1.setVisible(true);
			//f2.setVisible(false);
		}
		else if (e.getActionCommand().equals("back")){
			f1.setVisible(true);
			f2.setVisible(false);
		}
		else if (e.getActionCommand().equals("cfg")){
			Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象 
			String cmd = "java soot.tools.CFGViewer -cp " + classPath + " -pp " +  mainClass + " -d sootOutput/" + mainClass;
	        try {  
	            Process p = run.exec(cmd);// 启动另一个进程来执行命令  
	            BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
	            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
	            String lineStr;  
	            while ((lineStr = inBr.readLine()) != null)  
	                //获得命令执行后在控制台的输出信息  
	                System.out.println(lineStr);// 打印输出信息  
	            //检查命令是否执行失败。  
	            if (p.waitFor() != 0) {  
	                if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束  
	                    System.err.println("命令执行失败!");  
	            }  
	            inBr.close();  
	            in.close();  
	        } catch (Exception e1) {  
	            e1.printStackTrace();  
	        } 

	        //String path = classPath
	        cmd = "find" + " ./sootOutput/" + mainClass + " -type f -name \"* *\" -print "
	         		+ "| "
					+ "while read name; do\n"
					+ "na=$(echo $name | tr ' ' '_')\n"
					+ "mv \"$name\" \"$na\"\n"
					+ "done\n";
			//System.out.println(cmd);
	        try { 
	        	List<String> cmds = new ArrayList<String>();
				cmds.add("sh");
				cmds.add("-c");
				cmds.add(cmd);
				ProcessBuilder pb =new ProcessBuilder(cmds);
				Process p = pb.start();
	            //Process p = run.exec(new String[] {"/bin/sh", "-c", cmd});// 启动另一个进程来执行命令  
	            BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
	            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
	            String lineStr; 
	            while ((lineStr = inBr.readLine()) != null) {

	                System.out.println(lineStr);// 打印输出信息  
	            }

	            //检查命令是否执行失败。  
	            if (p.waitFor() != 0) {  
	                if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束  
	                    System.err.println("命令执行失败!");  
	                 
	            }  
	            inBr.close();  
	            in.close();  
	        } catch (Exception e1) {  
	            e1.printStackTrace();  
	        }
	        cmd = "find" + " ./sootOutput/" + mainClass + " -type f -name \"*.dot\" -print "
	         		+ "| "
					+ "while read name; do\n"
					+ "na=$(echo $name\".png\")\n"
					+ "dot -Tpng -o $na $name\n"
					+ "eog $na\n"
					+ "done\n";
			//System.out.println(cmd);
	        try { 
	        	List<String> cmds = new ArrayList<String>();
				cmds.add("sh");
				cmds.add("-c");
				cmds.add(cmd);
				ProcessBuilder pb =new ProcessBuilder(cmds);
				Process p = pb.start();
	            //Process p = run.exec(new String[] {"/bin/sh", "-c", cmd});// 启动另一个进程来执行命令  
	            BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
	            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));  
	            String lineStr; 
	            while ((lineStr = inBr.readLine()) != null) {

	                System.out.println(lineStr);// 打印输出信息  
	            }

	            //检查命令是否执行失败。  
	            if (p.waitFor() != 0) {  
	                if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束  
	                    System.err.println("命令执行失败!");  
	                
	            }  
	            inBr.close();  
	            in.close();  
	        } catch (Exception e1) {  
	            e1.printStackTrace();  
	        }
		}
		else if (e.getActionCommand().equals("save")) {
			//弹出文件选择框
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(classPath));
			//后缀名过滤器
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "文本文件(*.txt)", "txt");
			chooser.setFileFilter(filter);
			//下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
			int option = chooser.showSaveDialog(null);
			if(option==JFileChooser.APPROVE_OPTION){	//假如用户选择了保存
				// File file = new File(mainClass+".txt");
				// chooser.setSelectedFile(file);
				File file = chooser.getSelectedFile();
			
				String fname = chooser.getName(file);	//从文件名输入框中获取文件名
				
				//假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
				if(fname.indexOf(".txt")==-1){
					file=new File(chooser.getCurrentDirectory(),fname+".txt");
				}
				try {
					FileOutputStream fos = new FileOutputStream(file);
					
					//写文件操作……
					fos.write(result.getText().getBytes());
					fos.close();
					
				} catch (IOException e1) {
					System.err.println("IO异常");
					e1.printStackTrace();
				}
			}
		}
		else if (e.getSource()==radioBtn01) {
            methodNameText.setEditable(false);
        }
        else if(e.getSource()==radioBtn02){
            methodNameText.setEditable(true);
        } 
	}

	public void showJavaTxt() {
		BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(classPath+'/'+mainClass+".java")));
            StringBuffer sbuf = new StringBuffer();
            String hasRead = null;
            while ((hasRead = br.readLine()) != null) {
                sbuf.append(hasRead + "\n");
            }
            javaTxt.setText(sbuf.toString());
        } catch (Exception e1) {
            // 当设置的路径下没有文件的时候提示用户
            javaTxt.setText("请确认文件路径:" + classPath + "有文件？？");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
	}
}