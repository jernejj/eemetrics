package atree.metrics.criteria;

import atree.treeData.Node;
import atree.treeData.NodeRealValues;
import atree.util.Util;

public class EECriteriaEuclidianDistance implements IEECriteria {
	private double maxDistance;
	public EECriteriaEuclidianDistance(double dis) {
		maxDistance = dis;
	}
	@Override
	public boolean isExplore(Node parent, Node child) {
		NodeRealValues p = (NodeRealValues) parent;
		NodeRealValues c = (NodeRealValues) child;
		//System.out.println(Util.euclidian(p.getXV(), c.getXV()));
		if (Util.euclidian(p.getXV(), c.getXV())>maxDistance) return true; 
		return false;
	}
	

}
