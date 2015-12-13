package cs286.knn ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.la4j.vector.dense.BasicVector;

public class Point {
	
	private double species = -1 ;
	private BasicVector point = new BasicVector();

	
	Point(double[] input){
		species = Double.valueOf(input[input.length - 1]) ;
		this.point = new BasicVector(Arrays.copyOf(input, input.length - 1));
		/*
		double[] t = new double[4] ;
		for(int i = 0 ; i < 4 ; i++)
			t[i] = input[i] ;
		this.point = new BasicVector(t);
		*/
	}
	
	

	public BasicVector getPoint() {
		return point;
	}
	


	public void setPoint(BasicVector point) {
		this.point = point;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Point)) {
	        return false;
	    }
	    
	    Point x = (Point) other ;
	    if (this.species == x.getSpecies() && this.point.equals(x.getPoint()))
	    	return true;
	    else 
	    	return false ;
	}
	
	public double getSpecies() {
		return species;
	}


	public void setSpecies(double species) {
		this.species = species;
	}


	private static double euclideanDistance(BasicVector x, BasicVector y){
		double dist = 0 ;
		BasicVector v = (BasicVector) x.subtract(y) ;
		dist = v.euclideanNorm() ;
		return dist ;
	}
	
	private static double cosineDistance(BasicVector x, BasicVector y){
		double dist = 0 ;
		double dotproduct = x.innerProduct(y) ;
		dist = 1 - dotproduct/(x.euclideanNorm()*y.euclideanNorm()) ;
		return Math.abs(dist) ;
	}
	
	static public double distance(BasicVector x, BasicVector y, String method) {
		if(method.equalsIgnoreCase("euclidean"))
			return euclideanDistance(x,y) ;
		if(method.equalsIgnoreCase("cosine"))
			return cosineDistance(x,y) ;
		
		return -100 ;
	}
	
	 @Override
	    public int hashCode() {
	        final int prime = 31;
	        double result = 1;
	        result = point.multiply(prime).sum() + this.species ;
	        return (int) result;
	    }
	 

}
