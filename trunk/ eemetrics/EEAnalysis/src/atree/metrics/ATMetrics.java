package atree.metrics;

import java.util.ArrayList;

import atree.treeData.Node;
import atree.util.MeanStDev;


public class ATMetrics {
	private ArrayList<Node> initTrees;
	private ArrayList<Node> splitTrees; // only tree root nodes
	private ArrayList<Node> allNodes; 
	private double x;
	private long count;
	private long differentSolutions;

	
	public ATMetrics(ArrayList<Node> initTrees, double x) {
		super();
		this.initTrees = initTrees;
		this.x = x;
		splitTrees = new ArrayList<Node>();
		allNodes = new ArrayList<Node>();
		count = 0;
		differentSolutions=0;
		fillRootLeafsAndCount();
		setRevisitedAll();
	}
   
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
								for (k=0;k<max;k++) {
									if (s1.charAt(k)!=s2.charAt(k)) {
										break;
									}
								}
								if (k==max) {
									tmp.addRevisited(); //same solution!
									tmp2.setRevisited(true);	
								}
		//			  if (tmp.getChromo().equals(tmp2.getChromo())) { SLOW
		//				tmp.addRevisited(); //same solution!
		//				tmp2.setRevisited(true);
		//			  } 
					}
				}
			}
		}
	}
	public double revisitedRatio() {
		// System.out.println(splitTrees.size()+"/"+count);
		return Util.divide(differentSolutions,count);
	}
	
	public long getCount() {
		return count;
	}

	private void fillRootLeafsAndCount() {
		splitTrees.addAll(initTrees);
		for (Node n : initTrees) {
			count++;
			allNodes.add(n);
			for (Node e : n.getChildrens()) {
				recursiveFillRootTopDown(e);
			}
		}
	}

	private void recursiveFillRootTopDown(Node e) {
		count++;
		allNodes.add(e);
		if (e.getX() >= x) {
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

    // First parent A 
	private Node getParentATree(Node e) {
		if (e.getParent()==null) return null; //first no parent
		Node p=e.getParent();
		while (p.getX()<=x) {
			if (p.getParent()==null) return p; //Last is root
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
	public MeanStDev explorDynamic1() {
	  ArrayList<Double> population = new ArrayList<Double>();
		for (Node t : splitTrees) {
			if (t.getParent()!=null) {
				population.add(new Double(t.getIdGen()-getParentATree(t).getIdGen())); 
			}
		}
	  return new MeanStDev(population);
	}

	public MeanStDev explorDynamic2() {
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
		if (tree.getX()<x) {
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
    public double exploitStructure() {
    	int leafs=0;
    	for (Node n:splitTrees) {    		
        	for (Node nn:n.getChildrens()) {
        		leafs+=countLeafs(nn);
        	}
    	}
    	return Util.divide(leafs,count);
    }

	private int countLeafs(Node nn) {
		if (nn.getX()>=x) return 0;
		if (nn.getChildrens().size()==0) {
			return 1; //Node
		}
		int leafs=0;
		boolean l=true; //one chiled is not leaf then it is not leaf 
       	for (Node n:nn.getChildrens()) {
           	if (nn.getX()<x) {
           		l=false;
        		leafs+=countLeafs(n);
           	}
    	}
       	if (l) leafs++;
		return leafs;
	}
	

}
