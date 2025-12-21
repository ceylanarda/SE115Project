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
                Scanner sc=new Scanner(file);
                //System.out.println("Dosya açıldı: " + fileName); //Kontrolüm için
                if(sc.hasNextLine()){
                    sc.nextLine();
                }
                while (sc.hasNextLine()){
                    String line = sc.nextLine();
                    if(line.trim().isEmpty()) continue;

                    //20,Oil,3000 gibi olucak
                    String[] parts = line.split(",");
                    // Araya virgül koymak için.
                    if(parts.length==3){
                        int day = Integer.parseInt(parts[0].trim());//Boşlukları kaldırıcak.
                        String namecomm = parts[1].trim();//Boşluk kaldırıcak isimdeki.
                        int profit = Integer.parseInt(parts[2].trim());//Aynı şekilde

                        int commindex = getCommIndex(namecomm);

                        if(day>=1 && day <= DAYS && commindex!=-1){
                            data[m][day-1][commindex]=profit;
                        }//else{
                           // System.out.println("Geçersiz veri: " + line + " Index " + commindex);
                        //}
                    }
                }
                sc.close();
            } catch (Exception e) {
                //System.out.println("Dosya okuma hatası " + fileName);
                //e.printStackTrace();
                // Exception olmaması ve göstermemesi için.
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if(month<0 || month>=MONTHS){return "Invalid-Month";}

        int maxProfit = -10000000; // En küçük sayı başlangıç say.
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
        if(month<0 || month>=MONTHS || day<1 || day > DAYS){return -99999;}//geçersiz değer girmesi için.
        int total = 0;

        for(int c=0;c<COMMS;c++){ //belli gün ve aydaki emtiaları toplar.
            total += data[month][day-1][c];
        }
        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        int idx = getCommIndex(commodity);
        if(idx==-1||from>to||to>DAYS){
            return -99999;
        }
        int total = 0;

        for(int m=0;m<MONTHS;m++){
            for(int d = from; d <= to; d++){
                total += data[m][d-1][idx];
            }
        }
        return total;
    }

    public static int bestDayOfMonth(int month) {
        if(month<0||month>MONTHS){
        return -1; }

        int maxprofit=-100000000;
        int bestDay=-1;

        for(int d=0;d<DAYS;d++){//28 günü gez
            int dailyTotal=0;
            for(int c = 0;c<COMMS;c++){//O günün comm(emtia) toplar.
                dailyTotal+=data[month][d][c];
            }
            if(dailyTotal>maxprofit){
                maxprofit=dailyTotal;
                bestDay=d+1; //index 0'ken gün 1'dir
            }
        }
        return bestDay;
    }
    
    public static String bestMonthForCommodity(String comm) {
        int idx=getCommIndex(comm);
        if(idx==-1){return "Invalid-Commodity";}

        int maxProfit=-1000000000;
        int bestmonthindex=-1;

        for(int m=0;m<MONTHS;m++){
            int monthtotal=0;
            for(int d=0;d<DAYS;d++){
                monthtotal+=data[m][d][idx];
            }
            if(monthtotal>maxProfit){
                maxProfit=monthtotal;
                bestmonthindex=m;
            }
        }
        return months[bestmonthindex];
    }

    public static int consecutiveLossDays(String comm) {
        int idx = getCommIndex(comm);
        if (idx == -1) {
            return -1;
        }

        int maxStreak = 0;
        int currStreak =0;

        for(int m=0;m<MONTHS;m++){
            for(int d=0;d<DAYS;d++){
                if(data[m][d][idx]<0){
                    currStreak++;
                }
                else {
                    if(currStreak>maxStreak){
                        maxStreak=currStreak;
                    }
                    currStreak=0;
                }
            }
        }
        if(currStreak>maxStreak){
            currStreak=maxStreak;
        }
        return maxStreak;
    }
    
    public static int daysAboveThreshold(String comm, int threshold) {
        int idx = getCommIndex(comm);
        if(idx==-1){
            return -1;
        }
        int count=0;
        for(int m=0;m<MONTHS;m++){
            for(int d=0;d<DAYS;d++){
                if(data[m][d][idx]>threshold){
                    count++;
                }
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if(month<0||month>MONTHS){
        return -99999;}

        int maxSwing=0;
        for(int d=1;d<DAYS;d++){
            int profitToday=0;
            int profitYesterday=0;
            for(int c=0;c<COMMS;c++){
                profitToday+=data[month][d][c];
                profitYesterday+=data[month][d-1][c];
            }

            int swing = Math.abs(profitToday-profitYesterday);
            if(swing>maxSwing){
                maxSwing=swing;
            }
        }
        return maxSwing;
    }
    
    public static String compareTwoCommodities(String c1, String c2) {
        int idx1 = getCommIndex(c1);
        int idx2 = getCommIndex(c2);

        if(idx1==-1||idx2==-1){
        return "Invalid commodity.";}

        int total1 = 0;
        int total2 = 0;

        for(int m=0;m<MONTHS;m++){
            for(int d=0;d<DAYS;d++){
                total1 += data[m][d][idx1];
                total2 += data[m][d][idx2];
            }
        }
        if(total1>total2){
            return c1 + " is better by " + (total1-total2);
        }
        else if(total2>total1){
            return c2 + " is better by " + (total2-total1);
        }
        else {
            return "They are equal.";
        }
    }
    
    public static String bestWeekOfMonth(int month) {
        if(month < 0 || month >= MONTHS) { return "INVALID_MONTH"; }
        int[] profitsweek = new int[4];

        for(int d=0;d<DAYS;d++){
            int dailytotal=0;
            for(int c =0;c<COMMS;c++){
                dailytotal += data[month][d][c];
            }
            int wIndex = d/7;
            profitsweek[wIndex]+=dailytotal;
        }
        int maxValue = -10000000;
        int bestWeek = -1;

        for(int i =0;i<4;i++){
            if(profitsweek[i]>maxValue){
                maxValue=profitsweek[i];
                bestWeek=i;
            }
        }
        return "Week " + (bestWeek + 1);
    }
    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}