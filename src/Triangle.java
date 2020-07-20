public class Triangle implements TriangleInterface {
    PointInterface a,b,c;
    PointInterface[] tri;
    Edge[] etri;
    int num;
    Edge ab, bc, ca;
    LinkedList<Triangle> neighbour;
    public Triangle(PointInterface a, PointInterface b, PointInterface c){
        this.a = a;
        this.b = b;
        this.c = c;
        neighbour = new LinkedList<>();
        ab = new Edge(a,b);
        bc = new Edge(b,c);
        ca = new Edge(c,a);
        tri = new PointInterface[3];
        etri = new Edge[3];
        etri[0] = ab;
        etri[1]=bc;
        etri[2] = ca;
        tri[0] = a;
        tri[1] = b;
        tri[2] = c;
    }

    @Override
    public PointInterface[] triangle_coord() {
        return tri;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setNeighbour(Triangle a){
        neighbour.insert(a);
    }

    public LinkedList<Triangle> getNeighbour() {
        return neighbour;
    }
    public float[] centroid(){
        float[] ret = new float[3];
        ret[0] = (a.getX() + b.getX()+c.getX())/3;
        ret[1] = (a.getY() + b.getY()+c.getY())/3;
        ret[2] = (a.getZ() + b.getZ()+c.getZ())/3;
        return ret;
    }
}
