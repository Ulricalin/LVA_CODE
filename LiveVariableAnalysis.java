package lva;

import java.util.*;
import soot.Local;
import soot.Unit;
import soot.ValueBox;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.scalar.BackwardFlowAnalysis;
import soot.toolkits.scalar.FlowSet;
import soot.toolkits.scalar.ArraySparseSet;

class LiveVariableAnalysis extends BackwardFlowAnalysis<Unit, FlowSet<Local>> {
	public LiveVariableAnalysis(DirectedGraph g) {
		super(g);
		doAnalysis();
	}

	@Override
	protected void merge(FlowSet<Local> src1, FlowSet<Local> src2, FlowSet<Local> dest) {
		// dest <- src1 U src2
		src1.union(src2, dest);
	}


	@Override
	protected void copy(FlowSet<Local> srcSet, FlowSet<Local> destSet) {
		srcSet.copy(destSet);
	}

	@Override
	protected FlowSet<Local> newInitialFlow() {
		return new ArraySparseSet<Local>();
	}

	@Override
	protected FlowSet<Local> entryInitialFlow() {
		return new ArraySparseSet<Local>();
	}

	@Override
	protected void flowThrough(FlowSet<Local> inSet, 
		Unit node, FlowSet<Local> outSet) {
		// 后向分析，对应于公式 in、out相反
		FlowSet defs = (FlowSet)(new ArraySparseSet<Local>());

		for (ValueBox def: node.getDefBoxes()) {
			if (def.getValue() instanceof Local) {
				defs.add((Local)def.getValue());
			}
		}
		// out <- in - def
		inSet.difference(defs, outSet);

		// out <- out ∪ use
		for (ValueBox use: node.getUseBoxes()) {
			if (use.getValue() instanceof Local) {
				outSet.add((Local) use.getValue());
			}
		}
	}
}

