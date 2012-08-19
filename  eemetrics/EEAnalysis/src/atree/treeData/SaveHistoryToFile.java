package atree.treeData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SaveHistoryToFile {
	private FileOutputStream fstream;
	private ZipOutputStream out;
	private int problemID;
	private String fileName;
	private String path;
	private String parameters;
	private int run;
	private String filename;

	public SaveHistoryToFile(String path, int problemID, String parameters,
			int run) throws IOException {
		super();
		this.problemID = problemID;
		this.parameters = parameters;
		this.run = run;
		this.path = path;
		filename = "out_p" + problemID + "_" + parameters + "_" + "a" + run;
		fstream = new FileOutputStream(path + "/" + filename + ".zip");
		System.out.println(path + "/" + filename + ".zip");
		out = new ZipOutputStream(fstream);
		ZipEntry ze = new ZipEntry(filename + ".stat");
		out.putNextEntry(ze);
	}

	public void writeGeneartion(ArrayList<Node> generations, int gen,
			String comment) {

		try {
			out.write(("Generation: " + gen+" "+comment+ "\n").getBytes());
			for (Node n : generations) {
				out.write((n.toFileFormat() + "\n").getBytes());
			}
			out.flush();
		} catch (IOException e) {
			System.err.println("SaveHistoryToFile.writeGeneartion writing error?");
			e.printStackTrace();
		}
	}

	public void close() throws IOException {
		out.close();
		fstream.close();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
