/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dateProcessorPackage;

import dateProcessorPackage.Exceptions.*;

/**
 *
 * @author
 *
 * Fredo Ronan
 * 
 */
public class DateProcessor {
    private String date;
    private String format;
    private int start, end;
    
    /*        
        SUPPORTED FORMAT / FORMAT TANGGAL YANG DI DUKUNG
        == List dari berbagai format tanggal yang di dukung pada kelas ini ==
    
        Day First
        dd/mm/yyyy
        dd-mm-yyyy
        dd mm yyyy
    
        Month First
        mm/dd/yyyy
        mm-dd-yyyy
        mm dd yyyy
    
        Year First
        yyyy/mm/dd
        yyyy-mm-dd
        yyyy mm dd
    */
    
    // Date and Format constructor (can be use imediately when needed just by creating the new Instance of this class)
    public DateProcessor(String inputDate, String format){
        this.date = inputDate;
        this.format = format;
    }
    
    // Format only constructor (use method createDate(String input) to insert date
    public DateProcessor(String format) throws DateProcessorFormatLengthException, DateProcessorFormatInvalidException {
        this.date = "";
        
        if(format.length()!=10){
            // Length Exception
            throw new DateProcessorFormatLengthException("\n\t[!] Format must be 10 characters length [!]");
        } else if(checkFormat(format)!='+'){
            throw new DateProcessorFormatInvalidException("\n\t[!] Invalid Character for parameter Format when creating DateProcessor object [!]\n\t"
                                                           + "'" + checkFormat(format) + "'");
        } else {
            this.format = format;
        }
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 
        PRIVATE METHOD
        
        These private method only use for operation inside this class
    */
    
    
    // untuk mendapatkan posisi index dari String date sesuai format yang di inginkan
    private int getPositionIndexDate(String find){
        for(int i = 0; i<format.length(); i++){
            if(format.equalsIgnoreCase(find)){
                return i;
            }
        }
        
        return -1;
    }
    
    // cek apakah index selanjutnya masih mengandung karakter yang sama dengan yang dicari
    private boolean isNext(int startIndex, String compare){
        return format.charAt(startIndex + 1) == compare.charAt(startIndex);
    }
    
    // parsing date sesuai format yang di berikan
    private String parseDate(String getWhat){
        // Untuk mendapatkan nilai tanggal hari menggunakan fungsi getPositionIndexDate() untuk mendapatkan charAt(index)
        // dimana hal tersebut adalah mulai di temukannya karakter yang di cari
        // lalu traversal ke karakter - karakter yang di cari
        if(getWhat.equalsIgnoreCase("Day")){
            // dd
            start = end = getPositionIndexDate("d");
            
            while(isNext(start, format)){
                end++;
            }
            
            return format.substring(start, end);
        }
        
        // Untuk mendapatkan nilai bulan menggunakan fungsi yang sama seperti di atas termasuk dengan
        // semua mekanismenya
        if(getWhat.equalsIgnoreCase("Month")){
            // mm
            start = end = getPositionIndexDate("m");
            
            while(isNext(start, format)){
                end++;
            }
            
            return format.substring(start, end);
        }
        
        // Untuk mendapatkan nilai tahun menggunakan fungsi yang sama seperti di atas termasuk dengan
        // semua mekanismenya
        if(getWhat.equalsIgnoreCase("Year")){
            // yyyy
            start = end = getPositionIndexDate("y");
            
            while(isNext(start, format)){
                end++;
            }
            
            return format.substring(start, end);
        }
        
        return "";
    }
    
    private char checkFormat(String formatInput){
        for(int i = 0; i<formatInput.length(); i++){
            if(formatInput.charAt(i) == 47 || formatInput.charAt(i) == 45 || formatInput.charAt(i) == 109
               || formatInput.charAt(i) == 100 || formatInput.charAt(i) == 121 || formatInput.charAt(i) == 32){
               if(validFormat(formatInput)){
                   // pass
               } else {
                   return ' ';
               }
            } else {
                return formatInput.charAt(i);
            }
        }
        
        return '+';
    }
    
    private boolean validFormat(String formatInput){
        int countD = 0;
        int countM = 0;
        int countY = 0;
        for(int i = 0; i<formatInput.length(); i++){
            if(formatInput.charAt(i) == 'd'){
                countD++;
            }
            
            if(formatInput.charAt(i) == 'm'){
                countM++;
            }
            
            if(formatInput.charAt(i) == 'y'){
                countY++;
            }
        }
        
        return countD == 2 && countM == 2 && countY == 4;
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 
        PUBLIC METHOD
        
        These public method can be access outside this class
    */
    
    // Public Method untuk mendapatkan nilai Hari dari tanggal yang di inputkan (String date)
    public String getDay(){
        return parseDate("Day");
    }
    
    // Public Method untuk mendapatkan nilai Bulan dari tanggal yang di inputkan (String date)
    public String getMonth(){
        return parseDate("Month");
    }
    
    // Public Method untuk mendapatkan nilai Tahun dari tanggal yang di inputkan (String date)
    public String getYear(){
        return parseDate("Year");
    }
    
    // Public Method untuk mendapatkan format yang sudah di setting di awal pembuatan object dari kelas ini
    public String getFormat(){
        return "[ " + this.format + " ]";
    }
    
    // Method yang di sediakan untuk mengecek apakah inputan tanggal dari user sudah sesuai
    // dengan format yang di inginkan atau belum
    public boolean isDateValid(String date){
        if(date.charAt(2) == format.charAt(2) && date.charAt(5) == format.charAt(5)){
            return true;
        } else {
            System.out.println("\n\t[!] INVALID DATE FORMAT [!]\n\tExpected : " + format);
        }
        
        return false;
    }
    
    public void createDate(String inputDate) throws DateProcessorDateException {
        if(isDateValid(inputDate)){
            this.date = inputDate;
        } else {
            throw new DateProcessorDateException("\n\t[!] INVALID DATE FORMAT [!]\n\tThe declared format is : " + this.format);
        }
    }
}
