package atree.metrics;

import java.util.ArrayList;

import atree.metrics.criteria.IDominantParentCriteria;
import atree.metrics.criteria.IEECriteria;
import atree.metrics.criteria.IRevisitedCriteria;
import atree.treeData.Node;
import atree.util.MeanStDev;
import atree.util.Util;


public class ATMetrics {
	private ArrayList<Node> initTreesRootNodes;
	private ArrayList<Node> splitTrees; // only tree root nodes
	private ArrayList<Node> allNodes; 
	@Deprecated private double x; 
	private long count;
	private long differentSolutions;
	private IDominantParentCriteria setDominantParentCriteria; //not preset value
	private IEECriteria eeCriteria; //instead of x
	private IRevisitedCriteria revisitedCriteria;
	public ATMetrics( ArrayList<Node> allNodes, IDominantParentCriteria setParent, IEECriteria c,IRevisitedCriteria r) {
		super();
		revisitedCriteria = r;
		this.setDominantParentCriteria = setParent;
		eeCriteria = c;
		initTreesRootNodes = new ArrayList<Node>();
		splitTrees = new ArrayList<Node>();
		this.allNodes = allNodes;
		count = allNodes.size();
		differentSolutions=0;
		setDominantParents();
		fillRootLeafsAndCountCriteria();
		setRevisitedAllCriteria();
	
	}
	private void setDominantParents() {
		for (int i=0; i<allNodes.size();i++) { //clear childrens, parents
			allNodes.get(i).clearTreeData();;
		}
		Node tmp;
		for (int i=0; i<allNodes.size();i++) {
			tmp = allNodes.get(i);
			setDominantParentCriteria.setDominantParent(tmp);
			if (tmp.getParent()==null) initTreesRootNodes.add(tmp);
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
		differentSolutions=0;
		fillRootLeafsAndCount();
		setRevisitedAll();
		eeCriteria = new IEECriteria() { //for compatibility
			
			@Override
			public boolean isExplore(Node parent, Node child) {
				return (child.getX()>=x);
			}
		};
	}
  

	private void setRevisitedAllCriteria() {
		Node tmp,tmp2;
		for (int i=0; i < allNodes.size(); i++) {
			tmp = allNodes.get(i);
			if (!tmp.isRevisited()) {
				differentSolutions++;
				for (int j = i+1; j < allNodes.size(); j++) {
					tmp2=allNodes.get(j);
					if (revisitedCriteria.isRevisited(tmp, tmp2)) {
							tmp.addRevisited(); //same solution!
							tmp2.setRevisited(true);	
					}
				}
			}
		}

	}
    @Deprecated
	private void setRevisitedAll() {
		Node tmp, tmp2;
		int k;
		int max=allNodes.get(0).chromo.length();
		String s1; String s2;
		//ArrayList<Node> allNodesCopy = new ArrayList<Node>(); 
		for (int i=0; i < allNodes.size(); i++) {
			tmp = allNodes.get(i);
			if (!tmp.isRevisited()) {
				differentSolutions++;
				for (int j = i+1; j < allNodes.size(); j++) {
			//	for (int j = allNodes.size()-1; j > i; j--) {
					tmp2=allNodes.get(j);
					if (tmp.ones1==tmp2.ones1) 
						if (tmp.ones2==tmp2.ones2)
							if (tmp.ones3==tmp2.ones3){
								s1=tmp.chromo;
								s2=tmp2.chromo;
								max = s1.length();
								if (max!=s2.length()) {
									break;	
								}
								for (k=0;k<max;k++) {
									if (s1.charAt(k)!=s2.charAt(k)) {
										break;
									}
								}
								if (k==max) {
									tmp.addRevisited(); //same solution!
									tmp2.setRevisited(true);	
								}
					}
				}
			}
		}
	}
	public double nonRevisitedRatio() {
		// System.out.println(splitTrees.size()+"/"+count);
		return Util.divide(differentSolutions,count);
	}
	
	public long getCount() {
		return count;
	}

	
	private void fillRootLeafsAndCountCriteria() {
		splitTrees.addAll(initTreesRootNodes);
		for (Node n : initTreesRootNodes) {
			n.setExploreRootSubTree(true);
			for (Node e : n.getChildrens()) {
				recursiveFillRootTopDownCriteria(n, e);
			}
		}
	}

	
	private void recursiveFillRootTopDownCriteria(Node p, Node e) {
		if (eeCriteria.isExplore(p,e)) {
			splitTrees.add(e);
			e.setExploreRootSubTree(true);
		} else {
			e.setExploreRootSubTree(false);
		}
		for (Node n : e.getChildrens()) {
			recursiveFillRootTopDownCriteria(e,n);
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
		return Util.divide(splitTrees.size(),count);
	}
	public double exploitRatio() {
		// System.out.println(splitTrees.size()+"/"+count);
		return (double) 1-explorRatio();
	}

/*    // First parent A 
	private Node getParentATree(Node e) {
		if (e.getParent()==null) return null; //first no parent
		Node p=e.getParent();
		while (p.getX()<=x) {
			if (p.getParent()==null) return p; //Last is root
			p=p.getParent();
		}
		return p;	
	}
	*/
	 // First parent A 
		private Node getParentATree(Node e) {
			if (e.getParent()==null) return null; //first no parent
			Node p=e.getParent();
			while (!p.isExploreRootSubTree()) {
				p=p.getParent();
			}
			return p;	
		}
    // TreeDepth A 
	private int getTreeDepth(Node e) {
		int i=0;
		do {
		  Node t = getParentATree(e);
		  if (t!=null) i++; 
		  else break;
		  e=t;
		  
		} while (true);
		return i;
		
	}
	public MeanStDev explorGap() {
	  ArrayList<Double> population = new ArrayList<Double>();
		for (Node t : splitTrees) {
			if (t.getParent()!=null) {
				population.add(new Double(t.getIdGen()-getParentATree(t).getIdGen())); 
			}
		}
	  return new MeanStDev(population);
	}

	public MeanStDev explorProgressiveness() {
		  ArrayList<Double> population = new ArrayList<Double>();
			for (Node t : splitTrees) {
				if (t.getParent()!=null) {
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
		//System.out.println(countType + "/" + countAllType);
		return Util.divide(countType,countAllType);
	}
	
	private double countExploitType(int type, Node tree) {
		double countType = 0;
		if (!tree.isExploreRootSubTree()) { //if (tree.getX()<x) 
		for (Node t : tree.getChildrens()) {
			countType+=countExploitType(type, t);
		}
		if (type == 0) if (tree.isC()) countType++;
		if (type == 1) if (tree.isM()) countType++;
		if (type == 2) if (tree.isR()) countType++;
		if (type == 4) if (tree.isClone()) countType++;
		}
		return countType;
	}
    /*
     * Skips root
     */
    private double countExploitTypeNode(int type, Node tree) {
    	double sumType=0;
		for (Node n : tree.getChildrens()) {
    	  sumType +=countExploitType(type,n);
		}
    	return sumType;
    }
    /*
     * Average for node
     */    
    public double exploitTypeNode(int type, Node n) {
    	double summAll=0, sumType=0;
    	summAll=countExploitTypeNode(0,n)+countExploitTypeNode(1,n)+countExploitTypeNode(2,n)+countExploitTypeNode(4,n);
    	sumType +=countExploitTypeNode(type,n);
    	return Util.divide(sumType,summAll);
    }

    /*
     * Average for node
     */    
    public double exploitType(int type) {
    	double summAll=0, sumType=0;
    	for (Node n:splitTrees) {
    	  summAll+=countExploitTypeNode(0,n)+countExploitTypeNode(1,n)+countExploitTypeNode(2,n)+countExploitTypeNode(4,n);
    	  sumType +=countExploitTypeNode(type,n);
    	}
    	return Util.divide(sumType,summAll);
    }
    public double exploitSelectionPressure() {
    	int leafs=0;
    	for (Node n:splitTrees) {    		
        	for (Node nn:n.getChildrens()) {
        		leafs+=countLeafs(nn);
        	}
    	}
    	return Util.divide(leafs,count);
    }

	private int countLeafs(Node nn) {
		if (nn.isExploreRootSubTree()) return 0; //if (nn.getX()>=x) return 0;
		if (nn.getChildrens().size()==0) {
			return 1; //Node
		}
		int leafs=0;
		boolean l=true; //one chiled is not leaf then it is not leaf 
       	for (Node n:nn.getChildrens()) {
           	if (!nn.isExploreRootSubTree()) { //if (nn.getX()<x)
           		l=false;
        		leafs+=countLeafs(n);
           	}
    	}
       	if (l) leafs++;
		return leafs;
	}
    
	private int firstExploreParent(Node n) {
		int i=1;
		while (n.getParent()!=null) {
			n=n.getParent();
			if (n.isExploreRootSubTree()) { //if (n.getX()>=x) {  explore condition
				break;
			}
			i++; //one level more
		}
		return i;
	}
	public MeanStDev exploitProgressiveness() {
		  ArrayList<Double> population = new ArrayList<Double>();
			for (Node t : allNodes) {
				if (!t.isExploreRootSubTree()) {//t.getX()<x //exploit condition
					population.add(new Double(firstExploreParent(t))); //TODO Very slow...
				}
			}
		  return new MeanStDev(population);

	}

	/*
	 * if node nn has explore successor than it is not leaf 
	 */
	private boolean isExploreLeaf(Node nn) {
		for (Node n:nn.getChildrens()) {
           	if (n.isExploreRootSubTree()) { //n.getX()>=xexplore condition
           		return false; //has explore leaf
           	} else {
           		if (!isExploreLeaf(n)) return false;
           	}  		
    	}
		return true;
	}
	//Number of leafs in explore tree
	public double exploreSelectionPressure() {
    	int leafs=0;
    	for (Node n:splitTrees) { 
    		//
    		if (isExploreLeaf(n)) {
        		leafs++;
    		}
    	}
    	return Util.divide(leafs,splitTrees.size());
		//return 0;
	}
	

}
