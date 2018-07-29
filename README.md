### How To Use
1. Add `soot/*.jar` and `poi/*.jar` to your CLASSPATH
2. Run `java lva.LvaRunner`
3. choose a java class to analysis


生成控制流图

linux:

java -cp soot-2.5.0.jar soot.tools.CFGViewer --soot-classpath .:$JAVA_HOME\jre\lib\rt.jar THE_JAVA_CLASS_TO_ANALYSIS

windows:

java -cp soot-trunk.jar soot.tools.CFGViewer --soot-classpath .;"%JAVA_HOME%"\jre\lib\rt.jar THE_JAVA_CLASS_TO_ANALYSIS

使用graphviz dot转换为图片PNG格式

dot -Tpng -o xxx.png xxx.dot  