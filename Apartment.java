


 public class Apartment{
        String address;
        String aptNum;
        String city;
        int zipcode;
        double rent;
        double sqFT;
        public Apartment(){
            address = null;
            aptNum =null;
            city = null;
            zipcode = 0;
            rent =0;
            sqFT =0;
        }
        public Apartment(String adr, String apt, String c, int zip, double rnt, double sq){
            address = adr;
            aptNum =apt;
            city = c;
            zipcode = zip;
            rent =rnt;
            sqFT =sq;
        }
    }