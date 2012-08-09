package atree.experimet.realvector;

import java.util.ArrayList;

import atree.metrics.ATMetrics;
import atree.metrics.PrintAMetrics;
import atree.metrics.PrintStatATMetrics;
import atree.metrics.StatATMetrics;
import atree.metrics.criteria.DominantParentCriteriaEuclidianDistanceRealValues;
import atree.metrics.criteria.DominantParentCriteriaNormalizedEuclidianDistance;
import atree.metrics.criteria.EECriteriaEuclidianDistance;
import atree.metrics.criteria.EECriteriaNormalizedEuclidianDistance;
import atree.metrics.criteria.RevisitedCriteriaEuclidian;
import atree.metrics.criteria.RevisitedCriteriaNormalizedEuclidian;
import atree.treeData.Nodes;

public class ExperimentSovova {
	/**
	 * This is test for real vector input files!
	 * Reads 3 runs of algorithm and generates one averaged column for latex table.
	 * Generate Latex table and prints it on screen!
	 */
	public static void createLatexTableOverXrunsWithDifferentColumns() {
		Nodes n;
		ATMetrics m=null;
		StatATMetrics sumTableColumnStatistic = new StatATMetrics(); // for statistic
		ArrayList<ArrayList<String>> columsForSumTable = new ArrayList<ArrayList<String>>();
		columsForSumTable.add(PrintStatATMetrics.getDoubleInfoColumn());
		ArrayList<String> tableHeadsForSumTable = new ArrayList<String>();
		String tmpSourceFileName;
		final int MAX_RUNS = 30; //number of repeated
		String rootPath = "test_cases/realvector/de/sovova/";
		/**
		 * test_cases/realvector/de/sovova
		 * Popsize = 30; //based on the DE in the paper >Maxgen = 500; //based on
		 * the DE in the paper >pc (CR) = 0.9 //based on the DE in the paper >pm (F)
		 * = 0.9 //based on the DE in the paper >Round = 5 (will be increased after
		 * checking with no bug) >Z = 0~10 >W = 0~10 >x_K = 0~0.0619
		 */
		
		tmpSourceFileName = rootPath + "out_20120808a";
		String problemExt = ".stat";
		String fileName;
		double intervals[]={10,10,0.0619};
		ArrayList<Nodes> inMemory = new ArrayList<Nodes>(); //needs more memory but faster
		//read ones calcuale many times run with -Xms256m -Xmx1024m
		for (int runs = 1; runs <= MAX_RUNS; runs++) {
			n = new Nodes();
			fileName = tmpSourceFileName + runs + problemExt;
			n.createAllFromRealVector(fileName, 500); // read first 500
			inMemory.add(n);
		}
		PrintStatATMetrics statTAble = new PrintStatATMetrics();
		//Column---------------------------------------------------------
		tableHeadsForSumTable.add("$0.1\\%$");
		for (int runs = 0; runs < MAX_RUNS; runs++) {
			m = new ATMetrics(inMemory.get(runs).getAllNodes(),
					new DominantParentCriteriaNormalizedEuclidianDistance(intervals),
					new EECriteriaNormalizedEuclidianDistance(0.001, intervals),
					new RevisitedCriteriaNormalizedEuclidian(0.0000001,intervals));
			sumTableColumnStatistic.add(m);
		}
		System.out.println(m.getLatexInfo());
		statTAble.setStatATMetrics(sumTableColumnStatistic);
		columsForSumTable.add(statTAble.getDoubleColumn());
		sumTableColumnStatistic.clear(); //reuse object
		//End Column---------------------------------------------------------
		//Column---------------------------------------------------------
		tableHeadsForSumTable.add("$0.5\\%$");
		for (int runs = 0; runs < MAX_RUNS; runs++) {
			m = new ATMetrics(inMemory.get(runs).getAllNodes(),
					new DominantParentCriteriaNormalizedEuclidianDistance(intervals),
					new EECriteriaNormalizedEuclidianDistance(0.005, intervals),
					new RevisitedCriteriaNormalizedEuclidian(0.0000001,intervals));
			sumTableColumnStatistic.add(m);
		}
		System.out.println(m.getLatexInfo());
		statTAble.setStatATMetrics(sumTableColumnStatistic);
		columsForSumTable.add(statTAble.getDoubleColumn());
		sumTableColumnStatistic.clear(); //reuse object
		//End Column---------------------------------------------------------
		//Column---------------------------------------------------------
		tableHeadsForSumTable.add("$1\\%$");
		for (int runs = 0; runs < MAX_RUNS; runs++) {
			m = new ATMetrics(inMemory.get(runs).getAllNodes(),
					new DominantParentCriteriaNormalizedEuclidianDistance(intervals),
					new EECriteriaNormalizedEuclidianDistance(0.01, intervals),
					new RevisitedCriteriaNormalizedEuclidian(0.0000001,intervals));
			sumTableColumnStatistic.add(m);
		}
		System.out.println(m.getLatexInfo());
		statTAble.setStatATMetrics(sumTableColumnStatistic);
		columsForSumTable.add(statTAble.getDoubleColumn());
		sumTableColumnStatistic.clear();
		//End Column---------------------------------------------------------
		//Column---------------------------------------------------------
		tableHeadsForSumTable.add("$2\\%$");
		for (int runs = 0; runs < MAX_RUNS; runs++) {
			m = new ATMetrics(inMemory.get(runs).getAllNodes(),
					new DominantParentCriteriaNormalizedEuclidianDistance(intervals),
					new EECriteriaNormalizedEuclidianDistance(0.02, intervals),
					new RevisitedCriteriaNormalizedEuclidian(0.0000001,intervals));
			sumTableColumnStatistic.add(m);
		}
		System.out.println(m.getLatexInfo());
		statTAble.setStatATMetrics(sumTableColumnStatistic);
		columsForSumTable.add(statTAble.getDoubleColumn());
		sumTableColumnStatistic.clear();
		//End Column---------------------------------------------------------
		System.out.println(PrintStatATMetrics.toLatex(tableHeadsForSumTable, columsForSumTable,"Normalized distance"));
		tableHeadsForSumTable.clear();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createLatexTableOverXrunsWithDifferentColumns();

	}

}
