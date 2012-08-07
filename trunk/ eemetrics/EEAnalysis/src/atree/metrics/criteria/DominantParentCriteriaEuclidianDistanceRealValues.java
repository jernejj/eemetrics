package atree.metrics.criteria;

import java.util.ArrayList;

import atree.treeData.Node;
import atree.treeData.NodeRealValues;
import atree.util.Util;

public class DominantParentCriteriaEuclidianDistanceRealValues implements IDominantParentCriteria {

	@Override
	public void setDominantParent(Node n) {
		if (n instanceof NodeRealValues) {
			NodeRealValues tmpN = (NodeRealValues) n;
			NodeRealValues tmpP,tmpDominant;
			ArrayList<Node> list = tmpN.getParents();
			tmpDominant = null;
			double dominantDistance = Double.MAX_VALUE;
			double euclidianDistance;
			n.setParent(null);
			for (int i=0; i<list.size();i++) {
				tmpP = (NodeRealValues) list.get(i);
				euclidianDistance = Util.euclidian(tmpN.getXV(), tmpP.getXV());
				if (euclidianDistance<dominantDistance) {
					tmpDominant = tmpP;
					dominantDistance = euclidianDistance;
				}
			}
			n.setParent(tmpDominant);
		} else {
			System.err.println("Error uring DominantParentCriteriaEuclidianDistanceRealValues for non NodeRealValues!");
		}
		
	}

}
