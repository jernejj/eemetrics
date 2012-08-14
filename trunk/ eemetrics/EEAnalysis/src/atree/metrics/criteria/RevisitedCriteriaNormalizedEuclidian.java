package atree.metrics.criteria;

import atree.treeData.Node;
import atree.treeData.NodeRealValues;
import atree.util.Util;

public class RevisitedCriteriaNormalizedEuclidian implements IRevisitedCriteria {
	private double maxDistance;
	private double intervals[];
	private int len;
	public RevisitedCriteriaNormalizedEuclidian(double dis, double[] intervals) {
		maxDistance = dis;
		if (dis>1) {
			System.err.println("RevisitedCriteriaNormalizedEuclidian:\nThis value is normalized so it needs to be betwean [0..1]");
		}
		this.intervals = intervals;
		len = intervals.length;
	}
	
	public boolean isRevisited(NodeRealValues parent, NodeRealValues child) {
		NodeRealValues p = (NodeRealValues) parent;
		NodeRealValues c = (NodeRealValues) child;
		double r=0;
		System.out.println("TUKAJ");
	
		return true;
		
	/*	if (Util.euclidian_normalizedIsIn(p.getXV(), c.getXV(), intervals,maxDistance) )
		{
			return true; 
		}
		*/
	/*	if (Util.euclidian_normalized(p.getXV(), c.getXV(), intervals)<maxDistance) 
		{
			return true; 
		}*/
		//return false;
	}

	@Override
	public boolean isRevisited(Node parent, Node child) {
		NodeRealValues p = (NodeRealValues) parent;
		NodeRealValues c = (NodeRealValues) child;
		double r=0;
		for (int i=0; i<len; i++) {
			r+= (Math.abs(p.xv[i]-c.xv[i])/intervals[i])/len;
			if (r>maxDistance) return false;
			
		}
		return true;
		
	/*	if (Util.euclidian_normalizedIsIn(p.getXV(), c.getXV(), intervals,maxDistance) )
		{
			return true; 
		}
		*/
	/*	if (Util.euclidian_normalized(p.getXV(), c.getXV(), intervals)<maxDistance) 
		{
			return true; 
		}*/
	//	return false;
	}
	@Override
	public String getInfo() {
		return "revisited if normalized distance $<$"+maxDistance;
	}

	@Override
	public double getDistance(Node parent, Node child) {
		NodeRealValues p = (NodeRealValues) parent;
		NodeRealValues c = (NodeRealValues) child;
		return Util.euclidian_normalized(p.getXV(), c.getXV(), intervals);
	}

	@Override
	public boolean isDistanceInside(double d) {
		if (d<maxDistance) 
		{
			return true; 
		}
		return false;

	}

	
}
