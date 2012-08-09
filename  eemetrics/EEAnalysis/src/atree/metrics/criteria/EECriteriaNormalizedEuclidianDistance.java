package atree.metrics.criteria;

import atree.treeData.Node;
import atree.treeData.NodeRealValues;
import atree.util.Util;

public class EECriteriaNormalizedEuclidianDistance implements IEECriteria {
	private double maxDistance;
	private double intervals[];
	
	public EECriteriaNormalizedEuclidianDistance(double dis, double[] intervals) {
		maxDistance = dis;
		if (dis>1) {
			System.err.println("EECriteriaNormalizedEuclidianDistance:\nThis value is normalized so it needs to be betwean [0..1]");
		}
		this.intervals = intervals;
	}
	
	@Override
	public boolean isExplore(Node parent, Node child) {
		NodeRealValues p = (NodeRealValues) parent;
		NodeRealValues c = (NodeRealValues) child;
		if (Util.euclidian_normalized(p.getXV(), c.getXV(),intervals)>maxDistance) return true; 
		return false;
	}
	@Override
	public String getInfo() {
		return "explore if normalized distance $>$"+maxDistance;
	}
	

}
