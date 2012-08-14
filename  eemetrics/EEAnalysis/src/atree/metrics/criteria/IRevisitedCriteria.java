package atree.metrics.criteria;

import atree.treeData.Node;

public interface IRevisitedCriteria {
	public double getDistance(Node parent, Node child);
	public boolean isDistanceInside(double d);
	public boolean isRevisited(Node parent, Node child);
	public String getInfo();
}
