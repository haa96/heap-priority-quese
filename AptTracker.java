import java.util.*;
import java.io.*;
public class AptTracker{
    static OperationPQ byRentPQ = new OperationPQ(); 
    static OperationPQ bySqPQ = new OperationPQ();
    static Scanner scanner = new Scanner(System.in);
    static boolean finished = true;
   

    public static void addApartment(){
        System.out.print("Enter street address (e.g. 1234 abcd st.) : ");
        String address = scanner.nextLine();
        System.out.print("Enter apartment number (e.g., 1234) : ");
        String aptNum = scanner.nextLine();
        System.out.print("Enter city (e.g. Pittsburgh) :");
        String city = scanner.nextLine();
        System.out.print("Enter zipcode (e.g. 12345) : ");
        int zipcode = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Monthly rent (in US dollor) : ");
        double rent = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Square footage (e.g. 550.45) : ");
        double sqFT = scanner.nextDouble();
        scanner.nextLine();
        Apartment node = new Apartment(address, aptNum, city, zipcode, rent, sqFT);
        byRentPQ.add(node, 'r');           
        bySqPQ.add(node, 's');
        }// case 1
    public static void updateApartment(){
        System.out.println("Please enter the street address: ");
        String address = scanner.nextLine();
        System.out.println("Enter the apartment number: ");
        String aptNum = scanner.nextLine();
        System.out.println("Enter the zipcode: ");
        int zip = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Want to update the price? (y)Yes / (n)No ]: ");
        String userRsp = scanner.nextLine();
        char yORn = userRsp.charAt(0);  
        if(yORn =='y'){
        System.out.println("Enter the new price: ");
        double newRent = scanner.nextDouble(); 
        scanner.nextLine();
        byRentPQ.update(address, aptNum, newRent, 'r');
        bySqPQ.update(address, aptNum, newRent, 's');
        } 
        else finished = false;
                    
    }// case 2
    public static void removeApartment(){
        System.out.println("Enter street address (e.g. 1234 abcd st.) : ");
        String address = scanner.nextLine();
        System.out.println("Enter apartment number (e.g., 1234) : ");
        String aptNum = scanner.nextLine();
        System.out.println("Enter zipcode (e.g. 12345) : ");
        int zipcode = scanner.nextInt();
        scanner.nextLine();
        byRentPQ.delete(address,aptNum, 'r');
        bySqPQ.delete(address,aptNum,'s');
        System.out.println("Apartment: ["+ address+". #" +aptNum+ "] has been removed!");
    }// case 3

    public static void getCheapestRent(){
        if (byRentPQ.size() == 0)
            System.out.println("There are no apartments to display!");
        else byRentPQ.cheapestRent();
        System.out.println();
        System.out.println("[Total apartments in queue: "+ bySqPQ.size()+" ]");
    }// case 4

    public static void getLargestSqFt(){
        if (bySqPQ.size() == 0)
            System.out.println("There are no apartments to display!");
        else bySqPQ.largestSqFt();
        System.out.println();
        System.out.println("[Total apartments in queue: "+ bySqPQ.size()+" ]");
    }// case 5

    public static void getCheapestByCity(){
        System.out.println("City: ");
        String city = scanner.nextLine();
        byRentPQ.cheapestByCity(city);

    } // case 6

    public static void getLargestByCity(){
        System.out.println("City: ");
        String city = scanner.nextLine();
        bySqPQ.largestByCity(city);
    }// case 7

    public static void main(String [] args) throws Exception {

        BufferedReader bf = new BufferedReader(new FileReader("apartments.txt"));
        String fileLine;
        while((fileLine = bf.readLine()) != null) {
            if(fileLine.charAt(0) == '#') continue;
            String[] data = fileLine.split(":");
            Apartment fromFile = new Apartment(data[0], data[1], data[2], Integer.parseInt(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]));
            byRentPQ.add(fromFile,'r');
            bySqPQ.add(fromFile,'s');
            }
        System.out.println("||---------------( APARTMENT TRACKER )---------------||");
        do{
            System.out.println("  --------------------------------------------------- ");
            System.out.println("| Please enter what you would like to do:             |");
            System.out.println("| 1.  Add  Apartment                                  |");
            System.out.println("| 2.  Update Apartment                                |");
            System.out.println("| 3.  Remove Apartment                                |");
            System.out.println("| 4.  Lowest Rent of All                              |");
            System.out.println("| 5.  Largest Apartment of All                        |");
            System.out.println("| 6.  Lowest Rent By City                             |");
            System.out.println("| 7.  Largest Apartment By City                       |");
            System.out.println("| 8.  Exit                                            |");
            System.out.println("  ---------------------------------------------------- ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch(option){
            case 1:
                System.out.println();
                addApartment();
                break;
            case 2:
                System.out.println();
                updateApartment();
                break;
            case 3:
                System.out.println();
                removeApartment();
                break;
            case 4:
                System.out.println();
                getCheapestRent();   
                break;
            case 5:
                System.out.println();
                getLargestSqFt();
                break;
            case 6:
                System.out.println();
                getCheapestByCity();
                break;
            case 7:
                System.out.println();
                getLargestByCity();
                break;
            case 8: 
                System.out.println();
                System.out.println("Bye! ");
            System.exit(0);
            }
           char response;
        do {
            System.out.println("Press: ( m ) for main menu. ( e ) to Exit ");
            String userRsp = scanner.nextLine().toLowerCase();
            response = userRsp.charAt(0);
            if(response == 'm' || response =='e') break;
            }while(true); // inner do

        if(response == 'm')
            finished = false;
        else    
            finished = true;
        }while(!finished);// outter do    
    }//MAIN
}//CLASS 