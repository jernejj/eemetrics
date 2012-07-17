package atree.experimet.ecj;

import java.util.ArrayList;

import atree.metrics.ATMetrics;
import atree.metrics.PrintAMetrics;
import atree.metrics.PrintStatATMetrics;
import atree.metrics.StatATMetrics;
import atree.treeData.Nodes;
import atree.util.Util;


public class ExperimentECJ_DE {
	//RastriginEEstatPSO_a1.stat
	//SpherePSOOut_a1.STAT
	public static String dir = "test_cases/ecj/de/"; // on mac linux "/"
	public static String pripona = "PSO";
//	public static String dir = "D:\\My Documents\\fax\\Doktorat\\Workplace\\ecj\\ec\\EEstat\\samples\\"+pripona+"/"; // on mac linux "/"
	public static String function = "Rosenbrock";
	public static String problem_1 = function + "Best" + "EEStat" + "_a";
	public static String problem_2 = function + "PSO" + "EEStat" + "_a";
	public static String problem_3 = function + "ES" + "EEStat" + "_c";
	public static String problem_4 = "Rosenbrock" + pripona + "EEStat" + "_a";
	public static String analiza = "a";


	public static String[] mixrun;
	public static int[] mixrunID;
	public static int[] printrunID; //latex column name
	public static double epsilon[][];
	public static String problemFiles[];
	
	public static void setArrays(String subdir, int i) {
		mixrun = new String[i];
		for (int j=0; j<i;j++) mixrun[j] = subdir;
		mixrunID = new int[i];
		for (int j=0; j<i;j++) mixrunID[j] = j+1;
		printrunID = new int[i];
		for (int j=0; j<i;j++) printrunID[j] = j+1;	
	}
	//  cols2.add(createXproblmTable(problemX[i],number_of_test_repetition,scenario_type,x,false));
	private static ArrayList<String> createXproblmTable(String fileName, int number_of_repetition, int type, int myX, boolean print, double epsilon1[]) {
		ArrayList<String> heads = new ArrayList<String>();
		ArrayList<ArrayList<String>> cols = new ArrayList<ArrayList<String>>();
		cols.add(PrintAMetrics.getInfoColumn());
		Nodes n;
		ATMetrics m;
		PrintAMetrics pm=null;
		String problem2;
		StatATMetrics sam=new StatATMetrics();
		int maxgeneration=10;
		//long start = System.currentTimeMillis();
		// ---------------------------------------------------------
		for (int id = 1; id < (number_of_repetition+1); id++) {
			problem2 = dir + fileName + id + ".stat";
			System.out.println(problem2);
			n = new Nodes();
			n.createAll_ECJ(problem2, maxgeneration, epsilon1, false);
			if (type==Nodes.SCENARIO_OPTIMISTIC) {
				n.transformInOptimisticParetoTree();
			}
			if (type==Nodes.SCENARIO_SEMI_OPTIMISTIC) {
				n.transformInOptimisticPlusParetoTree();
			}
			heads.add("" + id);
			m = new ATMetrics(n.getInitTrees(), myX);
			pm = new PrintAMetrics(m);
			sam.add(m);
			cols.add(pm.getColumn());
	
		}
		PrintStatATMetrics statTAble = new PrintStatATMetrics(sam);
		if (print) System.out.println(pm.toLatex(heads, cols,fileName));
		// New table clear and print
		heads.clear();
		cols.clear();
		cols.add(PrintAMetrics.getInfoColumn());
		return statTAble.getDoubleColumn();

	}

	private static void mutationMainTest(String problemX[], int x[],int number_of_test_repetition, double eps[][]){
		ArrayList<ArrayList<String>> cols2 = new ArrayList<ArrayList<String>>();
		cols2.add(PrintStatATMetrics.getDoubleInfoColumn());
		ArrayList<String> heads = new ArrayList<String>();
		long start = System.currentTimeMillis();
		int scenario_type=3; //normal
		for (int i=0; i<problemX.length; i++) {
			System.out.println("Start: "+(i+1)+"/"+problemX.length);
		  heads.add(""+problemX[i]); //Column name in latex table
		  cols2.add(createXproblmTable(problemX[i],number_of_test_repetition,scenario_type,x[i],false, eps[i]));
		  System.out.println(i+" ("+((double)(System.currentTimeMillis()-start)/1000/60)+")"+problemX[i]);
		}
		System.out.println(PrintStatATMetrics.toLatex(heads, cols2, function));	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		setArrays(dir, 1);
		int problemDimension = 20;
		epsilon = new double[4][];
		epsilon[0] = Util.generateEpsilonVector(problemDimension, 1.44); //for binary vector is any value less than 1 ok!
		epsilon[1] = Util.generateEpsilonVector(problemDimension, 1.44); //for binary vector is any value less than 1 ok!
		epsilon[2] = Util.generateEpsilonVector(problemDimension, 1.44); //for binary vector is any value less than 1 ok!
		epsilon[3] = Util.generateEpsilonVector(problemDimension, 1.44); //for binary vector is any value less than 1 ok!
		
		int number_of_test_repetition = 9;
		int x[] = new int[4];
		x[0] = 10; //X dimension-s is/are changed by epsilon
		x[1] = 10; //X dimension-s is/are changed by epsilon
		x[2] = 10; //X dimension-s is/are changed by epsilon
		x[3] = 10; //X dimension-s is/are changed by epsilon
		problemFiles = new String[3];
		problemFiles[0] = problem_1;
		problemFiles[1] = problem_2;
		problemFiles[2] = problem_3;
		//problemFiles[3] = problem_4;
		mutationMainTest(problemFiles, x,number_of_test_repetition, epsilon);
 

	}

}
