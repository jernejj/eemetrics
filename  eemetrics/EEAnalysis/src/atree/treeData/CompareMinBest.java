package atree.treeData;

public class CompareMinBest implements ICompare {

	@Override
	public boolean isFirstBetter(Node a, Node b) {
		if (b==null) return true;
		if (a.getDoubleFitness()<b.getDoubleFitness()) return true;
		return false;
	}

}
