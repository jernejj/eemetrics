package atree.treeData;

import java.util.Arrays;

/**
 * This class is used for real vector based individuals
 * 
 * @author matej
 *
 */
public class NodeRealValues extends Node {
	transient public double tmp; 
public double xv[]; //x values
	
	public NodeRealValues() {
		super();
	}
	
	public NodeRealValues(int iPop, double[] genes, int mGeneration,
			boolean spwnMutate, boolean spwnCross, boolean spwnRepair) {
		setIdGen(mGeneration);
		setIdInPop(iPop);
		setChromo(genes);
		xv = genes;
		setM(spwnMutate);
		setC(spwnCross);
		setR(spwnRepair);
	}

	public double[] getXV() {
		return xv;
	}
	
	public void setChromo(double x[]) {
		xv = Arrays.copyOf(x, x.length); //deep copy
	}
	@Override
	public Node cloneSimple(){
		NodeRealValues tmp = new NodeRealValues();
		tmp.fitness = fitness;
		tmp.dfitness = dfitness;
		tmp.chromo = chromo;
		tmp.m = m;
		tmp.c = c;
		tmp.r = r;
		tmp.revisited = revisited;
		tmp.exploreRootSubTree = exploreRootSubTree;
		tmp.xv = Arrays.copyOf(xv, xv.length); //deep copy
		return tmp;
	}
	@Override
	public void setChromo(String chromo) {
		//this.chromo = chromo;
		String[] c = chromo.split(" "); //space is delimeter
		xv = new double[c.length];
		for (int i=0; i<xv.length;i++) {
			xv[i] = Double.parseDouble(c[i]);
		}
	}
	@Override
	public String getChromo() { 
		return Arrays.toString(xv);
	}
}
