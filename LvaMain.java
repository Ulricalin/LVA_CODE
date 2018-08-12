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

	public static void main(String[] args) {
		
		//要分析的class路径
		String classPath = args[0];
		//要分析的class名
		String mainClass = args[1];
		//要分析的类别 1-analysis all method, 0-analysis one method
		Boolean type = args[2].equals("true")? true : false;
		//要分析方法名（analysis one method时有效）
		String name = args[3]+" ";

		for (int i = 4; i < args.length; i++) name+=args[i];

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

}