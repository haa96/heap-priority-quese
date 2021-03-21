


public class HashGauge{
        int index;
        boolean hashed;
        public HashGauge(){
            index =0;
            hashed =false;
        }
        public HashGauge(int toLoc, boolean wasHashed){
            index = toLoc;
            hashed = wasHashed;
        }
    }