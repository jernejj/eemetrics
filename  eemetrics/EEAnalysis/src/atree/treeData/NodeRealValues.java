package atree.treeData;

import java.util.Arrays;

/**
 * This class is used for real vector based individuals
 * 
 * @author matej
 *
 */
public class NodeRealValues extends Node {
	private double xv[]; //x values
	public NodeRealValues() {
		super();
	}
	public double[] getXV() {
		return xv;
	}
	
	public void setChromo(double x[]) {
		xv = Arrays.copyOf(x, x.length); //deep copy
	}
	@Override
	public void setChromo(String chromo) {
		this.chromo = chromo;
		String[] c = chromo.split(" "); //space is delimeter
		xv = new double[c.length];
		for (int i=0; i<xv.length;i++) {
			xv[i] = Double.parseDouble(c[i]);
		}
	}
}
