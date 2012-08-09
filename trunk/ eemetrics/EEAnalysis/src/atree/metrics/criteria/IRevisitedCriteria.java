package atree.metrics.criteria;

import atree.treeData.Node;

public interface IRevisitedCriteria {
	public boolean isRevisited(Node parent, Node child);
	public String getInfo();
}
