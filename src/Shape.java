
public class Shape implements ShapeInterface
{
    int vertex;
    int connected;
    int points;
    LinkedList<LinkedList<Triangle>> mylist;
    LinkedList<PointInterface> pointlist;
    LinkedList<LinkedList<Triangle>> ExtNeibList;
    LinkedList<Triangle> comp ;
    LinkedList<Graph> master;
    public Shape(){
        vertex=0;
        mylist = new LinkedList<>();
        pointlist = new LinkedList<>();
        ExtNeibList=new LinkedList<>();
        connected=0;
        points=0;
        master = new LinkedList<>();
        comp = new LinkedList<>();
    }

    @Override
    public boolean ADD_TRIANGLE(float[] triangle_coord) {
        PointInterface a;
        PointInterface b;
        PointInterface c;

        if(colcord(triangle_coord))  return false;
        else{
            if(find(triangle_coord[0], triangle_coord[1], triangle_coord[2])){
                 a= findObj(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
            }
            else {
                a = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
                ((Point) a).repeat = points;
                points++;
                pointlist.insert(a);
            }
            if(find(triangle_coord[3], triangle_coord[4], triangle_coord[5])){
                b= findObj(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
            }
            else {
                b = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
                ((Point) b).repeat = points;
                points++;
                pointlist.insert(b);
            }
            if(find(triangle_coord[6], triangle_coord[7], triangle_coord[8])){
                c= findObj(triangle_coord[6], triangle_coord[7], triangle_coord[8]);
            }
            else {
                c = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);
                ((Point) c).repeat = points;
                points++;
                pointlist.insert(c);
            }

                boolean flag = true;
                Point ap = (Point) a; ap.setNeibPoint((Point) b); ap.setNeibPoint((Point) c);
                Point bp = (Point) b; bp.setNeibPoint((Point) a); bp.setNeibPoint((Point) c);
                Point cp = (Point) c; cp.setNeibPoint((Point) b); cp.setNeibPoint((Point) a);

                Triangle t = new Triangle(a,b,c);
                t.setNum(vertex);
                vertex++;
                ap.setNeibTri(t);  bp.setNeibTri(t); cp.setNeibTri(t);
                ap.setNeibEdge(t.ca); ap.setNeibEdge(t.bc); ap.setNeibEdge(t.ab);
                bp.setNeibEdge(t.bc); bp.setNeibEdge(t.ab); bp.setNeibEdge(t.ca);
                cp.setNeibEdge(t.ca); cp.setNeibEdge(t.bc); cp.setNeibEdge(t.ab);
                //  graph formations
            LinkedList.Node q = master.head;
            Graph p =null;
            int connector =0;
            while (q!=null){
                Graph g = (Graph) q.obj;
                if(g.connection(t)) {
                    g.add(t);
                    connector++;
                    if(connector>1){
                        g.combine(p);
                        master.remove(p);
                    }

                    p = g;
                }

                q=q.next;
            }
            if(connector==0) {
                Graph g = new Graph();
                g.add(t);
                master.insert(g);
                comp.insert(t);
                connected++;
            }
            if(connector>1){
                connected = connected-connector+1;
            }

            // graph formation

            LinkedList<Triangle> temp = new LinkedList<>();
                LinkedList<Triangle> tempExt = new LinkedList<>();
                temp.insert(t);
                LinkedList.Node<LinkedList<Triangle>> foo = mylist.head;
                LinkedList.Node<LinkedList<Triangle>> fook = ExtNeibList.head;
                while(foo!=null){
                    LinkedList<Triangle> foo1 = foo.obj;
                    LinkedList.Node<Triangle> re = foo1.head;
                    if(similarity(re.obj,t)){
                        foo1.insert(t);
                        re.obj.setNeighbour(t);
                        t.setNeighbour(re.obj);
                        temp.insert(re.obj);
                        flag=false;
                    }
                    foo = foo.next;
                }

            mylist.insert(temp);
                tempExt.insert(t);
                while(fook!=null){
                    LinkedList<Triangle> foo1 = fook.obj;
                    LinkedList.Node<Triangle> re = foo1.head;
                    if(Posimilarity(re.obj,t)){
                        foo1.insert(t);
                        tempExt.insert(re.obj);
                    }
                    fook = fook.next;
                }
            ExtNeibList.insert(tempExt);
                return true;
        }

    }
    public boolean find(float x, float y, float z){
        LinkedList.Node<Point> foo = pointlist.head;
        while (foo!=null){
            if(foo.obj.getX()==x && foo.obj.getY()==y && foo.obj.getZ()==z) return true;
            foo=foo.next;
        }
        return false;
    }
    public Point findObj(float x, float y, float z){
        LinkedList.Node<Point> foo = pointlist.head;
        while (foo!=null){
            if(foo.obj.getX()==x && foo.obj.getY()==y && foo.obj.getZ()==z) return foo.obj;
            foo=foo.next;
        }
        return null;
    }


    public boolean similarity(Triangle p, Triangle q){
        Edge[] arr1 = p.etri;
        Edge[] arr2 = q.etri;
        boolean flag = false;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(check(arr1[i],arr2[j])){
                    arr1[i].shared++; arr2[j].shared++;
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
    public boolean Posimilarity(Triangle p, Triangle q){
        PointInterface[] arp = p.tri;
        PointInterface[] arp1 = q.tri;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(arp[i].getXYZcoordinate()==arp1[j].getXYZcoordinate()){
                    return true;
                }
            }
        }

        return false;
    }
    @Override
    public int TYPE_MESH() {
        LinkedList.Node<LinkedList<Triangle>> foo = mylist.head;
        int total=0;
        int prop =0;
        int semprop =0;
        int improp =0;
        int misc =0;
        while(foo!=null) {
            LinkedList<Triangle> foo1 = foo.obj;
            LinkedList.Node<Triangle> re = foo1.head;
            Edge[] p = re.obj.etri;
            for (int i = 0; i < 3; i++) {
                if (p[i].sharedCount() == 2) prop++;
                if (p[i].sharedCount() == 1) misc++;
                if (p[i].sharedCount() <= 2) semprop++;
                if (p[i].sharedCount() > 2) improp++;
                total++;
            }
            foo=foo.next;
        }
            if(misc>=1&&semprop==total) return 2;
            else if(prop==total) return 1;
            else return 3;

    }


    @Override
    public EdgeInterface[] BOUNDARY_EDGES() {   //////////////// SORT
        LinkedList.Node<LinkedList<Triangle>> foo = mylist.head;
        LinkedList<Edge> ed = new LinkedList<>();
        while(foo!=null) {
            LinkedList<Triangle> foo1 = foo.obj;
            LinkedList.Node<Triangle> re = foo1.head;
            Edge[] p = re.obj.etri;
            for (int i = 0; i < 3; i++) {
                if(p[i].sharedCount()==1) ed.insert(p[i]);
            }
            foo=foo.next;
        }
        EdgeInterface[] ret = new EdgeInterface[ed.size];
        LinkedList.Node<Edge> ac = ed.head;
        int i=0;
        while(ac!=null){
            ret[i]= ac.obj;
            i++;
            ac = ac.next;
        }
        if(ed.size!=0){
            MergeSort m = new MergeSort();
            m.bisectArr(ret,0,ret.length-1);
            return ret;
        }
        return null;
    }

    @Override
    public int COUNT_CONNECTED_COMPONENTS() {
        return master.size;
    }

    @Override
    public TriangleInterface[] NEIGHBORS_OF_TRIANGLE(float[] triangle_coord) {
        LinkedList<Triangle> jk = new LinkedList<>();
        LinkedList.Node<LinkedList<Triangle>> foo = mylist.head;
        while(foo!=null){
            LinkedList<Triangle> foo1 = foo.obj;
            LinkedList.Node<Triangle> re = foo1.head;
            if(sameTri(re.obj.tri,triangle_coord)) {
                jk = foo1;
                break;
            }
            foo = foo.next;
        }
        int p = jk.size();
        TriangleInterface[] por = new TriangleInterface[p-1];
        if(p>0) {
            int k=0;
            LinkedList.Node<Triangle> re = jk.head.next;
            while(re!=null && k<p){
                por[k] = re.obj;
                k++;
                re=re.next;
            }
            return por;
        }
        return null;
    }
    public boolean sameTri(PointInterface[] arr , float[] cord){
        float[] a = new float[3]; a[0] = cord[0]; a[1] = cord[1]; a[2] = cord[2];
        float[] b = new float[3]; b[0] = cord[3]; b[1] = cord[4]; b[2] = cord[5];
        float[] c = new float[3]; c[0] = cord[6]; c[1] = cord[7]; c[2] = cord[8];
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        for(int i=0;i<3;i++){
            if(arr[i].getXYZcoordinate()[0]==a[0]&&arr[i].getXYZcoordinate()[1]==a[1] && arr[i].getXYZcoordinate()[2]==a[2]) flag1= true;

            else if(arr[i].getXYZcoordinate()[0]==b[0]&&arr[i].getXYZcoordinate()[1]==b[1] && arr[i].getXYZcoordinate()[2]==b[2]) flag2= true;

            else if(arr[i].getXYZcoordinate()[0]==c[0]&&arr[i].getXYZcoordinate()[1]==c[1] && arr[i].getXYZcoordinate()[2]==c[2]) flag3= true;

        }
        if(flag1&&flag2&&flag3) return true;
        return false;
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        EdgeInterface[] abc = new  EdgeInterface[3];
        LinkedList.Node<LinkedList<Triangle>> foo = mylist.head;

        while(foo!=null){
            LinkedList<Triangle> foo1 = foo.obj;
            LinkedList.Node<Triangle> re = foo1.head;
            if(sameTri(re.obj.tri,triangle_coord)) {
                abc[0] = re.obj.etri[0]; abc[1] = re.obj.etri[1]; abc[2] = re.obj.etri[2];
                return abc;
            }
            foo = foo.next;
        }
        return null;
    }

    @Override
    public PointInterface[] VERTEX_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        PointInterface[] abc = new  PointInterface[3];
        LinkedList.Node<LinkedList<Triangle>> foo = mylist.head;
        while(foo!=null){
            LinkedList<Triangle> foo1 = foo.obj;
            LinkedList.Node<Triangle> re = foo1.head;
            if(sameTri(re.obj.tri,triangle_coord)) {
                abc[0] = re.obj.tri[0]; abc[1] = re.obj.tri[1]; abc[2] = re.obj.tri[2];
                return abc;
            }
            foo = foo.next;
        }

        return null;
    }

    @Override
    public TriangleInterface[] EXTENDED_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        LinkedList<Triangle> jk = new LinkedList<>();
        LinkedList.Node<LinkedList<Triangle>> foo = ExtNeibList.head;
        while(foo!=null){
            LinkedList<Triangle> foo1 = foo.obj;
            LinkedList.Node<Triangle> re = foo1.head;
            if(sameTri(re.obj.tri,triangle_coord)) {
                jk = foo1;
                break;
            }
            foo = foo.next;
        }
        int p = jk.size();
        TriangleInterface[] por = new TriangleInterface[p-1];
        if(p>0) {
            int k=0;
            LinkedList.Node<Triangle> re = jk.head.next;
            if(re!=null) {
                while (re != null) {
                    por[k] = re.obj;
                    k++;
                    re = re.next;
                }
                return por;
            }
        }
        return null;

    }

    @Override
    public TriangleInterface[] INCIDENT_TRIANGLES(float[] point_coordinates) {
        Point p = findObj(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
        if(p==null) return null;
        LinkedList<Triangle> abc = p.getNeibTri();
        TriangleInterface[] ret = new TriangleInterface[abc.size()];
        LinkedList.Node<Triangle> naan = abc.head;
        int i=0;
        while (naan!=null){
            ret[i] =  naan.obj;
            i++;
            naan=naan.next;
        }
        return ret;
    }

    @Override
    public PointInterface[] NEIGHBORS_OF_POINT(float[] point_coordinates) {
        Point p = findObj(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
        if(p==null) return null;
        LinkedList<Point> abc = p.getNeibPoint();
        PointInterface[] ret = new PointInterface[abc.size()];
        LinkedList.Node<Point> naan = abc.head;
        int i=0;
        while (naan!=null){
            ret[i] =  naan.obj;
            i++;
            naan=naan.next;
        }
        if(abc.size!=0)return ret;
        return null;
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBORS_OF_POINT(float[] point_coordinates) {
        Point p = findObj(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
        if(p==null) return null;
        LinkedList<EdgeInterface> abc = p.getNeibEdge();
        EdgeInterface[] ret = new EdgeInterface[abc.size()];
        LinkedList.Node<EdgeInterface> naan = abc.head;
        int i=0;
        while (naan!=null){
            ret[i] =  naan.obj;
            i++;
            naan=naan.next;
        }
        return ret;
    }

    @Override
    public TriangleInterface[] FACE_NEIGHBORS_OF_POINT(float[] point_coordinates) {
        return INCIDENT_TRIANGLES(point_coordinates);
    }

    @Override
    public boolean IS_CONNECTED(float[] triangle_coord_1, float[] triangle_coord_2) {
        LinkedList.Node<LinkedList<Triangle>> foo = mylist.head;
        LinkedList.Node<LinkedList<Triangle>> fooreq = mylist.head;
        int p =0;
        int req=-1;
        while(foo!=null){
            LinkedList<Triangle> foo1 = foo.obj;
            LinkedList.Node<Triangle> re = foo1.head;
            if(sameTri(re.obj.tri,triangle_coord_1)) {
                p = re.obj.num;
                break;
            }
            foo = foo.next;
        }
        while(fooreq!=null){
            LinkedList<Triangle> foo1 = fooreq.obj;
            LinkedList.Node<Triangle> re = foo1.head;
            if(sameTri(re.obj.tri,triangle_coord_2)) {
                req = re.obj.num;
                break;
            }
            fooreq = fooreq.next;
        }
        LinkedList.Node<LinkedList<Triangle>> foop = mylist.head;
        LinkedList<Triangle>[] graph = new LinkedList[mylist.size()];
        boolean[] visited = new boolean[mylist.size()];
        int i=0;
        while(foop!=null){
            graph[i] = foop.obj;
            i++;
            foop=foop.next;
        }
        for(int l=0;l<visited.length;l++){
            visited[l] = false;
        }
        maxDia(visited,graph,p);

        return visited[req];
    }


    @Override
    public TriangleInterface[] TRIANGLE_NEIGHBOR_OF_EDGE(float[] edge_coordinates) {
        Point a = findObj(edge_coordinates[0],edge_coordinates[1],edge_coordinates[2]);
        Point b = findObj(edge_coordinates[3],edge_coordinates[4],edge_coordinates[5]);
        if(a==null||b==null) return null;
        LinkedList.Node foo1 = a.neibTri.head;
        LinkedList<Triangle> qwe = new LinkedList<>();

        while (foo1!=null){
            LinkedList.Node foo2 = b.neibTri.head;
            while (foo2!=null){
                if(foo1.obj==foo2.obj) {
                    qwe.insert((Triangle) foo2.obj);
                    break;
                }
                foo2 = foo2.next;
            }
            foo1=foo1.next;
        }
        TriangleInterface[] ret = new TriangleInterface[qwe.size];
        LinkedList.Node c = qwe.head;
        int i=0;
        while (c != null) {
            ret[i] = (TriangleInterface) c.obj;
            i++;
            c=c.next;
        }
        return ret;
    }

    @Override
    public int MAXIMUM_DIAMETER() {
        LinkedList.Node<LinkedList<Triangle>> foop = mylist.head;
        LinkedList<Triangle>[] graph = new LinkedList[mylist.size()];
        LinkedList<Integer> dia = new LinkedList<>();
        boolean[] visited = new boolean[mylist.size()];
        int i=0;

        while(foop!=null){
            graph[i] = foop.obj;
            i++;
            foop=foop.next;
        }
        for(int p=0;p<visited.length;p++){
            visited[p] = false;
        }
        int max =0;
        LinkedList.Node ab = master.head;
        while (ab!=null){
            Graph g = (Graph) ab.obj;
            LinkedList.Node op = g.graph.head;
            while (op!=null){
                LinkedList<Triangle> te = (LinkedList<Triangle>) op.obj;
                LinkedList.Node tp = te.head;
                for(int p=0;p<visited.length;p++){
                    visited[p] = false;
                }
                Triangle t = (Triangle) tp.obj;
                int p = maxDia(visited,graph,t.getNum());
                if(p>max){
                    max=p;
                }

                op = op.next;
            }


            ab = ab.next;
        }
        return  max;
    }

    @Override
    public PointInterface[] CENTROID() {      ///////// SORT
        PointInterface[] ret = new PointInterface[master.size];
        LinkedList.Node<Graph> foo = master.head;
        int i=0;
        while(foo!=null){
            boolean flag1 = false; boolean flag2 =false; boolean flag3 = false;
            Triangle t = foo.obj.arr()[0];
            Point as = (Point) t.a; Point bs = (Point) t.b; Point cs = (Point) t.c;
            LinkedList<Triangle> er = as.neibTri; LinkedList<Triangle> qr = bs.neibTri; LinkedList<Triangle> tr = cs.neibTri;
            LinkedList.Node<Triangle> q1 = er.head; if(q1==null){flag1=true;}
            while (q1!=null){
                if(foo.obj.trilist.find(q1.obj)==null){flag1=true; break;}
                q1=q1.next;
            }
            LinkedList.Node<Triangle> q2 = qr.head; if(q2==null){flag2=true;}
            while (q2!=null){
                if(foo.obj.trilist.find(q2.obj)==null){flag2=true; break;}
                q2=q2.next;
            }          LinkedList.Node<Triangle> q3 = tr.head; if(q3==null){flag3=true;}
            while (q3!=null){
                if(foo.obj.trilist.find(q3.obj)==null){flag3=true; break;}
                q3=q3.next;
            }
            if(!flag1) ret[i] = CENTROID_OF_COMPONENT(t.a.getXYZcoordinate());
            else if(!flag2) ret[i] = CENTROID_OF_COMPONENT(t.b.getXYZcoordinate());
             else ret[i] = CENTROID_OF_COMPONENT(t.c.getXYZcoordinate());

            // centroid query........
//            ret[i] = CENTROID_OF_COMPONENT(t.a.getXYZcoordinate());
            i++;
            foo = foo.next;
        }
        SortPoint so = new SortPoint();
        so.bisectArr(ret,0,ret.length-1);
        return ret;
    }

    @Override
    public PointInterface CENTROID_OF_COMPONENT(float[] point_coordinates) {
        Point p = findObj(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
        LinkedList<Triangle> abc = p.getNeibTri();
        Triangle as = (Triangle) abc.head.obj;
        LinkedList.Node<LinkedList<Triangle>> foop = mylist.head;
        LinkedList<Triangle>[] graph = new LinkedList[mylist.size()];
        boolean[] visited = new boolean[mylist.size()];
        boolean[] visitpoint = new boolean[pointlist.size];
        int i=0;
        while(foop!=null){
            graph[i] = foop.obj;
            i++;
            foop=foop.next;
        }
        for(int l=0;l<visited.length;l++){
            visited[l] = false;
        }
        for(int l=0;l<visitpoint.length;l++){
            visitpoint[l] = false;
        }

        return centro(visited,graph,as.getNum(),visitpoint);
    }

    @Override
    public PointInterface[] CLOSEST_COMPONENTS() {
        PointInterface[] ret = new PointInterface[2];
        Graph[] g = new Graph[master.size];
        LinkedList.Node top = master.head;
        float min = 100000000;
//        while(top!=null){
//            LinkedList.Node to = master.head;
//            Graph g = (Graph) top.obj;
//            Point[] op = g.extreme();
//            while(to!=null){
//                Graph gd = (Graph) to.obj;
//                Point[] tp = gd.extreme();
//               if(g!=gd) {
//                   if(disMin(op,tp)<min) {
//                       min = disMin(op,tp);
//                       ret = retMin(op, tp);
//                   }
//               }
//                to=to.next;
//            }
//            top=top.next;
//        }
        LinkedList.Node fo = master.head;
        int i=0;
        while (fo!=null){
            g[i] = (Graph) fo.obj;
            fo=fo.next;
            i++;
        }
//        while(fo!=null){
//            Graph g = (Graph) fo.obj;
//            LinkedList.Node foo = master.head;
//            LinkedList<LinkedList<Triangle>> re = g.graph;
//            Triangle[] asad = new Triangle[re.size];
//            LinkedList.Node tr = re.head;
//            int k =0;
//            while (tr!=null){
//                LinkedList<Triangle> p = (LinkedList<Triangle>) re.head.obj;
//                LinkedList.Node rte = p.head;
//                asad[k] =(Triangle)  rte.obj;
//                k++;  tr=tr.next;
//            }
//            while (foo!=null){
//                Graph gp = (Graph) foo.obj;
//                LinkedList<LinkedList<Triangle>> rt = gp.graph;
//                if(gp!=g){
//                    Triangle[] asd = new Triangle[rt.size];
//                    LinkedList.Node tu = rt.head;
//                    int h =0;
//                    while (tu!=null){
//                        LinkedList<Triangle> p = (LinkedList<Triangle>) rt.head.obj;
//                        LinkedList.Node rte = p.head;
//                        asd[h] = (Triangle) rte.obj;
//                        h++;  tu=tu.next;
//                    }
//                    PointInterface[] x = newmindis(asad,asd);
//                    PointInterface o =  x[0];
//                    PointInterface m =  x[1];
//                    float as = distance(o,m);
//                    if(as<min){
//                        System.out.println("flag");
//                        min = as;
//                        ret = x;
//                    }
//                }
//                foo = foo.next;
//            }
//            fo = fo.next;
//        }
        for(int j=0;j<g.length;j++){
            Triangle[] g_ = g[j].arr();
            for(int k=0;k<g.length;k++){
                if(g[j]!=g[k]){
                    for(int o=0;o<g_.length;o++){
                        for(int t=0;t<g[k].arr().length;t++){
                            Triangle a =g_[o]; Triangle b=g[k].arr()[t];
                            for(int h=0;h<a.tri.length;h++){
                                for(int u=0;u<b.tri.length;u++){
                                    float as = distance(a.tri[h],b.tri[u]);
                                    if(as<min){
                                        min =as;
                                        ret[0] = a.tri[h]; ret[1] = b.tri[u];
                                    }
                                }
                            }


                        }
                    }
                }
            }
        }

        return ret;
    }
    public PointInterface[] newmindis(Triangle[] a, Triangle[] b ){
        PointInterface[] op = new Point[2];
        float min =1000000;
        for(int i=0;i<a.length;i++){
            for(int j=0;j<b.length;j++){
                PointInterface[] t = a[i].tri;
                PointInterface[] tr = b[j].tri;
                float f = disMin(t,tr);
                if(f<min){
                    min = f;
                    op = retMin(t,tr);
                }
            }
        }

        return op;
    }

    public PointInterface[] retMin(PointInterface[] a, PointInterface[] b){
//        float q = distance(a[0],b[0]);
//        float w = distance(a[0],b[1]);
//        float e = distance(a[1],b[0]);
//        float r = distance(a[1],b[1]);
        PointInterface[] op = new Point[2];
        float min = 10000000;
//        if(q<=w&&q<=e&&q<=r) return new Point[]{a[0], b[0]};
//        if(w<q&&w<=e&&w<=r) return new Point[]{a[0], b[1]};
//        if(e<q&&e<w&&e<=r) return new Point[]{a[1], b[0]};
        for(int i=0;i<a.length;i++){
            for(int j=0;j<b.length;j++){
                float p = distance(a[i],b[j]);
                if(p<min){
                    min = p;
                    op[0] = a[i]; op[1] = b[j];
                }
            }
        }
        return op;
    }
    public float disMin(PointInterface[] a, PointInterface[] b){
        float min = 1000000;
        for(int i=0;i<a.length;i++){
            for(int j=0;j<b.length;j++){
                float p = distance(a[i],b[j]);
                if(p<min){
                    min = p;
                }
            }
        }
        return min;
    }
    public float distance(PointInterface a, PointInterface b){
        return (a.getX()-b.getX())*(a.getX()-b.getX()) + (a.getY()-b.getY())*(a.getY()-b.getY()) + (a.getZ()-b.getZ())*(a.getZ()-b.getZ());
    }

    public float min(Triangle a){
        float p = a.ab.edgelength(); float q = a.bc.edgelength(); float r = a.ca.edgelength();
        if(p<=q&&p<=r) return p;
        else if(q<p&&q<=r) return q;
        else return r;
    }
    public Edge minE(Triangle a){
        float p = a.ab.edgelength(); float q = a.bc.edgelength(); float r = a.ca.edgelength();
        if(p<=q&&p<=r) return a.ab;
        else if(q<p&&q<=r) return a.bc;
        else return a.ca;
    }


    public boolean colcord(float[] point){
        float x1 = point[0]; float y1 = point[1]; float z1 = point[2];
        float x2 = point[3]; float y2 = point[4]; float z2 = point[5];
        float x3 = point[6]; float y3 = point[7]; float z3 = point[8];
        float a = x1-x2; float b = y1-y2; float c = z1-z2; float d = x3-x2; float e = y3-y2; float f = z3-z2;
        float ar = b*f-c*e; float br = a*e-b*d; float cr = a*f-c*d;
        if(ar*ar<0.000000001&&br*br<0.000000001&&cr*cr<0.000000001) return true;
         return false;
    }

    public void search(boolean[] visited, LinkedList<Triangle>[] graph, int start){
        visited[start] = true;
        LinkedList.Node temp = graph[start].head.next;
        while (temp!=null){
            Triangle t =(Triangle) temp.obj;
            if(!visited[t.getNum()]){
                search(visited,graph,t.getNum());
            }
            temp=temp.next;
        }

    }
    public int maxDia(boolean[] visited, LinkedList<Triangle>[] graph, int start){
        visited[start] = true;
        LinkedList.Node temp = graph[start].head;
        int nodecount = 0;
        int height = 0;
        LinkedList<Triangle> tri = new LinkedList<>();
        tri.insert((Triangle) temp.obj);
        while(true){
            boolean flag = true;
            nodecount = tri.size();
            if(tri.size==0) return height;
            height++;
            while(nodecount>0){
                Triangle t = tri.top();
                visited[t.getNum()] = true;
                LinkedList.Node<Triangle> pe = graph[t.getNum()].head;
                while(pe!=null){
                    if(!visited[pe.obj.getNum()] ) {
                        tri.insert(pe.obj);
                        visited[pe.obj.getNum()]=true;
                        flag=false;
                    }
                    pe = pe.next;
                }
                nodecount--;
            }
            if(flag) height--;
        }

    }
    public PointInterface centro(boolean[] visited, LinkedList<Triangle>[] graph, int start, boolean[] visitpoint){
        float[] cen = {0,0,0};
        visited[start] = true;
        LinkedList.Node temp = graph[start].head;
        int nodecount = 0;
        int num = 3;
        LinkedList<Triangle> tri = new LinkedList<>();
        tri.insert((Triangle) temp.obj);
        Triangle tk = (Triangle) temp.obj;
        Point oa = (Point) tk.a; Point ob = (Point) tk.b; Point oc = (Point) tk.c;
        cen[0] = oa.getX() + ob.getX() + oc.getX(); cen[1] = oa.getY() + ob.getY() + oc.getY(); cen[2] = oa.getZ() + ob.getZ() + oc.getZ();
        visitpoint[oa.repeat] = true; visitpoint[ob.repeat] = true;  visitpoint[oc.repeat] = true;
        while(true){
            nodecount = tri.size();
            if(tri.size==0) {
                cen[0] = cen[0]/num; cen[1] = cen[1]/num; cen[2] = cen[2]/num;
                PointInterface x = new Point(cen[0],cen[1],cen[2]);
                return x;
            }
            while(nodecount>0){
                Triangle t = tri.top();
                visited[t.getNum()] = true;
                LinkedList.Node<Triangle> pe = graph[t.getNum()].head;
                while(pe!=null){
                    if(!visited[pe.obj.getNum()] ) {
                        Point al = (Point) pe.obj.a;
                        Point bl = (Point) pe.obj.b;
                        Point cl = (Point) pe.obj.c;
                        if(!visitpoint[al.repeat]) { cen[0] +=pe.obj.a.getX(); cen[1] +=pe.obj.a.getY();cen[2] +=pe.obj.a.getZ();  visitpoint[al.repeat]=true; num++;}
                        if(!visitpoint[bl.repeat]) {cen[0] +=pe.obj.b.getX(); cen[1] +=pe.obj.b.getY();cen[2] +=pe.obj.b.getZ(); visitpoint[bl.repeat] = true; num++;}
                        if(!visitpoint[cl.repeat]){cen[0] +=pe.obj.c.getX(); cen[1] +=pe.obj.c.getY();cen[2] +=pe.obj.c.getZ(); visitpoint[cl.repeat]=true; num++;}
                        tri.insert(pe.obj);
                        visited[pe.obj.getNum()]=true;
                    }
                    pe = pe.next;
                }
                nodecount--;
            }
        }

    }

//    public static void main(String[] args) {
//        Shape x = new Shape();
//        float[] tri = { -1 ,0 ,0 ,0 ,-1 ,0 ,0 ,0 ,3};
//        float[] trix = {-1 ,0 ,0 ,0 ,-3 ,0 ,0, 1 ,0};
//        float[] trixc = { -1 ,0 ,0, 0, -3, 0, 0, 0 ,3};
//        float[] triz = {  -2,0 ,0 ,0 ,-3 ,0 ,0 ,0 ,3};
//        float[] tria = {  -2, 0, 0, 0 ,-3 ,0 ,0 ,0 ,4};
//        float[] tris = { -2, 0, 0, 0, 0 ,3 ,0, 0, 4};
//        float[] trist = { 3, 0, 0, 0, 0 ,4 ,0, 0, 5};
//        float[] trit = { 3, 0, 0, 12, 0 ,3 ,0, 0, 5};
//        float[] tritas = { 3 ,4 ,5 ,6 ,7 ,8, 9, 10, 12};
//        float[] p = {0,-1,0};
//        System.out.println(x.ADD_TRIANGLE(tri));
//        System.out.println(x.MAXIMUM_DIAMETER() + " Diameter");
//        System.out.println(x.ADD_TRIANGLE(trix));
//        System.out.println(x.MAXIMUM_DIAMETER() + " Diameter");
//                for(int i=0;i<x.CENTROID().length;i++){
//            PointInterface t = x.CENTROID()[i];
//            System.out.println(t.getX() +" "+t.getY()+" "+t.getZ());
//        }
//        System.out.println(x.ADD_TRIANGLE(trixc));
//        System.out.println(x.MAXIMUM_DIAMETER() + " Diameter");
//        for(int i=0;i<x.CENTROID().length;i++){
//            PointInterface t = x.CENTROID()[i];
//            System.out.println(t.getX() +" "+t.getY()+" "+t.getZ());
//        }
//        System.out.println(x.ADD_TRIANGLE(triz));
//        System.out.println(x.MAXIMUM_DIAMETER() + " Diameter");
//        System.out.println(x.ADD_TRIANGLE(tria));
//        for(int i=0;i<x.CENTROID().length;i++){
//            PointInterface t = x.CENTROID()[i];
//            System.out.println(t.getX() +" "+t.getY()+" "+t.getZ());
//        }
//        System.out.println(x.ADD_TRIANGLE(tris));
//        System.out.println(x.ADD_TRIANGLE(trist));
//        for(int i=0;i<x.CENTROID().length;i++){
//            PointInterface t = x.CENTROID()[i];
//            System.out.println(t.getX() +" "+t.getY()+" "+t.getZ());
//        }
//////        System.out.println(x.ADD_TRIANGLE(trit));
////        System.out.println(x.ADD_TRIANGLE(tritas));
////        System.out.println(x.TYPE_MESH());
//        System.out.println(x.COUNT_CONNECTED_COMPONENTS()+" components");
//
//        System.out.println(x.MAXIMUM_DIAMETER() + " Diameter");
//        LinkedList.Node a = x.master.head;
//        while (a!=null){
//            Graph g = (Graph) a.obj;
//            for(int i=0;i<g.arr().length;i++){
//                System.out.print(g.arr()[i].getNum()+ " ");
//            }
//            System.out.println();
//            a=a.next;
//        }
//    }


}

