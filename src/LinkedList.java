

public class LinkedList<T> {
    Node head;
    int size;
    public LinkedList(){
        head = null;
        size=0;
    }


    static class Node<T> implements Comparable{
        Node next;
        T obj;
        public Node(T obj){
            this.obj = obj;
            next = null;
        }

        @Override
        public int compareTo(Object o) {
            return compareTo(o);
        }
    }
    public void insert(T obj){
        if(find(obj)!=null) return;
        Node node = new Node(obj);
        node.next = null;
        size++;
        if(head==null) {
            head = node;
            return;
        }
        Node temp = head;
        while(temp.next!=null){
            temp = temp.next;
        }
        temp.next=node;
    }

    public T find(T obj){
        Node temp = head;
        while(temp!=null){
            if(temp.obj==obj) return (T)temp.obj;
            temp=temp.next;
        }
        return null;
    }
    public int size(){
        return size;
    }
    public T top(){
        Node temp = head;
        if(temp!=null) {
            size--;
            head = temp.next;
            return (T) temp.obj;
        }

        return null;
    }
//    public void remove(T obj){
//        Node temp =head;
//        if(temp.obj==obj) {
//            top();
//            return;
//        }
//        Node p =null;
//        Node q =null;
//        while (temp.next!=null){
//            if(temp.next.obj==obj){
//                p=temp;
//                q=temp.next;
//                break;
//            }
//            temp.next=temp.next.next;
//        }
//        if(q!=null){
//            p.next=q.next;
//            size--;
//        }
//
//    }
    public  void remove(T obj)
    {
        Node temp = head;
        Node prev = null;
        if (temp != null && temp.obj == obj) {
            top();
            return;
        }

        while (temp != null && temp.obj != obj) {
            prev = temp;
            temp = temp.next;
        }
        if (temp == null) return;
        size--;
        prev.next = temp.next;
    }




//    public static void main(String[] args) {
//        LinkedList<Integer> a = new LinkedList<>();
//        LinkedList<Integer> b = new LinkedList<>();
//        a.insert(1);
//        a.insert(2);
//        a.insert(3);
//        b.insert(4);
//        b.insert(5);
//        a.remove(2);
//        Node t = a.head;
//        while (t!=null){
//            System.out.println(t.obj);
//            t=t.next;
//        }
//        System.out.println(a.size);
//    }

}
