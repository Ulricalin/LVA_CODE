package lva;

import java.util.Iterator;
import java.util.List;
import soot.*;
import soot.Body;
import soot.NormalUnitPrinter;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.UnitPrinter;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.jimple.internal.*;
import java.util.*;
import java.io.*; 




public class LvaMain
{
	// private AnalysisTransformer analysisTransformer;

	// public LvaMain() {

	// }
	public static void main(String[] args) {
		
		// if (args.length == 0) {
		// 	System.out.println("Usage: java RunDataFlowAnalysis class_to_analyse");
		// 	System.exit(1);
		// } else {
		// 	System.out.println("Analyzing class: "+args[0]);
		// }

		String mainClass = args[1];

		// You may have to update the class Path based on your OS and Java version 
		/*** *** YOU SHOULD EDIT THIS BEFORE RUNNING *** ***/
		String classPath = args[0]; 			//change to appropriate path to the test class
															// if needed add path to rt.jar (or classes.jar)

		Boolean type = args[2].equals("true")? true : false;

		String name = args[3];

		String[] sootArgs = {
			"-cp", classPath, "-pp",
			"-w", 						// 执行整个程序分析
			"-src-prec", "java",		// 指定源文件类型
			"-main-class", mainClass,	// 指定主类 
			"-f", "J", 					// 指定输出文件类型
			mainClass 
		};

		AnalysisTransformer analysisTransformer = new AnalysisTransformer(type,name);

		PackManager.v().getPack("wjtp").add(new Transform("wjtp.dfa", analysisTransformer));

		// 调用sootMain
		soot.Main.main(sootArgs);

		System.out.print(analysisTransformer.getOutput());
	}
// 	public LvaMain(String classPath,String mainClass,Boolean type, String name)   {
		
// // 		if (args.length < 2) {
// // 			System.out.println("Usage: java lva.LvaMain class_to_analyse class_path");
// // 			System.exit(1);
// // 		} else {
// // 			System.out.println("Analyzing class: "+args[0]);
// // 		}

// 		// String mainClass = args[0];


// 		// String classPath = args[1]; // 此处的路径是你待分析的类所在的路径
		

// 		//Set up arguments for Soot
// 		String[] sootArgs = {
// 			"-cp", classPath, "-pp",
// 			"-w", 						// 执行整个程序分析
// 			"-src-prec", "java",		// 指定源文件类型
// 			"-main-class", mainClass,	// 指定主类 
// 			"-f", "J", 					// 指定输出文件类型
// 			mainClass 
// 		};

// 		analysisTransformer = new AnalysisTransformer(type,name);

// 		PackManager.v().getPack("wjtp").add(new Transform("wjtp.dfa", analysisTransformer));

// 		// 调用sootMain
// 		soot.Main.main(sootArgs);

// 	}

// 	public String getOutput() {
// 		return analysisTransformer.getOutput();
// 	}
}