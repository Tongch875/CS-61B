public class NBody{

    public static double readRadius(String args){ //*WHY USE static??????*/
        In in = new In(args);
        double all_planet_num = in.readInt(); /*firstItemInFile*/
        double radius = in.readDouble();  //secondItemInFile
        return radius;
    }

    public static Body[] readBodies(String filename){
        In in = new In(filename);
        int all_planet_num = in.readInt();
        in.readDouble();
        int i = 0;
        Body arr[] = new Body[all_planet_num];//
        while(i != all_planet_num){  //*WHY !in.isEmpty() DONT WORK???????*/
            // //planetA[i] = new Body(0, 0, 0, 0, 0, args );
            // planetA[i].xxPos = in.readDouble();
            
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            arr[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
            // Body last[i] = arr;   NO
            i++;
        }
        return arr;
    }

    public static void main (String[] args){ //
        /* get data */
        args = new String[] {"157788000.0", "25000.0", "data/planets.txt"};
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double uniradius = readRadius(filename);
        Body[] bodies = readBodies(filename);

        /* Draw 频繁的show 会让画面闪动，所以只要在最后show一次就行*/        
        String imageToDraw = "images/starfield.jpg";
        //StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-uniradius, uniradius);
        StdDraw.clear();
        StdDraw.picture(0, 0, imageToDraw);
        //StdDraw.show();

        for (Body p : bodies){
            p.draw();
            //StdDraw.show();
        }
        //StdDraw.show();

        /* Animation */
        StdDraw.enableDoubleBuffering();
        double t = 0;
        while(t <= T) {
			double[] xForces = new double[bodies.length];
			double[] yForces = new double[bodies.length];
			for(int i=0; i<bodies.length; i++) {
				xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
                bodies[i].update(dt, xForces[i], yForces[i]);
			}
            
            StdDraw.setScale(-uniradius, uniradius);
            StdDraw.clear();
            StdDraw.picture(0, 0, imageToDraw);            
            
            for (Body p : bodies){
                StdDraw.enableDoubleBuffering();
                p.draw();
                //StdDraw.show();
            }
            StdDraw.show();

            StdDraw.pause(10);
            t += dt;

        }

    }
}