public class Edge implements EdgeInterface {
    PointInterface a,b;
    PointInterface[] edge;
    int shared;

    public Edge(PointInterface a, PointInterface b){
        this.a = a;
        this.b = b;
        edge = new PointInterface[2];
        edge[0] =a;
        edge[1] = b;
        shared=1;
    }
    public float edgelength(){
        return (a.getX()-b.getX())*(a.getX()-b.getX()) + (a.getY()-b.getY())*(a.getY()-b.getY()) +(a.getZ()-b.getZ())*(a.getZ()-b.getZ());
    }
    @Override
    public PointInterface[] edgeEndPoints() {
        return edge;
    }
    public int sharedCount(){
        return shared;
    }

//    public static void main(String[] args) {
//        float x1 = (float) 2; float y1 = (float) 1; float z1 = (float) 2;
//        float x2 = (float) 3.2; float y2 = (float)2.1; float z2 = (float) 4.7;
//        float x3 = (float) 4.4; float y3 =(float) 3.2; float z3 = (float) 7.4;
//        float a = x1-x2; float b = y1-y2; float c = z1-z2; float d = x3-x2; float e = y3-y2; float f = z3-z2;
//        float ar = b*f-c*e; float br = a*e-b*d; float cr = a*f-c*d;
//        System.out.println(ar*ar);
//        System.out.println(br*br);
//        System.out.println(cr*cr);
//    }
}
