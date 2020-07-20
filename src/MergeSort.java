public class MergeSort {


    public MergeSort(){

    }

    public void bisectArr(EdgeInterface[] arr, int low, int high){
        if(low<high) {
            int mid = (high + low) / 2;
            bisectArr(arr,low, mid);
            bisectArr(arr,mid + 1, high);
            merge(arr,low,mid,high);
        }
    }

    public void merge(EdgeInterface[] arr,int low, int mid, int high){
        EdgeInterface[] tempL = new EdgeInterface[mid-low+1];
        EdgeInterface[] tempR = new EdgeInterface[high-mid];
        for (int i=0; i<tempL.length; i++)
            tempL[i] = arr[low + i];
        for (int j=0; j<tempR.length; j++)
            tempR[j] = arr[mid + 1+ j];
        int i = 0; int j = 0; int k = low;
        while (i<tempL.length&&j<tempR.length){
            Edge ed = (Edge) tempL[i];
            Edge edg = (Edge) tempR[j];
            if(ed.edgelength()<=edg.edgelength()){
                arr[k] = tempL[i];
                i++;
            }
            else  {
                arr[k] = tempR[j];
                j++;
            }
            k++;
        }
        while (i<tempL.length){
            arr[k] = tempL[i];
            i++;
            k++;
        }
        while (j<tempR.length){
            arr[k] = tempR[j];
            j++;
            k++;
        }

    }

//    public static void main(String[] args) {
//        EdgeInterface[] x = new EdgeInterface[5];
//        x[0] = new Edge(new Point(1,2,3),new Point(1,5,7));
//        x[1] = new Edge(new Point(1,2,3),new Point(1,8,7));
//        x[2] = new Edge(new Point(1,2,3),new Point(1,5,3));
//        x[3] = new Edge(new Point(1,2,3),new Point(3,5,7));
//        x[4] = new Edge(new Point(1,2,3),new Point(1,3,2));
//        MergeSort s = new MergeSort();
//        s.bisectArr(x,0,x.length-1);
//        for (int i=0;i<x.length;i++){
//            Edge p = (Edge) x[i];
//            System.out.println(p.edgelength());
//        }
//    }

}
