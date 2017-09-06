
import java.util.HashMap;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zachary Cohan
 */
public class City implements Comparable<City>{
    protected String name;
    protected double lat;
    protected double lng;
    protected double dist;
    protected double score;
    protected double runningTotal = 0;
    protected City returnCity = null;
    protected int id;
    private final double R = 6371;
    protected City edgeFrom;
    protected HashMap<City,Double> adjList;

    
    public City(String name,double lat, double lng, int id){
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.id = id;
        adjList = new HashMap<>();
    }
    
    public City(String name, double dist){
        this.name = name;
        this.dist = dist;
    }
    
    public void setReturnCity(City c)
    {
        returnCity = c;
    }
    
    public void runningTotal(double runningTotal)
    {
        this.runningTotal += runningTotal;
    }
    public double getRunningTotal()
    {
        return this.runningTotal;
    }
    
    public void addToAdjList(City c,Double d)
    {
        adjList.put(c, d);
    }
    
    public City edgeFrom(){
        return edgeFrom;
    }
    
    
    public Iterable<City> getAdjList()
    {
         return adjList.keySet();
    }
    public boolean equals(City c)
    {
        return (this.name.equals(c.name));
    }

    public void calcDist(City c)
    {
        this.dist = Math.acos(Math.sin(lat)*Math.sin(c.lat) + Math.cos(lat)*Math.cos(c.lat)*Math.cos(lng-c.lng)) * this.R;
    }
    
    public int id(){
        return id;
    }
    

    @Override
    public int compareTo(City c) 
    {
        return (int)(this.score - c.score);
    }
    
    public void modifyScore(double i)
    {
        score = dist+i;
    }
    
    public String toString()
    {
        return (name+", ID: "+id+", Score: "+dist);
    }

    City getReturnCity() {
        return returnCity;
    }
}
