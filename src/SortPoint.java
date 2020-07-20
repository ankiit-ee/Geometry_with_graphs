public class SortPoint {

    public SortPoint(){
    }
    public void bisectArr(PointInterface[] arr, int low, int high){
        if(low<high) {
            int mid = (high + low) / 2;
            bisectArr(arr,low, mid);
            bisectArr(arr,mid + 1, high);
            merge(arr,low,mid,high);
        }
    }

    public void merge(PointInterface[] arr,int low, int mid, int high){
        PointInterface[] tempL = new PointInterface[mid-low+1];
        PointInterface[] tempR = new PointInterface[high-mid];
        for (int i=0; i<tempL.length; i++)
            tempL[i] = arr[low + i];
        for (int j=0; j<tempR.length; j++)
            tempR[j] = arr[mid + 1+ j];
        int i = 0; int j = 0; int k = low;
        while (i<tempL.length&&j<tempR.length){
            Point ed = (Point) tempL[i];
            Point edg = (Point) tempR[j];
            if(ed.getX()<=edg.getX()){
                if(ed.getX()==edg.getX()){
                    if(ed.getY()<=edg.getY()){
                        if(ed.getY()==edg.getY()){
                            if(ed.getZ()<=edg.getZ()){
                                arr[k]=tempL[i];
                                i++;
                            }
                            else  {
                                arr[k] = tempR[j];
                                j++;
                            }
                        }
                        else  {
                            arr[k] = tempL[i];
                            i++;
                        }
                    }
                    else  {
                        arr[k] = tempR[j];
                        j++;
                    }
                }
                else {
                    arr[k] = tempL[i];
                    i++;
                }
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
            j++; k++;
        }

    }





//    public static void main(String[] args) {
//        PointInterface[] x = new PointInterface[5];
//        x[0] = new Point(1,0,3); x[1] = new Point(1,5,8); x[2] = new Point(3,0,3);
//        x[3] = new Point(-1,6,3); x[4] = new Point(1,0,0);
//
//        SortPoint so =new SortPoint();
//        so.bisectArr(x,0,x.length-1);
//        for(int i=0;i<x.length;i++){
//            System.out.print("["+x[i].getX()+" "+x[i].getY()+" "+x[i].getZ()+"]  ");
//        }
//    }

}
