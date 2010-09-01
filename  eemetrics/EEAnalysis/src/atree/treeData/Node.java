package atree.treeData;

import java.util.ArrayList;

public class Node implements Comparable<Node> {
	private Node parent;
	private ArrayList<Node> childrens;
	private long revisits;
	private long idGen;
	private long idInPop;
	public String chromo; //just for fast compare!!!
	public int ones1, ones2, ones3; //just for comparing speed
	private boolean m; 
	private boolean c; 
	private boolean r;
	private boolean pareto;
	private boolean revisited;
	public boolean isRevisited() {
		return revisited;
	}
	public void setRevisited(boolean revisited) {
		this.revisited = revisited;
	}
	public boolean isTmp() {
		return tmp;
	}
	public void setTmp(boolean tmp) {
		this.tmp = tmp;
	}

	private boolean tmp;

	private long x;

	public Node() {
		super();
		childrens = new ArrayList<Node>();
		revisits = 0;
		parent = null;
		pareto = false;
		tmp=false;
	}
	public void addRevisited()  {
		revisits++;
	}
	public boolean isPareto() {
		return pareto;
	}
	public void setPareto(boolean pareto) {
		this.pareto = pareto;
	}
	public Node(long idGen, long idInPop, String chromo, boolean m, boolean c,
			boolean r, long x) {
		this();
		this.idGen = idGen;
		this.idInPop = idInPop;
		this.m = m;
		this.c = c;
		this.r = r;
		this.x = x;
		setChromo(chromo);
	}


	public Node getParent() {
		return parent;
	}
	public void addChild(Node c) {
		childrens.add(c);
	}
	public void setParent(Node parent) {
		this.parent = parent;
		parent.addChild(this);
	}
	public ArrayList<Node> getChildrens() {
		return childrens;
	}
	public void setChildrens(ArrayList<Node> childrens) {
		this.childrens = childrens;
	}
	public long getIdGen() {
		return idGen;
	}
	public void setIdGen(long idGen) {
		this.idGen = idGen;
	}
	public long getIdInPop() {
		return idInPop;
	}
	public void setIdInPop(long idInPop) {
		this.idInPop = idInPop;
	}
	public String getChromo() {
		return chromo;
	}
	public void setChromo(String chromo) {
		this.chromo = chromo;
		ones1=0; //just for fast compare!!!
		ones2=0;		
		ones3=0;
		for (int i=2; i<chromo.length();i=i+3) { //can skip 2 elements!!!
			 if (chromo.charAt(i-2)=='1') ones1++;
			 if (chromo.charAt(i-1)=='1') ones2++;
			 if (chromo.charAt(i)=='1') ones3++;
		}
	}
	public boolean isM() {
		return m;
	}
	public void setM(boolean m) {
		this.m = m;
	}
	public boolean isC() {
		return c;
	}
	public void setC(boolean c) {
		this.c = c;
	}
	public boolean isLeaf() {
		if (childrens.size()==0) return true;
		return false;
	}
	public boolean isClone() {
		return !(isM()||isR()||isC());
	}
	public boolean isR() {
		return r;
	}
	public void setR(boolean r) {
		this.r = r;
	}
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}
	public boolean isRnd() {
		return 	(parent==null);
	}
	@Override
	public int compareTo(Node o) {
        if ((o.idGen==idGen) && (o.idInPop == idInPop)) return 0;
        if ((o.idGen>idGen)) return -1;
        if ((o.idGen==idGen) && (o.idInPop > idInPop)) return -1;
        return 1;
	}
	public String getID() {
		return "("+getIdGen()+","+getIdInPop()+")";
	}
	public String getID(Node n) {
		if (n==null) return "(-1,-1)";
		return n.getID();
	}
	public String getC() {
		if (isC()) return "c";
		return "";
	}
	public String getM() {
		if (isM()) return "m";
		return "";
	}
	public String getR() {
		if (isR()) return "r";
		return "";
	}
	public String getCMR() {
		return getC()+getM()+getR();
	}
	public String printChildrens() {
		StringBuffer sb= new StringBuffer();
		for (Node t:childrens) {
			sb.append(t.getID()).append(" ");
		}
		return sb.toString();
	}
	public String toString() {
		String t=getID()+" "+getID(parent)+" "+getChromo()+" "+getCMR()+" "+getX()+" "+printChildrens()+" "+isPareto()+" "+isTmp();
		return t;
	}
	
	//Example: (-1,-1)(-1,-1)(5,0) 0000111010 0 0 0 0
	public static Node convertBarbaraFormat(String line, Nodes all) {
		String stepOne[] = line.split(" ");
		stepOne[0] = stepOne[0].replaceAll("\\(", "");
		String stepTwo[] = stepOne[0].trim().split("\\)");
		Node r = new Node();
		String stepThree[] = stepTwo[2].trim().split(",");
		r.setIdInPop(Long.parseLong(stepThree[0]));
		r.setIdGen(Long.parseLong(stepThree[1]));
		r.setChromo(stepOne[1].trim());
		r.setC(stepOne[2].trim().equals("1"));
		r.setM(stepOne[3].trim().equals("1"));
		r.setR(stepOne[4].trim().equals("1"));
		r.setX(Long.parseLong(stepOne[5].trim()));
		//set parent
		stepThree = stepTwo[0].trim().split(",");
		String key= "("+stepThree[1]+","+stepThree[0]+")";
		if (all.containsKey(key)) r.setParent(all.get(key));
		return r;
	}
	
}
