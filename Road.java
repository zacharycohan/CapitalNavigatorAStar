/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zachary Cohan
 */
public class Road {

    City c1;
    City c2;
    double distance;
    double crowFlies;
    
    
    public Road(City c1, City c2)
    {
        this.c1 = c1;
        this.c2 = c2;
    }
    public Road(City c1, City c2, double dist)
    {
        this.c1 = c1;
        this.c2 = c2;
        distance = dist;    
    }

    public boolean equals(Road r) {
        return (this.c1.equals(r.c1) && this.c2.equals(r.c2)); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
