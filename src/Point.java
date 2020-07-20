public class Point implements PointInterface {
    float x,y,z;
    float[] cord;
    LinkedList<Point> neibPoint;
    LinkedList<Triangle> neibTri;
    LinkedList<EdgeInterface> neibEdge;
    int repeat;

    public Point(float x, float y, float z){
        this.x=x;
        this.y=y;
        this.z=z;
        neibTri = new LinkedList<>();
        neibPoint = new LinkedList<>();
        neibEdge = new LinkedList<>();
        cord = new float[3];
        repeat =0;
        cord[0]=x; cord[1] =y; cord[2] = z;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setNeibPoint(Point a) {
        neibPoint.insert(a);
    }
    public void setNeibEdge(EdgeInterface a) {
        neibEdge.insert(a);
    }
    public void setNeibTri(Triangle a) {
        neibTri.insert(a);
    }
    public LinkedList<Point> getNeibPoint() {
        return neibPoint;
    }
    public LinkedList<Triangle> getNeibTri() {
       return neibTri;
    }
    public LinkedList<EdgeInterface> getNeibEdge() {
        return neibEdge;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public float[] getXYZcoordinate() {
        return cord;
    }
}
