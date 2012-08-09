package atree.metrics.criteria;

import atree.treeData.Node;
import atree.treeData.NodeRealValues;
import atree.util.Util;

public class RevisitedCriteriaEuclidian implements IRevisitedCriteria {
	private double maxDistance;
	public RevisitedCriteriaEuclidian(double dis) {
		maxDistance = dis;
	}
	
	@Override
	public boolean isRevisited(Node parent, Node child) {
		NodeRealValues p = (NodeRealValues) parent;
		NodeRealValues c = (NodeRealValues) child;
		if (Util.euclidian(p.getXV(), c.getXV())<maxDistance) 
		{
			//System.out.println("distance:"+Util.euclidian(p.getXV(), c.getXV()));
			return true; 
		}
		return false;
	}

	@Override
	public String getInfo() {
		return "revisited if euclidian distance $<$"+maxDistance;
	}
	
}
