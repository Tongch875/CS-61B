public class Body {
	/**
	 * 	double xxPos: Its current x position
	 *	double yyPos: Its current y position
	 *	double xxVel: Its current velocity in the x direction
	 *	double yyVel: Its current velocity in the y direction
	 *	double mass: Its mass
	 *	String imgFileName: The name of the file that corresponds to the image that depicts the planet 	
	 *  public static final double G: Universal gravitational constant
	 */
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public static final double G = 6.67e-11;

	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}

	public Body(Body p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

    /**
	 *  Calculate the distance between two Planets. 
	 *  @param Planet to be calculated
	 *  @return a double describing the distance between two Planets
	 */
    public double calcDistance(Body p){
        double dx=this.xxPos-p.xxPos;
        double dy=this.yyPos-p.yyPos;
        return Math.sqrt(dx*dx+dy*dy);

    }

    /**
	 *  Calculate the force exerted on this planet by the given planet.
	 *  @param Planet to give force
	 *  @return a double describing the force exerted on this planet by the given planet
	 */
    public double calcForceExertedBy(Body p){
        if (this.equals(p)){
            return 0;
        }
        return (G*this.mass*p.mass/(this.calcDistance(p) * this.calcDistance(p)));
        /* return (G / Math.pow(calcDistance(p), 2) * this.mass * p.mass);*/
    }

    /**
	 *  Calcualte the force exerted in the X direction
	 *  @param Planet to be calculated
	 *  @return a double describing the force exerted in the X direction
	 */
    public double calcForceExertedByX(Body p){
        return -this.calcForceExertedBy(p)*((this.xxPos-p.xxPos)/(this.calcDistance(p)));
    }

    /**
	 *  Calcualte the force exerted in the Y direction
	 *  @param Planet to be calculated
	 *  @return a double describing the force exerted in the Y direction
	 */
    public double calcForceExertedByY(Body p){
        return -this.calcForceExertedBy(p)*((this.yyPos-p.yyPos)/(this.calcDistance(p)));
    }

    /**
	 *  Calculate the net X force exerted by all planets in a array.
	 *  @param planet[] a Planets array
	 *  @return a double describing the net X force exerted by other Planets
	 */
    public double calcNetForceExertedByX(Body[] p){
        double xallforce = 0;
        for (Body each : p){
            if (this.equals(each)){ // For unknown reasons, you need to separate your status in the list
                continue;
            }
            xallforce = xallforce +this.calcForceExertedByX(each);
        } 
        return xallforce;
    }

    public double calcNetForceExertedByY(Body[] p){
        double Yallforce = 0;
        for (Body each : p){
            if (this.equals(each)){
                continue;
            }
            Yallforce = Yallforce +this.calcForceExertedByY(each);
        } 
        return Yallforce;
    }

    /**
	 *  Calculate how much the forces exerted on the planet will cause that planet 
	 *  to accelerate, and the resulting change in the planet’s velocity 
	 *  and position in a small period of time dt.
	 *  @param double,double,double a small period of time dt , x- and y- forces
	 这里没有其他的body，可以不用this*/
    public void update(double dt, double fx, double fy){
        double a_x_net = fx/this.mass;
        double a_y_net = fy/this.mass;
        //this.xxVel = this.xxVel + a_x_net*dt; 这里可以不用this
		xxVel += a_x_net*dt;
        yyVel += a_y_net*dt;
        xxPos += this.xxVel*dt;
        yyPos += this.yyVel*dt;
    }

	/**
	 *   Draw the Planet’s image at the Planet’s position
	 */
	public void draw(){
		String imageToDraw = "images/"+(imgFileName);
		//StdDraw.enableDoubleBuffering();
		//StdDraw.setScale(-100, 100);
		//StdDraw.clear();
		StdDraw.picture(xxPos, yyPos, imageToDraw);
	}


}