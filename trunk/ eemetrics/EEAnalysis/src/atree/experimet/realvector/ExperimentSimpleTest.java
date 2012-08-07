package atree.experimet.realvector;

import java.util.ArrayList;

import atree.metrics.ATMetrics;
import atree.metrics.PrintAMetrics;
import atree.metrics.criteria.DominantParentCriteriaEuclidianDistanceRealValues;
import atree.metrics.criteria.EECriteriaEuclidianDistance;
import atree.metrics.criteria.RevisitedCriteriaEuclidian;
import atree.treeData.Node;
import atree.treeData.Nodes;


public class ExperimentSimpleTest {
	public static void createOverGenerationTable() {
		ArrayList<String> heads = new ArrayList<String>();
		ArrayList<ArrayList<String>> cols = new ArrayList<ArrayList<String>>();
		cols.add(PrintAMetrics.getInfoColumn());
		String rootPath = "test_cases/realvector/de/";
		Nodes n;
		ATMetrics m;
		PrintAMetrics pm;
		String problem2;
		//---------------------------------------------------------
		problem2 = rootPath + "de_rand1bin_a1.stat";
		//problem2 = rootPath + "test_a1.stat";
		//x=3-----------------------------
		n = new Nodes();
		n.createAllFromRealVector(problem2,500); //read first 500 generations
		heads.add("{\\footnotesize$gaus=1$}");
		m = new ATMetrics(n.getAllNodes(),new DominantParentCriteriaEuclidianDistanceRealValues(),new EECriteriaEuclidianDistance(0.0001),new RevisitedCriteriaEuclidian(0.00001));
		pm = new PrintAMetrics(m);
		cols.add(pm.getColumn());
		System.out.println(pm.toLatex(heads, cols,"Analyse of threshold $x$ and influence of generations over time in $x=4$"));
		//New table clear and print
		heads.clear();
		cols.clear();
		cols.add(PrintAMetrics.getInfoColumn());
				

	}
	public static void createOverMuatationTestTable() {
		ArrayList<String> heads = new ArrayList<String>();
		ArrayList<ArrayList<String>> cols = new ArrayList<ArrayList<String>>();
		cols.add(PrintAMetrics.getInfoColumn());
		String rootPath = "/Users/matej/Documents/clanki2010/IEEETec/ResultData/";
		Nodes n;
		ATMetrics m;
		PrintAMetrics pm;
		//---------------------------------------------------------
		//0.5-----------------------------
		n = new Nodes();
		n.createAllBarbara(rootPath + "knapsack_100_2_5Analiza.txt");
		heads.add("{\\footnotesize$n(.5\\%)$}");
		m = new ATMetrics(n.getInitTrees(), 4);
		pm = new PrintAMetrics(m);
		cols.add(pm.getColumn());

		
		n = new Nodes();
		n.createAllBarbara(rootPath + "knapsack_100_2_5Analiza.txt");
		n.setAllBarbaraPareto(rootPath +"knapsack_100_2_5NajdenaFronta.txt");
		n.transformInOptimisticParetoTree();		
		heads.add("{\\footnotesize$ o(.5\\%)$}");
		m = new ATMetrics(n.getInitTrees(), 4);
		pm = new PrintAMetrics(m);
		cols.add(pm.getColumn());

		n = new Nodes();
		n.createAllBarbara(rootPath + "knapsack_100_2_5Analiza.txt");
		n.setAllBarbaraPareto(rootPath +"knapsack_100_2_5NajdenaFronta.txt");
		n.transformInOptimisticPlusParetoTree();		
		heads.add("{\\footnotesize$ s(.5\\%)$}");
		m = new ATMetrics(n.getInitTrees(), 4);
		pm = new PrintAMetrics(m);
		cols.add(pm.getColumn());

		//1-----------------------------
		n = new Nodes();
		n.createAllBarbara(rootPath + "knapsack_100_2_10Analiza.txt");
		heads.add("{\\footnotesize$ n(1\\%)$}");
		m = new ATMetrics(n.getInitTrees(), 4);
		pm = new PrintAMetrics(m);
		cols.add(pm.getColumn());

		
		n = new Nodes();
		n.createAllBarbara(rootPath + "knapsack_100_2_10Analiza.txt");
		n.setAllBarbaraPareto(rootPath +"knapsack_100_2_10NajdenaFronta.txt");
		n.transformInOptimisticParetoTree();		
		heads.add("{\\footnotesize$ o(1\\%)$}");
		m = new ATMetrics(n.getInitTrees(), 4);
		pm = new PrintAMetrics(m);
		cols.add(pm.getColumn());

		n = new Nodes();
		n.createAllBarbara(rootPath + "knapsack_100_2_10Analiza.txt");
		n.setAllBarbaraPareto(rootPath +"knapsack_100_2_10NajdenaFronta.txt");
		n.transformInOptimisticPlusParetoTree();		
		heads.add("{\\footnotesize$s(1\\%)$}");
		m = new ATMetrics(n.getInitTrees(), 4);
		pm = new PrintAMetrics(m);
		cols.add(pm.getColumn());

		//2-----------------------------
		n = new Nodes();
		n.createAllBarbara(rootPath + "knapsack_100_2_20Analiza.txt");
		heads.add("{\\footnotesize$n(2\\%)$}");
		m = new ATMetrics(n.getInitTrees(), 4);
		pm = new PrintAMetrics(m);
		cols.add(pm.getColumn());

		
		n = new Nodes();
		n.createAllBarbara(rootPath + "knapsack_100_2_20Analiza.txt");
		n.setAllBarbaraPareto(rootPath +"knapsack_100_2_20NajdenaFronta.txt");
		n.transformInOptimisticParetoTree();		
		heads.add("{\\footnotesize$ o(2\\%)$}");
		m = new ATMetrics(n.getInitTrees(), 4);
		pm = new PrintAMetrics(m);
		cols.add(pm.getColumn());

		n = new Nodes();
		n.createAllBarbara(rootPath + "knapsack_100_2_20Analiza.txt");
		n.setAllBarbaraPareto(rootPath +"knapsack_100_2_20NajdenaFronta.txt");
		n.transformInOptimisticPlusParetoTree();		
		heads.add("{\\footnotesize$ s(2\\%)$}");
		m = new ATMetrics(n.getInitTrees(), 4);
		pm = new PrintAMetrics(m);
		cols.add(pm.getColumn());
	
		//---------------------------------------------------------		
		System.out.println(pm.toLatex(heads, cols,"Mutation parameter analyse on normal ($n$), optimistic ($o$) and semioptimistic ($s$) tree"));

	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createOverGenerationTable();
		//createOverMuatationTestTable();
		
	}

}
