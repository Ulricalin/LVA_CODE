package lva;

import java.util.*;
import soot.*;
import soot.toolkits.graph.*;
import soot.toolkits.scalar.BackwardFlowAnalysis;
import soot.toolkits.scalar.FlowSet;

import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.util.Iterator; 
import org.apache.poi.xssf.usermodel.XSSFCell;  
import org.apache.poi.xssf.usermodel.XSSFRow;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class AnalysisTransformer extends SceneTransformer   
{
	// 1-analysis all method, 0-analysis one method
	private boolean analysis_type;

	private String methodName;

	private String output;

	public AnalysisTransformer() {
		super();
		analysis_type = true;
		methodName = "";
		output = "";
	}
	public AnalysisTransformer(boolean type, String name) {
		super();
		analysis_type = type;
		methodName = name;
	}

	public String getOutput() {
		return output;
	}

	@Override
	protected void internalTransform(String arg0, Map arg1) {

		if (analysis_type == true) {
            //遍历类中的每一个方法
        	int rowIndex = 0;
		
			// XSSFWorkbook wb = new XSSFWorkbook();  
			// XSSFSheet sheet = wb.createSheet("sheet1");
			// XSSFRow row = sheet.createRow(rowIndex++);
	  //       row.createCell(0).setCellValue("Basic Block");
	  //       row.createCell(1).setCellValue("入口处活跃变量");
	  //       row.createCell(2).setCellValue("出口处活跃变量");
	        for (SootClass sootClass : Scene.v().getApplicationClasses()){
	            for (SootMethod sootMethod : sootClass.getMethods())
	            {
	                if (!sootMethod.hasActiveBody())
	                {
	                    continue;
	                }
	 
	                //遍历方法中的每一行,检查url
	                UnitGraph graph = new BriefUnitGraph(sootMethod.getActiveBody());

					// Perform LV Analysis on the Graph
					LiveVariableAnalysis analysis = new LiveVariableAnalysis(graph);

					// Print live variables at the entry and exit of each node
					
					Iterator<Unit> unitIt = graph.iterator();

					// int rowIndex = 0;
				
					// XSSFWorkbook wb = new XSSFWorkbook();  
					// XSSFSheet sheet = wb.createSheet("sheet1");
					// XSSFRow row = sheet.createRow(rowIndex++);
			  //       row.createCell(0).setCellValue("Basic Block");
			  //       row.createCell(1).setCellValue("入口处活跃变量");
			  //       row.createCell(2).setCellValue("出口处活跃变量");
					// while (unitIt.hasNext()) {
					// 	Unit s = unitIt.next();
					// 	row = sheet.createRow(rowIndex++);
					// 	String entryVals = "";
					// 	String exitVals = "";
					// 	row.createCell(0).setCellValue(s.toString());

					// 	FlowSet<Local> set = analysis.getFlowBefore(s);

					// 	for (Local local: set) {
					// 		entryVals += local + " ";
					// 	}

					// 	set = analysis.getFlowAfter(s);
						
					// 	System.out.print("]\t[exit: ");
					// 	for (Local local: set) {
					// 		exitVals += local + " ";
					// 	}
			  //           row.createCell(1).setCellValue(entryVals);
			  //           row.createCell(2).setCellValue(exitVals);
					// }
					// try {
					// 	FileOutputStream fileOut = new FileOutputStream("./result.xlsx");  
					// 	// write this workbook to an Outputstream.  
					// 	wb.write(fileOut);  
					// 	fileOut.flush();  
					// 	fileOut.close();  
					// }
					// catch (IOException ex) {
					// 	System.out.print("IO exception occurred");
					// }
					output += sootMethod.getName() +"\n";
					while (unitIt.hasNext()) {
						Unit s = unitIt.next();

						output += s;
						
						int d = 70 - s.toString().length();
						while (d > 0) {
							output += ".";
							d--;
						}
						
						FlowSet<Local> set = analysis.getFlowBefore(s);

						output += "\t[entry: ";
						for (Local local: set) {
							output += local + " ";
						}

						set = analysis.getFlowAfter(s);
						output += "]\t[exit: ";

						for (Local local: set) {
							output += local + " ";
						}
						output += "]\n";
					}
					output += "\n\n";
				}
			}
		} else {

			int rowIndex = 0;
		
			// XSSFWorkbook wb = new XSSFWorkbook();  
			// XSSFSheet sheet = wb.createSheet("sheet1");
			// XSSFRow row = sheet.createRow(rowIndex++);
	  //       row.createCell(0).setCellValue("Basic Block");
	  //       row.createCell(1).setCellValue("入口处活跃变量");
	  //       row.createCell(2).setCellValue("出口处活跃变量");

			for (SootClass sootClass : Scene.v().getApplicationClasses()){
				// 我们首先获取Main方法，因为我们的分析应当从Main方法开始
				SootMethod sMethod = sootClass.getMethodByName(methodName);

				// 获取当前Main方法中ActiveBody
				// ActiveBody: The body of a method contains the statements inside that method as well as 
				// the `local variable` definitions and the exception handlers.
				UnitGraph graph = new BriefUnitGraph(sMethod.getActiveBody());

				// 执行活跃变量分析
				LiveVariableAnalysis analysis = new LiveVariableAnalysis(graph);

				
				Iterator<Unit> unitIt = graph.iterator();
				output += sMethod.getName() +"\n";
				while (unitIt.hasNext()) {
					Unit s = unitIt.next();

					output += s;
					
					int d = 70 - s.toString().length();
					while (d > 0) {
						output += ".";
						d--;
					}
					
					FlowSet<Local> set = analysis.getFlowBefore(s);

					output += "\t[entry: ";
					for (Local local: set) {
						output += local + " ";
					}

					set = analysis.getFlowAfter(s);
					output += "]\t[exit: ";
					//System.out.print("]\t[exit: ");
					for (Local local: set) {
						output += local + " ";
					}
					output += "]\n";
				}
				output += "\n\n";
				// while (unitIt.hasNext()) {
				// 	Unit s = unitIt.next();
				// 	row = sheet.createRow(rowIndex++);
				// 	String entryVals = "";
				// 	String exitVals = "";
				// 	row.createCell(0).setCellValue(s.toString());

				// 	FlowSet<Local> set = analysis.getFlowBefore(s);

				// 	for (Local local: set) {
				// 		entryVals += local + " ";
				// 	}

				// 	set = analysis.getFlowAfter(s);
					
				// 	System.out.print("]\t[exit: ");
				// 	for (Local local: set) {
				// 		exitVals += local + " ";
				// 	}
		  //           row.createCell(1).setCellValue(entryVals);
		  //           row.createCell(2).setCellValue(exitVals);
				// }
				// try {
				// 	FileOutputStream fileOut = new FileOutputStream("./result.xlsx");  
				// 	// write this workbook to an Outputstream.  
				// 	wb.write(fileOut);  
				// 	fileOut.flush();  
				// 	fileOut.close();  
				// }
				// catch (IOException ex) {
				// 	System.out.print("IO exception occurred");
				// }
			}
		}

	}
}