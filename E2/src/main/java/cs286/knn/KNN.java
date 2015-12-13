package cs286.knn ;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;


public class KNN {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		int percent = Integer.parseInt( args[0]) ;
		int k = Integer.parseInt (args[1]) ;
		String distance = args[2] ;
		String fileName = args[3] ;
		
		
		double[][] data = readFile(fileName) ;
		ArrayList<Point> points = new ArrayList<Point>();
		ArrayList<Point> species0 = new ArrayList<Point>();
		ArrayList<Point> species1 = new ArrayList<Point>();
		ArrayList<Point> species2 = new ArrayList<Point>();
		ArrayList<TreeMap> listMap = new ArrayList<TreeMap>();
		ArrayList<ArrayList<Point>> splitData = new ArrayList<ArrayList<Point>>() ;
		// convert all data vectors to Point add to list of Points
		for(double[] a : data) {
			points.add(new Point(a));
		}
		
		splitData = splitData(points , percent) ;
		ArrayList<Point> trainData = new ArrayList<Point>();
		trainData = splitData.get(0) ;
		ArrayList<Point> testData = new ArrayList<Point>();
		testData = splitData.get(1) ;
		
		//iterate all points 
		for(Point p1 : testData) {
			TreeMap < Double , Point> m = new TreeMap< Double,Point>() ;
			for( Point p2 : trainData){
				double d = Point.distance(p1.getPoint(), p2.getPoint(), distance) ;
					m.put(d, p2);
			}
			
			// Get a prediction of a point and assign it to the corresponding list of the same species
			int i = getPrecdictSpecies(m,k) ;
			if(i == 0)
				species0.add(p1);
			if(i == 1)
				species1.add(p1);
			if(i == 2)
				species2.add(p1);
		}
		
		System.out.println("k = " + k);
		System.out.println("distance = " + distance);
		System.out.println("accuracy for species 0 = " + getAccuracy(species0, 0.0 ));
		System.out.println("accuracy for species 1 = " + getAccuracy(species1, 1.0 ));
		System.out.println("accuracy for species 2 = " + getAccuracy(species2, 2.0 ));
		
		//int total = species0.size() + species1.size() + species2.size()  ;
		//System.out.println("Total Species 0 = " + species0.size() );
		//System.out.println("Total Species 1 = " + species1.size() );
		//System.out.println("Total Species 2 = " + species2.size() );
		//System.out.println("Total After = " + total );
		
		
		

	}
	
	/**
	 * Split list of data into train data and test data 
	 * @param data
	 * @param percent
	 * @return an arraylist of 2 arraylists. The first list is train data , The second list is the test data  
	 */
	
	public static ArrayList<ArrayList<Point>> splitData(ArrayList<Point> data, int percent) {
		ArrayList<ArrayList<Point>> splitData = new ArrayList<ArrayList<Point>>() ;
		ArrayList<Point> trainData = new ArrayList<Point>() ;
		ArrayList<Point> testData = new ArrayList<Point>();
		int numberOfTest = (int) Math.round((data.size()*(percent/100.0))) ;
		Collections.shuffle(data);
		for(int i = 0 ; i < data.size() ; i++) {
			if(i < numberOfTest)
				testData.add(data.get(i)) ;
			else
				trainData.add(data.get(i)) ;
		}
		splitData.add(trainData);
		splitData.add(testData) ;
		
		return splitData ;
	}
	
	/**
	 * 
	 * @param m
	 * @param k
	 * @return
	 */
	public static int getPrecdictSpecies(TreeMap<Double, Point> m , int k ) {
		int s = 0 ;
		ArrayList<Point> points = new ArrayList<Point>();
		int count = 0 ;
		for (Double key : m.keySet()){
			if(count == k)
				break ;
			points.add(m.get(key)) ;
			count ++ ;
			
		}
		
		s = vote(points) ;
		return s ;
	}
	
	/**
	 * 
	 * @param points
	 * @return
	 */
	public static int vote(ArrayList<Point> points){
		int[] count = new int[3];
		int c = 0 ;
		for(Point p : points) {
			if(p.getSpecies() == 0.0) {
				count[0]++ ;
			}
			if(p.getSpecies() == 1.0) {
				count[1]++ ;
			}
			if(p.getSpecies() == 2.0) {
				count[2]++ ;
			}	
		}
		
		int max = 0 ;
		int index = 0 ;
		for( int j = 0 ; j < count.length ; j++){
			if(count[j] > max )
				index = j ;
		}
		
		return index ;
	}
	
	
	/**
	 * Calculate the percentage of a species found in a list of species 
	 * @param points
	 * @param species
	 * @return
	 */
	
	public static double getAccuracy(ArrayList<Point> points, double species ) {
		int count = 0 ;
		for(Point p : points){
			if(p.getSpecies() == species)
				count++ ;
		}
		return (double)count / points.size() ;
	}
	
	public static double[][] readFile(String inputFile) throws IOException{
		ArrayList<double[]> data = new ArrayList<double[]>() ;
		FileReader fileReader = null ;
		//BufferedReader bufferedReader = null ;
		try {
			fileReader = new FileReader(inputFile) ;
			//bufferedReader = new BufferedReader(fileReader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner scan = new Scanner(fileReader) ;
		
		String line = null ;
		while(scan.hasNext()){
			double[] a = new double[5];
			line = scan.nextLine() ;
			String[] t = line.split("\\t");
			for(int i = 0 ; i < 5 ; i++){
				a[i] = Double.parseDouble(t[i]);
			}
			data.add(a) ;
		}
		
		fileReader.close();
		scan.close();
		double[][] returnMatrix = new double[data.size()][] ;
		for(int i = 0 ; i < data.size(); i++){
			returnMatrix[i] = data.get(i) ;
		}
		return returnMatrix ;
	}

}
