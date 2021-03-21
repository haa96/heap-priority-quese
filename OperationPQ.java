


public class OperationPQ{

    private Apartment [] heap;
    private int hashTable [];
    private int count;
    public OperationPQ(){heap = new Apartment[500];   hashTable= new int[1009];   count = 0;}
    public int size(){return count;}
    public void add(Apartment apartment, char OperationPQID){
        int i=0;
        int hashIndex = 0;
        while(i<heap.length){
        if(heap[i] == null)
        { hashIndex =i; break; }
        else if(heap[i] !=null && i ==heap.length-1){ // if full, resize heap
            Apartment [] newSizeHeap = new Apartment [heap.length*2];
            System.arraycopy(heap, 0, newSizeHeap, 0, heap.length);
            heap = newSizeHeap;
            hashIndex = i+1;// carry on to next index after resize
            break; 
            }
        i++;
        }
        Apartment toAdd = new Apartment(apartment.address, apartment.aptNum, apartment.city, apartment.zipcode, apartment.rent, apartment.sqFT);
        heap[hashIndex] = toAdd;
        HashGauge hashingSet = new HashGauge();
        if(OperationPQID =='r')
            hashingSet = rentOperationPQProperty(0, hashIndex, 0, hashingSet);
        if(OperationPQID == 's')
            hashingSet = sqFTOperationPQProperty(0, hashIndex, 0, hashingSet);
        if(!hashingSet.hashed)
            hash(apartment.address, apartment.aptNum, hashIndex);
        count++;
    }
    //get the hashTable location
    private int getHash(String address, String tempNum){
        int hashIT = 0;
        String toHash = address + " " + tempNum;
        for(int i = 0; i<toHash.length(); i++){
            hashIT += ((int)toHash.charAt(i) +(256^(toHash.length()-i)));
        }
       
        hashIT = hashIT % hashTable.length;
        return hashIT;
    }
    // horner hashing
    private void hash(String address, String aptNum, int loc){
        int horner = 0;
        String tempy = address + " " + aptNum;
        for(int i = 0; i<tempy.length(); i++){
            horner += ((int)tempy.charAt(i) +(256^(tempy.length()-i)));
        }
        horner = horner % hashTable.length;
        hashTable[horner] = loc;
    }
    
    public void update (String address, String aptNum, double newRent, char pqID){    
        int hashIT =0;
        int heapIndex = 0;
        hashIT = getHash(address,aptNum);
        heapIndex = hashTable[hashIT];
        heap[heapIndex].rent = newRent;
        HashGauge hashVals = new HashGauge();
        if(pqID == 'r')
            hashVals = rentOperationPQProperty(0, heapIndex,0, hashVals);
    }

    public void delete (String address, String aptNum, char pqID){

            if(count ==0)
            {
                heap = new Apartment[128];
                hashTable = new int[1009];
                return;
            }
            else if(count ==1){
                if(heap[0].address.equals(address)){
                    Apartment aptToDelete = heap[1];
                    heap = new Apartment[128];
                    hashTable = new int[1009];
                    heap[0] = aptToDelete;
                    hash(heap[0].address, heap[0].aptNum, 0);
                    return;
                }
                else if(heap[1].address.equals(address)){
                    Apartment secondIndex = heap[0];
                    heap = new Apartment[128];
                    hashTable = new int[1009];
                    heap[0] = secondIndex;
                    hash(heap[0].address, heap[0].aptNum, 0);
                    return;
                }
            }
            int hashIndex =0;
            for (int i =0; i<heap.length; i++){
                if(heap[i] !=null && i ==heap.length-1){
                    Apartment [] newSizeHeap = new Apartment [heap.length*2];
                    System.arraycopy(heap, 0, newSizeHeap, 0, heap.length);
                    heap =newSizeHeap;
                    hashIndex = i+1;
                    break;
                }
                else if(heap[i] == null){
                    hashIndex =i;
                    break;
                }
            }
            hashIndex-=1;
            int hashIT = getHash(address, aptNum);
            int heapIndex = hashTable[hashIT];
            if(hashIT!=hashIndex){
                heap[heapIndex] = heap[hashIndex];
                hashTable[hashTable[getHash(heap[hashIndex].address, heap[hashIndex].aptNum)]] = heapIndex;
                heap[hashIndex] = null;
            }
            else{
                heap[heapIndex] = null;
            }

            if(pqID =='r')
            rentOperationPQProperty(0, heapIndex,1, null);
            else sqFTOperationPQProperty(0, heapIndex, 1,null);
            count--;      
    }
   
    //lowest rent in given city
    public void cheapestByCity(String city){
        Apartment apt = null;
        boolean filled = false;
        for(int i =0; i<heap.length;i++){
            if(heap[i] == null){}
            else if(heap[i].city.equals(city) && !filled){
                apt = heap[i];
                filled = true;
            }
            else if(heap[i].city.equals(city) && heap[i].rent < apt.rent){
                apt = heap[i];
            }
        } 
        if(apt != null){
            System.out.println("Address: " + apt.address);
            System.out.println("Apartment Number: " + apt.aptNum);
            System.out.println("City: " + apt.city);
            System.out.println("Zipcode: " + apt.zipcode);
            System.out.println("Rent: " + apt.rent);
            System.out.println("SqFt: " + apt.sqFT);
        }
         else{
            System.out.println("No apartments found in (" + city+") !!");
        }
    }
     public void cheapestRent(){
            System.out.println("Address: " + heap[0].address);
            System.out.println("Apartment Number: " + heap[0].aptNum);
            System.out.println("City: " + heap[0].city);
            System.out.println("Zipcode: " + heap[0].zipcode);
            System.out.println("Rent: " + heap[0].rent);
            System.out.println("SqFt: " + heap[0].sqFT);
     
        }
    public void largestSqFt(){
            System.out.println("Address: " + heap[0].address);
            System.out.println("Apartment Number: " + heap[0].aptNum);
            System.out.println("City: " + heap[0].city);
            System.out.println("Zipcode: " + heap[0].zipcode);
            System.out.println("Rent: " + heap[0].rent);
            System.out.println("SqFt: " + heap[0].sqFT);
    }
    public void largestByCity(String city){
        int i=0;
        Apartment apt = null;
        boolean inHeap = false;
        while(i<heap.length){
            if(heap[i] == null){}
            else if(heap[i].city.equals(city) && !inHeap){
                apt = heap[i];
                inHeap = true;
            }
            else if(heap[i].city.equals(city) && heap[i].sqFT > apt.sqFT){
                apt = heap[i];
            }
            i++;
        }
       
        if(apt !=null){
            System.out.println("Address: " + apt.address);
            System.out.println("Apartment Number: " + apt.aptNum);
            System.out.println("City: " + apt.city);
            System.out.println("Zipcode: " + apt.zipcode);
            System.out.println("Rent: " + apt.rent);
            System.out.println("SqFt: " + apt.sqFT);
        }
        else{
            System.out.println("No apartments found in (" + city+") !!");
        }
    }
    public HashGauge rentOperationPQProperty(int i, int originalIndex, int type, HashGauge child){
        
        if(heap[i] ==null && type ==0) return child;
        else{
            child = rentOperationPQProperty(2*i+1, originalIndex,0, child);
            child = rentOperationPQProperty(2*i+2, originalIndex, 0, child);
            if(heap[i].rent<(heap[(i-1)/2].rent)){
                if(i == originalIndex){
                    child.index = (i-1)/2;
                    child.hashed = true;
                }
                    Apartment apt = heap[i];
                    heap[i] = heap[(i-1)/2];
                    heap[(i-1)/2] = apt;
                    hashTable[getHash(heap[((i-1)/2)].address, heap[((i-1)/2)].aptNum)] = (i-1)/2;
                    hashTable[getHash(heap[i].address, heap[i].aptNum)]=i; 
                }
        return child;
        
            }       
    } 

    public HashGauge sqFTOperationPQProperty(int i, int originalIndex, int type, HashGauge child){
        
        if(heap[i] ==null && type ==0) return child;
        else{
            child = sqFTOperationPQProperty(2*i+1, originalIndex,0, child);
            child = sqFTOperationPQProperty(2*i+2, originalIndex, 0, child);
            if(heap[i].sqFT>(heap[(i-1)/2].sqFT)){
                if(i == originalIndex){
                    child.index = (i-1)/2;
                    child.hashed = true;
                }
                Apartment apt = heap[i];
                heap[i] = heap[(i-1)/2];
                heap[(i-1)/2] = apt;
                int iInHash = getHash(heap[i].address, heap[i].aptNum);
                hashTable[getHash(heap[((i-1)/2)].address, heap[((i-1)/2)].aptNum)] = (i-1)/2;
                hashTable[getHash(heap[i].address, heap[i].aptNum)]=i; 
            }
            }
        return child;  
    }
    
   
    
}