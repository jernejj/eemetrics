package atree.metrics.criteria;

import atree.treeData.Node;

public interface IEECriteria {
	public boolean isExplore(Node parent, Node child);
	public String getInfo();
}
