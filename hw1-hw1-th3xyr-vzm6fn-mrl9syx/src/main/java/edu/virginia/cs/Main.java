package edu.virginia.cs;

import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.poi.ss.usermodel.*;

import java.io.File;

public class Main {
    public static void main(String[] args) throws IOException {
        //Professor Commented: Make sure you updated the build.gradle file to point to the right main class
        //opencsv source: www.geeksforgeeks.org, https://www.youtube.com/watch?v=sgGGjisdNPA
        ArrayList<State> list = new ArrayList<State>();
        int repNum = 435;
        int totalPop = 0;
        int stateNumber = 0;
        boolean Huntington = true;

        if (args.length > 1){
            repNum = Integer.parseInt(args[1]);
        }

        if(args.length>2)
        {
            if(args[2].equals("--hamilton"))
            {
                Huntington = !Huntington;
            }
            else{
                System.out.println("Only Accept Huntington-Hill method or Hamilton method");
                System.exit(0);
            }
        }

        /// adds data to list if it is a CSV
        if (args[0].substring(args[0].length() - 3 ).equals("csv")){

            CSVReader readCensus = new CSVReader(new FileReader(args[0]));


            String[] nextRecord;
            // source: GeeksforGeeks, url: https://www.geeksforgeeks.org/reading-csv-file-java-using-opencsv/

            if(readCensus.readNext() == null){
                System.out.println("The list is empty.");  //Prompt the list is empty and avoid crashing.
                System.exit(0); //Ending the Program
            }

            while((nextRecord = readCensus.readNext()) != null) {
                try {
                    State state = new State(nextRecord[0],Integer.parseInt(nextRecord[1]), 0);

                    if(state.n<0)
                    {
                        System.out.println("Wrong Population Entered!");
                        System.exit(0);
                    }

                    list.add(state);
                    totalPop += state.n;
                    stateNumber++;
                } catch (Exception e){}
            }

            readCensus.close();

            /// adds data to list if it is an excel file
        } else if (args[0].substring(args[0].length() - 3 ).equals("lsx")){

            // source: simplesolution.dev, url: https://simplesolution.dev/java-how-to-iterate-over-sheets-rows-and-cells-of-excel-file-using-apache-poi/
            File file = new File(args[0]);

            try(Workbook workbook = WorkbookFactory.create(file)) {
                for(Sheet sheet : workbook) {
                    for(Row row : sheet) {
                        try {
                            State state = new State(row.getCell(0).getStringCellValue(), ((int)row.getCell(1).getNumericCellValue()), 0);

                            if(state.n<0)
                            {
                                System.out.println("Wrong Population Entered!");
                                System.exit(0);
                            }
                            list.add(state);
                            totalPop += state.n;
                            stateNumber++;
                        } catch (Exception e){}
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        int totalReps = 0;
        double averagePerRep = totalPop / repNum;
        ///// Alexander Hamilton's Algorithm ///////
        if(!Huntington) {
            for (int i = 0; i < list.size(); i++) {
                int numReps = (int) Math.floor(list.get(i).n / averagePerRep);
                double remainder = (list.get(i).n / averagePerRep) - numReps;
                list.get(i).setRemainder(remainder);
                list.get(i).setRep(numReps);
                totalReps += numReps;
            }


            ///// Selection Sort ///////   Credit: https://stackoverflow.com/questions/41955427/selection-sort-using-arraylist
            for (int i = 0; i < list.size(); i++) {
                int pos = i;
                for (int j = i; j < list.size(); j++) {
                    if (list.get(j).getRemainder() > list.get(pos).getRemainder())
                        pos = j;
                }
                // Swap min (smallest num) to current position on array
                State max = list.get(pos);
                list.set(pos, list.get(i));
                list.set(i, max);
            }

            int remaining = repNum - totalReps;
            // Add Seat
            for (int i = 0; i < remaining; i++) {
                if (i < list.size()) {
                    list.get(i).add();
                } else {
                    list.get(i % list.size()).add();
                }
                totalReps++;
            }
        }

        ///// Huntington–Hill Method ///////
        else {
            if (repNum < stateNumber) {
                System.out.println("Not Enough Representatives!");
                System.exit(0);
            }
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setRemainder(list.get(i).getRep());
                    double quota = list.get(i).n / averagePerRep;
                    double geometric_mean = Math.sqrt(Math.floor(quota) * (Math.floor(quota) + 1));
                    if (quota > geometric_mean) {
                        list.get(i).setRep((int) Math.ceil(quota));
                    } else {
                        list.get(i).setRep((int) Math.floor(quota));
                    }
                    totalReps += list.get(i).getRep();
                }
                if(totalReps>repNum){
                    totalReps = 0;
                    averagePerRep = Math.ceil(averagePerRep) + 1;
                    for (int i = 0; i < list.size(); i++) {
                        double quota = list.get(i).remainder / averagePerRep;
                        double geometric_mean = Math.sqrt(Math.floor(quota) * (Math.floor(quota) + 1));
                        if (quota > geometric_mean) {
                            list.get(i).setRep((int) Math.ceil(quota));
                        } else {
                            list.get(i).setRep((int) Math.floor(quota));
                        }
                        totalReps += list.get(i).getRep();
                    }
                }
        }

        ///// Alphabetical Sort ///////
            Collections.sort(list);

        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).name + " - [" + list.get(i).n + "]");
        }
    }
}