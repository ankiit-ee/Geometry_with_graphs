public class Graph {
    LinkedList<LinkedList<Triangle>> graph;
    LinkedList<Triangle> trilist;
    LinkedList<Point> polist;
    float leastX, leastY, leastZ;
    float maxX, maxY, maxZ;
    float min, max;
    public Graph(){
        graph=new LinkedList<>();
        leastX=0; leastY=0; leastZ =0;
        maxX = 0; maxY =0; maxZ =0;
        min =100; max =-100;
        trilist=new LinkedList<>();
        polist = new LinkedList<>();
    }

    public void add(Triangle t){
        if(trilist.find(t)!=null) return;
        trilist.insert(t);
        LinkedList<Triangle> temp = new LinkedList<>();
        temp.insert(t);
        LinkedList.Node<LinkedList<Triangle>> foo = graph.head;
        while(foo!=null){
            LinkedList<Triangle> foo1 = foo.obj;
            LinkedList.Node<Triangle> re = foo1.head;
            if(similarity(re.obj,t)){
                foo1.insert(t);
                re.obj.setNeighbour(t);
                t.setNeighbour(re.obj);
                temp.insert(re.obj);

            }
            foo = foo.next;
        }
        polist.insert((Point) t.a); polist.insert((Point) t.b); polist.insert((Point) t.c);
        graph.insert(temp);
    }

    public boolean similarity(Triangle p, Triangle q){
        Edge[] arr1 = p.etri;
        Edge[] arr2 = q.etri;
        boolean flag = false;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(check(arr1[i],arr2[j])){
//                    arr1[i].shared++; arr2[j].shared++;
                    flag=true;
                    break;
                }
            }
        }

        return flag;
    }
    public boolean check(Edge ab, Edge bc){
        boolean flag1 = ab.a.getX()==bc.a.getX() && ab.a.getY()==bc.a.getY() && ab.a.getZ()==bc.a.getZ();
        boolean flag2 = ab.b.getX()==bc.b.getX() && ab.b.getY()==bc.b.getY() && ab.b.getZ()==bc.b.getZ();
        boolean flag3 = ab.a.getX()==bc.b.getX() && ab.a.getY()==bc.b.getY() && ab.a.getZ()==bc.b.getZ();
        boolean flag4 = ab.b.getX()==bc.a.getX() && ab.b.getY()==bc.a.getY() && ab.b.getZ()==bc.a.getZ();
        if((flag1||flag3) && (flag2||flag4)){
            return true;
        }
        return false;
    }
    public float[] getExtremeX(){
        return new float[]{leastX, maxX};
    }
    public float[] getExtremeY(){
        return new float[]{leastY, maxY};
    }
    public float[] getExtremeZ(){
        return new float[]{leastZ, maxZ};
    }
    public float minimum(float a, float b, float c){
        if(a<=b&&a<=c) return a;
        if(b<a&&b<=c) return b;
        return c;
    }
    public float maximum(float a, float b, float c){
        if(a>=b&&a>=c) return a;
        if(b>a&&b>=c) return b;
        return c;
    }
    public Point[] extreme(){
        LinkedList.Node temp = polist.head;
        Point xmin = (Point) temp.obj; Point xmax = (Point) temp.obj;
        Point ymin = (Point) temp.obj; Point ymax = (Point) temp.obj;
        Point zmin = (Point) temp.obj; Point zmax = (Point) temp.obj;


        float minx = xmin.getX(); float miny = xmin.getY(); float minz = xmin.getZ();
        float maxx = xmin.getX(); float maxy = xmin.getY(); float maxz = xmin.getZ();
        while(temp!=null){
            Point t = (Point) temp.obj;
            if(minx>=t.getX()){ minx = t.getX(); xmin = t; }
            if(miny>=t.getY()){ miny = t.getY(); ymin = t; }
            if(minz>=t.getZ()){ minz = t.getZ(); zmin = t; }

            if(maxx<=t.getX()){ maxx = t.getX(); xmax = t; }
            if(maxy<=t.getY()){ maxy = t.getY(); ymax = t; }
            if(maxz<=t.getZ()){ maxz = t.getZ(); zmax = t; }

            temp = temp.next;
        }
        return new Point[]{xmin,xmax,ymin,ymax,zmin,zmax};
    }

    public boolean connection(Triangle t){
        LinkedList.Node<LinkedList<Triangle>> foo = graph.head;
        while(foo!=null){
            LinkedList<Triangle> foo1 = foo.obj;
            LinkedList.Node<Triangle> re = foo1.head;
            if(similarity(re.obj,t)) return true;
            foo = foo.next;
        }
        return false;
    }
    public boolean pointexist(Point p){
        LinkedList.Node n = polist.head;
        while(n!=null){
          if(n.obj==p) return true;
        }
        return false;
    }
    public float distance(Point a, Point b){
        return (a.getX()-b.getX())*(a.getX()-b.getX()) + (a.getY()-b.getY())*(a.getY()-b.getY()) + (a.getZ()-b.getZ())*(a.getZ()-b.getZ());
    }
    public void combine(Graph g){

        LinkedList.Node n = g.graph.head;
        while (n!=null){
            LinkedList<Triangle> o = (LinkedList<Triangle>) n.obj;
            LinkedList.Node r =  o.head;
            Triangle p = (Triangle) r.obj;
            add(p);
            n=n.next;
        }
    }
    public Triangle[] arr(){
        Triangle[] a = new Triangle[graph.size];
        LinkedList.Node t = graph.head;
        int i=0;
        while (t!=null){
            LinkedList<Triangle> r = (LinkedList<Triangle>) t.obj;
            LinkedList.Node op = r.head;
            a[i] = (Triangle) op.obj;
            i++;
            t=t.next;
        }
        return a;
    }

    public static void main(String[] args) {

    }
}
