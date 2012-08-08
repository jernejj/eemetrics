package atree.experimet.realvector;

import java.util.ArrayList;

import atree.metrics.ATMetrics;
import atree.metrics.PrintAMetrics;
import atree.metrics.PrintStatATMetrics;
import atree.metrics.StatATMetrics;
import atree.metrics.criteria.DominantParentCriteriaEuclidianDistanceRealValues;
import atree.metrics.criteria.EECriteriaEuclidianDistance;
import atree.metrics.criteria.RevisitedCriteriaEuclidian;
import atree.treeData.Nodes;

public class ExperimentSovova {
	/**
	 * This is test for real vector input files!
	 * Reads 3 runs of algorithm and generates one averaged column for latex table.
	 * Generate Latex table and prints it on screen!
	 */
	public static void createOverGenerationTable() {
		Nodes n;
		ATMetrics m;
		StatATMetrics sumTableColumnStatistic = new StatATMetrics(); // for statistic
		ArrayList<ArrayList<String>> columsForSumTable = new ArrayList<ArrayList<String>>();
		columsForSumTable.add(PrintStatATMetrics.getDoubleInfoColumn());
		ArrayList<String> tableHeadsForSumTable = new ArrayList<String>();
		String tmpSourceFileName;
		final int MAX_RUNS = 30; //number of repeated
		String rootPath = "test_cases/realvector/de/sovova/";
		/**
		 * Popsize = 30; //based on the DE in the paper >Maxgen = 500; //based on
		 * the DE in the paper >pc (CR) = 0.9 //based on the DE in the paper >pm (F)
		 * = 0.9 //based on the DE in the paper >Round = 5 (will be increased after
		 * checking with no bug) >Z = 0~10 >W = 0~10 >x_K = 0~0.0619
		 */
		
		tmpSourceFileName = rootPath + "out_20120808a";
		String problemExt = ".stat";
		String fileName;
		//First Column---------------------------------------------------------
		tableHeadsForSumTable.add("Simple");
		for (int runs = 1; runs <= MAX_RUNS; runs++) {
			n = new Nodes();
			fileName = tmpSourceFileName + runs + problemExt;
			n.createAllFromRealVector(fileName, 501); // read first 500
			m = new ATMetrics(n.getAllNodes(),
					new DominantParentCriteriaEuclidianDistanceRealValues(),
					new EECriteriaEuclidianDistance(1.5),
					new RevisitedCriteriaEuclidian(0.00001));
			sumTableColumnStatistic.add(m);
		}
		PrintStatATMetrics statTAble = new PrintStatATMetrics(sumTableColumnStatistic);
		columsForSumTable.add(statTAble.getDoubleColumn());
		//End first Column---------------------------------------------------------
		System.out.println(PrintStatATMetrics.toLatex(tableHeadsForSumTable, columsForSumTable,"testCaption"));
		tableHeadsForSumTable.clear();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createOverGenerationTable();

	}

}
