package atree.metrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

import atree.metrics.criteria.IDominantParentCriteria;
import atree.metrics.criteria.IEECriteria;
import atree.metrics.criteria.IRevisitedCriteria;
import atree.treeData.CompareMinBest;
import atree.treeData.ICompare;
import atree.treeData.Node;
import atree.treeData.SaveHistoryToFile;
import atree.util.MeanStDev;
import atree.util.Util;

public class ATMetrics {
	private ArrayList<Node> initTreesRootNodes;
	private ArrayList<Node> splitTrees; // only tree root nodes
	private ArrayList<Node> allNodes;
	@Deprecated
	private double x; // use criteria interfaces for this!
	private long count;
	private long differentSolutions;
    private static Stack<Node> toExamine = new Stack<Node>(); //Alex
    private static Stack<Node> toExamine2 = new Stack<Node>(); //Alex
    private SaveHistoryToFile save;
    public void setLog( SaveHistoryToFile save) {
    	this.save = save;
    }
	public long getDifferentSolutions() {
		return differentSolutions;
	}

	public void setDifferentSolutions(long differentSolutions) {
		this.differentSolutions = differentSolutions;
	}

	private IDominantParentCriteria setDominantParentCriteria; // not preset
																// value
	private IEECriteria eeCriteria; // instead of x
	private IRevisitedCriteria revisitedCriteria;
	private ICompare compare;
	private Node best;

	public ICompare getCompare() {
		return compare;
	}

	public void setCompare(ICompare compare) {
		this.compare = compare;
	}

	public Node getBest() {
		return best;
	}

	public void setBest(Node best) {
		this.best = best;
	}

	/**
	 * No nodes. Use addNode
	 */
	public ATMetrics() {
		this(new ArrayList<Node>());
	}

	/**
	 * No revisired etc calcuations were done!
	 * 
	 * @param n
	 */
	public void addNode(Node n) {
		allNodes.add(n);
		count = allNodes.size(); // need optimization!
	}
	/**
	 * Alex
	 */
	   private void nonRecursivefillRootLeafsAndCountCriteria() {
	    	int total =0;
	    	splitTrees.addAll(initTreesRootNodes);
	        for (Node n : initTreesRootNodes) {

	            n.setExploreRootSubTree(true);
	            total+= nonRecursiveFillRootTopDownCriteria(n);
	        }
	        //System.out.println("size "+total);
	        this.count = total+initTreesRootNodes.size();
	    }

	    /**
	     * Alex
	     * A stack-based summation routine that uses loop optimization like
	     * recursive implementation 1.
	     *
	     * @param root
	     * @return
	     */
	    private int nonRecursiveFillRootTopDownCriteria(Node root){
	    	int total=0;
	        toExamine.push(root);
	        
	        while (!toExamine.isEmpty()) {
	            Node node = toExamine.pop();
	            ArrayList<Node> children = node.getChildrens();

	            int size = children.size();
	            total+=size;
	            for (int j = 0; j < size; ++j) {
	                Node child = (Node) children.get(j);
	                if (eeCriteria.isExplore(root, child)) {
	                    splitTrees.add(child);
	                    child.setExploreRootSubTree(true);
	                } else {
	                    child.setExploreRootSubTree(false);
	                }
	                toExamine.push(child);
	            }
	        }
	        return total;
	    }

	public ATMetrics(ArrayList<Node> allNodes) {
		super();
		best = null;
		compare = new CompareMinBest();
		initTreesRootNodes = new ArrayList<Node>();
		splitTrees = new ArrayList<Node>();
		this.allNodes = allNodes;
		count = allNodes.size();
		differentSolutions = 0;
	}

	public ATMetrics(ArrayList<Node> allNodes,
			IDominantParentCriteria setParent, IEECriteria c,
			IRevisitedCriteria r) {
		this(allNodes);
		setRevisitedAllCriteriaApproximation(r);
		setDominantParents(setParent); // fills setDominantParents
		calculateExplore(c);
		//nonRecursivefillRootLeafsAndCountCriteria();
	}

	public String getLatexInfo() {
		StringBuffer tmp = new StringBuffer();
		tmp.append("\\begin{itemize}\n");
		tmp.append("\\item Number of nodes=").append(count).append("\n");
		tmp.append("\\item ").append(setDominantParentCriteria.getInfo())
				.append("\n");
		tmp.append("\\item ").append(eeCriteria.getInfo()).append("\n");
		tmp.append("\\item ").append(revisitedCriteria.getInfo()).append("\n");
		tmp.append("\\end{itemize}\n");
		return tmp.toString();

	}

	private void clearExploreTreeData(ArrayList<Node> allNodes) {
		for (int i = 0; i < allNodes.size(); i++) { // clear childrens, parents
			allNodes.get(i).clearExploreTreeData();
			;
		}
	}

	private void clearNodesRevisitedData(ArrayList<Node> allNodes) {
		for (int i = 0; i < allNodes.size(); i++) { // clear childrens, parents
			allNodes.get(i).clearRevisited();
		}
	}

	private void clearNodesDominant(ArrayList<Node> nodes) {
		for (int i = 0; i < nodes.size(); i++) { // clear childrens, parents
			nodes.get(i).clearAllTreeData();
			;
		}
	}

	/**
	 * Input list must be prepared, in a way that all nodes have right
	 * references This method uses constructor based Criteria interfaces.
	 * 
	 * @param list
	 * @param b 
	 */
	public void addGeneration(ArrayList<Node> list, boolean b, int g) {
	
		/*for (Node e:list) {
			if (e.getChildrens().size()>0) {
				System.out.println("new GGG?"+e);
			}
		}*/	
		setDominantParents(list);
		allNodes.addAll(list);
		count = allNodes.size(); 
		if (b) {
			clearNodesRevisitedData(list); // clear info
			setRevisitedAllCriteriaApproximation(); //recalc new values
		}

		for (Node e:list) {
			if (e.getParent()==null) { //root
				splitTrees.add(e);
				e.setExploreRootSubTree(true);
			} else {//check  {
				recursiveFillRootTopDownCriteria(e.getParent(),e);
		}
		}
		if (save!=null) {
			save.writeGeneartion(list, g, "");
		}

	}

	public void setDominantParents(IDominantParentCriteria setParent) {
		this.setDominantParentCriteria = setParent;
		setDominantParents(allNodes);
	}

	private void setDominantParents(ArrayList<Node> lista) {
		clearNodesDominant(lista);
		Node tmp;
		for (int i = 0; i < lista.size(); i++) {
			tmp = lista.get(i);
			if (compare.isFirstBetter(tmp, best))
				best = tmp; // sets best
			setDominantParentCriteria.setDominantParent(tmp);
			if (tmp.getParent() == null)
				initTreesRootNodes.add(tmp);
		}
	}

	@Deprecated
	public ATMetrics(ArrayList<Node> initTrees, final double x) {
		super();
		this.initTreesRootNodes = initTrees;
		this.x = x;
		splitTrees = new ArrayList<Node>();
		allNodes = new ArrayList<Node>();
		count = 0;
		differentSolutions = 0;
		fillRootLeafsAndCount();
		setRevisitedAll();
		eeCriteria = new IEECriteria() { // for compatibility

			@Override
			public boolean isExplore(Node parent, Node child) {
				return (child.getX() >= x);
			}

			@Override
			public String getInfo() {
				return "Explore is if x>" + x;
			}
		};
	}


	public void setRevisitedAllCriteriaApproximation(IRevisitedCriteria r) {
		revisitedCriteria = r;
		clearNodesRevisitedData(allNodes); // clear info
		setRevisitedAllCriteriaApproximation();
	}

	/**
	 * Small approximation is done! If element is revisited is not compared with
	 * others nodes! This approximation has property that real
	 * differentSolutions is <= thisApproximation. Usually number is around 1%
	 * or less.
	 * 
	 * @param r
	 */
	private void setRevisitedAllCriteriaApproximation() {
		Node tmp, tmp2;
		differentSolutions = 0;
		Collections.sort(allNodes, new Comparator<Node>() {
			@Override
			public int compare(Node a, Node b) {
				if (a.getDoubleFitness() < b.getDoubleFitness())
					return -1;
				if (a.getDoubleFitness() > b.getDoubleFitness())
					return 1;
				return 0;
			}

		});
		for (int i = 0; i < allNodes.size(); i++) {
			tmp = allNodes.get(i);
			// System.out.println(tmp.getDoubleFitness());
			if (!tmp.isRevisited()) {
				differentSolutions++;
				for (int j = i + 1; j < allNodes.size(); j++) {
					tmp2 = allNodes.get(j);
					if (!tmp2.isRevisited()) {
						if (revisitedCriteria.isRevisited(tmp, tmp2)) {
							// tmp.addRevisited(); //same solution!
							tmp2.setRevisited(true);
						}
					}
				}
			}
		}
	}

	/**
	 * This version has no approximations!
	 * 
	 * @param r
	 */
	public void setRevisitedAllCriteria(IRevisitedCriteria r) {
		revisitedCriteria = r;
		clearNodesRevisitedData(allNodes); // clear info
		Node tmp, tmp2;
		differentSolutions = 0;
		for (int i = 0; i < allNodes.size(); i++) {
			tmp = allNodes.get(i);
			if (!tmp.isRevisited()) {
				differentSolutions++;
			}
			for (int j = i + 1; j < allNodes.size(); j++) {
				tmp2 = allNodes.get(j);
				if (revisitedCriteria.isRevisited(tmp, tmp2)) {
					// tmp.addRevisited(); //same solution!
					tmp2.setRevisited(true);
				}
			}
		}
	}

	@Deprecated
	private void setRevisitedAll() {
		Node tmp, tmp2;
		int k;
		int max = allNodes.get(0).chromo.length();
		String s1;
		String s2;
		// ArrayList<Node> allNodesCopy = new ArrayList<Node>();
		for (int i = 0; i < allNodes.size(); i++) {
			tmp = allNodes.get(i);
			if (!tmp.isRevisited()) {
				differentSolutions++;
				for (int j = i + 1; j < allNodes.size(); j++) {
					// for (int j = allNodes.size()-1; j > i; j--) {
					tmp2 = allNodes.get(j);
					if (tmp.ones1 == tmp2.ones1)
						if (tmp.ones2 == tmp2.ones2)
							if (tmp.ones3 == tmp2.ones3) {
								s1 = tmp.chromo;
								s2 = tmp2.chromo;
								max = s1.length();
								if (max != s2.length()) {
									break;
								}
								for (k = 0; k < max; k++) {
									if (s1.charAt(k) != s2.charAt(k)) {
										break;
									}
								}
								if (k == max) {
									tmp.addRevisited(); // same solution!
									tmp2.setRevisited(true);
								}
							}
				}
			}
		}
	}

	public double nonRevisitedRatio() {
		// System.out.println(splitTrees.size()+"/"+count);
		return Util.divide(differentSolutions, count);
	}

	public long getCount() {
		return count;
	}

	public void calculateExplore(IEECriteria cr) {
		eeCriteria = cr; // set new criteria
		clearExploreTreeData(allNodes);
		splitTrees.clear(); // new
		splitTrees.addAll(initTreesRootNodes);
		if (splitTrees.size() == 0) {
			System.err
					.println("Did you call setDominantParents()? No initTreesRootNodes currently.");
		}
		for (Node n : initTreesRootNodes) {
			// System.out.println("Explore1:"+n);
			n.setExploreRootSubTree(true);
			for (Node e : n.getChildrens()) {
				recursiveFillRootTopDownCriteria(n, e);
			}
		}
	}

	private void recursiveFillRootTopDownCriteria(Node p, Node e) {
		if (eeCriteria.isExplore(p, e)) {
			splitTrees.add(e);
			// System.out.println("x");
			e.setExploreRootSubTree(true);
		} else {
			// System.out.println("y");
			e.setExploreRootSubTree(false);
		}
		for (Node n : e.getChildrens()) {
			recursiveFillRootTopDownCriteria(e, n);
		}
	}

	@Deprecated
	private void fillRootLeafsAndCount() {
		splitTrees.addAll(initTreesRootNodes);
		for (Node n : initTreesRootNodes) {
			count++;
			allNodes.add(n);
			for (Node e : n.getChildrens()) {
				recursiveFillRootTopDown(e);
			}
		}
	}

	@Deprecated
	private void recursiveFillRootTopDown(Node e) {
		count++;
		allNodes.add(e);
		if (e.getX() >= x) {
			e.setExploreRootSubTree(true);
			splitTrees.add(e);
		}
		for (Node n : e.getChildrens()) {
			recursiveFillRootTopDown(n);
		}
	}

	public double explorRatio() {
		// System.out.println(splitTrees.size()+"/"+count);
		return Util.divide(splitTrees.size(), count);
	}

	public double exploitRatio() {
		// System.out.println(splitTrees.size()+"/"+count);
		return (double) 1 - explorRatio();
	}

	// First parent A
	private Node getParentATree(Node e) {
		if (e.getParent() == null)
			return null; // first no parent
		Node p = e.getParent();
		while (!p.isExploreRootSubTree()) {
			p = p.getParent();
		}
		return p;
	}

	// TreeDepth A
	private int getTreeDepth(Node e) {
		int i = 0;
		do {
			Node t = getParentATree(e);
			if (t != null)
				i++;
			else
				break;
			e = t;

		} while (true);
		return i;

	}

	public MeanStDev explorGap() {
		ArrayList<Double> population = new ArrayList<Double>();
		for (Node t : splitTrees) {
			if (t.getParent() != null) {
				population.add(new Double(t.getIdGen()
						- getParentATree(t).getIdGen()));
			}
		}
		return new MeanStDev(population);
	}

	public MeanStDev explorProgressiveness() {
		ArrayList<Double> population = new ArrayList<Double>();
		for (Node t : splitTrees) {
			if (t.getParent() != null) {
				population.add(new Double(getTreeDepth(t)));
			}
		}
		return new MeanStDev(population);
	}

	public double explorType(int type) {
		long countType = 0;
		long countAllType = 0;
		for (Node t : splitTrees) {

			if (t.getParent() == null) {
				countAllType++;
				if (type == 3) { // random init tree
					countType++;
				}
				continue;// skip count for root
			}
			if (t.isC()) {
				countAllType++;
				if (type == 0) {
					countType++;
				}
			}
			if (t.isM()) {
				countAllType++;
				if (type == 1) {
					countType++;
				}
			}
			if (t.isR()) {
				countAllType++;
				if (type == 2) {
					countType++;
				}
			}
		}
		// System.out.println(countType + "/" + countAllType);
		return Util.divide(countType, countAllType);
	}

	private double countExploitType(int type, Node tree) {
		double countType = 0;
		if (!tree.isExploreRootSubTree()) { // if (tree.getX()<x)
			for (Node t : tree.getChildrens()) {
				countType += countExploitType(type, t);
			}
			if (type == 0)
				if (tree.isC())
					countType++;
			if (type == 1)
				if (tree.isM())
					countType++;
			if (type == 2)
				if (tree.isR())
					countType++;
			if (type == 4)
				if (tree.isClone())
					countType++;
		}
		return countType;
	}

	/*
	 * Skips root
	 */
	private double countExploitTypeNode(int type, Node tree) {
		double sumType = 0;
		for (Node n : tree.getChildrens()) {
			sumType += countExploitType(type, n);
		}
		return sumType;
	}

	/*
	 * Average for node
	 */
	public double exploitTypeNode(int type, Node n) {
		double summAll = 0, sumType = 0;
		summAll = countExploitTypeNode(0, n) + countExploitTypeNode(1, n)
				+ countExploitTypeNode(2, n) + countExploitTypeNode(4, n);
		sumType += countExploitTypeNode(type, n);
		return Util.divide(sumType, summAll);
	}

	/*
	 * Average for node
	 */
	public double exploitType(int type) {
		double summAll = 0, sumType = 0;
		for (Node n : splitTrees) {
			summAll += countExploitTypeNode(0, n) + countExploitTypeNode(1, n)
					+ countExploitTypeNode(2, n) + countExploitTypeNode(4, n);
			sumType += countExploitTypeNode(type, n);
		}
		return Util.divide(sumType, summAll);
	}

	public double exploitSelectionPressure() {
		int leafs = 0;
		for (Node n : splitTrees) {
			for (Node nn : n.getChildrens()) {
				leafs += countLeafs(nn);
			}
		}
		return Util.divide(leafs, count);
	}

	private int countLeafs(Node nn) {
		if (nn.isExploreRootSubTree())
			return 0; // if (nn.getX()>=x) return 0;
		if (nn.getChildrens().size() == 0) {
			return 1; // Node
		}
		int leafs = 0;
		boolean l = true; // one chiled is not leaf then it is not leaf
		for (Node n : nn.getChildrens()) {
			if (!nn.isExploreRootSubTree()) { // if (nn.getX()<x)
				l = false;
				leafs += countLeafs(n);
			}
		}
		if (l)
			leafs++;
		return leafs;
	}

	private int firstExploreParent(Node n) {
		int i = 1;
		while (n.getParent() != null) {
			n = n.getParent();
			if (n.isExploreRootSubTree()) { // if (n.getX()>=x) { explore
											// condition
				break;
			}
			i++; // one level more
		}
		return i;
	}

	public MeanStDev exploitProgressiveness() {
		ArrayList<Double> population = new ArrayList<Double>();
		for (Node t : allNodes) {
			if (!t.isExploreRootSubTree()) {// t.getX()<x //exploit condition
				population.add(new Double(firstExploreParent(t))); // TODO Very
																	// slow...
			}
		}
		return new MeanStDev(population);

	}

	/*
	 * if node nn has explore successor than it is not leaf
	 */
	private boolean isExploreLeaf(Node nn) {
		for (Node n : nn.getChildrens()) {
			if (n.isExploreRootSubTree()) { // n.getX()>=xexplore condition
				return false; // has explore leaf
			} else {
				if (!isExploreLeaf(n))
					return false;
			}
		}
		return true;
	}

	// Number of leafs in explore tree
	public double exploreSelectionPressure() {
		int leafs = 0;
		for (Node n : splitTrees) {
			//
			if (isExploreLeaf(n)) {
				leafs++;
			}
		}
		return Util.divide(leafs, splitTrees.size());
		// return 0;
	}

	public void setEECriteria(
			IEECriteria eeCriteriaNormalizedEuclidianDistance) {
		eeCriteria = eeCriteriaNormalizedEuclidianDistance;
		
	}

	public void setDominantParentCriteria(
			IDominantParentCriteria dominantParentCriteriaNormalizedEuclidianDistance) {
		setDominantParentCriteria = dominantParentCriteriaNormalizedEuclidianDistance;
		
	}

	public void setRevisitedCriteria(
			IRevisitedCriteria revisitedCriteriaNormalizedEuclidian) {
		revisitedCriteria = revisitedCriteriaNormalizedEuclidian;
		
	}

}
