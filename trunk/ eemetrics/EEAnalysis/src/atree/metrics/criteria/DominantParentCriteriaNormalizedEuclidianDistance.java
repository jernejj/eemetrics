package atree.metrics.criteria;

import java.util.ArrayList;

import atree.treeData.Node;
import atree.treeData.NodeRealValues;
import atree.util.Util;

public class DominantParentCriteriaNormalizedEuclidianDistance implements IDominantParentCriteria {
	private double intervals[];
	/**
	 * This can be used only on realvalue problems (nodes are NodeRealValues)!
	 * 
	 * @param intervals positive values dimension is defined by problem 
	 */
	public DominantParentCriteriaNormalizedEuclidianDistance(double[] intervals) {
		super();
		this.intervals = intervals;
	}

	@Override
	public void setDominantParent(Node n) {
		if (n instanceof NodeRealValues) {
			NodeRealValues tmpN = (NodeRealValues) n;
			NodeRealValues tmpP,tmpDominant;
			ArrayList<Node> list = tmpN.getParents();
			tmpDominant = null;
			double dominantDistance = Double.MAX_VALUE;
			double distance;
			n.setParent(null);
			for (int i=0; i<list.size();i++) {
				tmpP = (NodeRealValues) list.get(i);
				distance = Util.euclidian_normalized(tmpN.getXV(), tmpP.getXV(), intervals);
				if (distance<dominantDistance) {
					tmpDominant = tmpP;
					dominantDistance = distance;
				}
			}
			n.setParent(tmpDominant);
		} else {
			System.err.println("Error uring DominantParentCriteriaNormalizedEuclidianDistance for non NodeRealValues!");
		}
		
	}
	@Override
	public String getInfo() {
		return "Dominant parent is set by normalized euclidian distance";
	}

}
