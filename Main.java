// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};

    //data için arrayimiz.
    public static int [] [] [] data=new int[MONTHS][DAYS][COMMS];


    private static int getCommIndex(String commodity){
        for(int i = 0;i<COMMS;i++){
            if (commodities[i].equals(commodity)){//stringi inte eşitlemek için .equals.
                return i;
            }
        }
        return -1; //hata kodu
    }


    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for(int m =0;m<MONTHS;m++){
            String fileName= "Data_Files/" + months[m] + ".txt";
            File file = new File(fileName);

            try {
                Scanner sc=new Scanner(System.in);
                while (sc.hasNextLine()){
                    String line = sc.nextLine();
                    //20,Oil,3000 gibi olucak
                    String[] parts = line.split(",");
                    // Araya virgül koymak için.
                    if(parts.length==3){
                        int day = Integer.parseInt(parts[0].trim());//Boşlukları kaldırıcak.
                        String namecomm = parts[1].trim();//Boşluk kaldırıcak isimdeki.
                        int profit = Integer.parseInt(parts[2].trim());//Aynı şekilde

                        int commindex = getCommIndex(namecomm);

                        if(day>=1 && day < DAYS && commindex!=-1){
                            data[m][day-1][commindex]=profit;
                        }
                    }
                }
            } catch (Exception e) {
                // Exception olmaması ve göstermemesi için.
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if(month<0 || month>=MONTHS){return "DUMMY";}

        int maxProfit = Integer.MIN_VALUE; // En küçük sayı başlangıç say.
        int bestCommindex = -1;

        for(int c=0;c<COMMS;c++){ //Emtiaları gez
            int currentCommtotal= 0;

            for(int d =0;d<DAYS;d++){ //Her emtianın o ayki toplamı.
                currentCommtotal+=data[month][d][c];
            }
            if(currentCommtotal>maxProfit){//Eğer maxtan fazlaysa yeni max o.
                maxProfit=currentCommtotal;
                bestCommindex=c;
            }
        }
        return commodities[bestCommindex]+ " " + maxProfit;
    }

    public static int totalProfitOnDay(int month, int day) {
        if(month<0 || month>=MONTHS || day<1 || day > DAYS){return 1234;}//geçersiz değer girmesi için.
        int total = 0;

        for(int c=0;c<COMMS;c++){//belli gün ve aydaki emtiaları toplar.
            total += data[month][day-1][c];
        }
        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) { 
        return 1234; 
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}