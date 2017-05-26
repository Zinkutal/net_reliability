package com.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zinkutal on 5/26/2017.
 */
public class ReadTXT {
    private String txtSource = "src/data/input/txt/";
    private String csvSource = "src/data/input/csv/";
    private String[] txtInputNames;
    private String[][] txtFiles;

    public Boolean getTXT() throws IOException {

        File folder = new File(txtSource);
        File[] listOfFiles = folder.listFiles();

        int filesCount = 0;

        if (listOfFiles != null){
            txtInputNames =  new String[listOfFiles.length];
            txtFiles =  new String[listOfFiles.length][2];
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    System.out.println("Txt input files name" + file.getName());
                    txtInputNames[filesCount] = file.getName();
                    filesCount++;
                }
            }
            int currentFile = 0;
            for (String name : txtInputNames) {

                try(BufferedReader br = new BufferedReader(new FileReader(txtSource + name))) {

                    StringBuilder sb1 = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    String line = br.readLine();

                    int stringCount = 0;

                    while (line != null) {
                        if(stringCount < 1){
                            sb1.append(line);
                            line = br.readLine();
                            stringCount++;
                            txtFiles[currentFile][0] = sb1.toString();
                            System.out.println(txtFiles[currentFile][0]);
                        } else {
                            sb2.append(line);
                            txtFiles[currentFile][1] = sb2.toString();
                            System.out.println(txtFiles[currentFile][1]);
                            break;
                        }
                    }

                } catch (IOException e){
                    e.printStackTrace();
                }
                currentFile++;
            }
        } else {
            System.out.println("No input txt files were found.");
        }

        int k = 0;
        for (String fname : txtInputNames) {
            String[] name = fname.split("\\.");

            String csvFile = csvSource + name[0] + ".csv";
            FileWriter writer = new FileWriter(csvFile);

            String[] arr  = txtFiles[k][0].split(",");
            String[] arr2 = txtFiles[k][1].split(",");
            String[][] temp = new String[arr.length][2];
            String[][] temp2 = new String[arr.length][2];

            for (int x =0; x < arr.length; x++) {
                for (int z =0;z < temp.length; z++) {
                    if (temp[z][0]!=null){
                        if(temp[z][0].equalsIgnoreCase(arr[x])){
                            temp[z][1] += " " + arr2[x];
                        }
                    }
                }
                temp[x][0] = arr[x];
                temp[x][1] = arr2[x];
            }

            int csv_line = 0;
            for (int x =0; x < temp.length; x++) {
                //if (temp[x][0]!=null){
                    for (int z = 0; z < temp.length; z++) {
                        if (!temp[x][0].equalsIgnoreCase(temp[z][0])) {
                            for (int d =0; d < temp.length; d++) {
                                if (temp2[csv_line][0]!=null){
                                    if (!temp2[csv_line][0].equalsIgnoreCase(temp[d][0])) {
                                        temp2[csv_line][0] = temp[x][0];
                                        temp2[csv_line][1] = temp[x][1];
                                        csv_line++;
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    }
                //}
            }

            CSVUtils.writeLine(writer, Arrays.asList("id","relations"));
            for (int j =0; j < temp2.length;j++){
                if (temp2[j][0]!=null){
                    List<String> list = new ArrayList<>();
                    list.add(temp2[j][0]);
                    list.add(temp2[j][1]);
                    CSVUtils.writeLine(writer, list);
                }
            }

            writer.flush();
            writer.close();

            k++;
        }


        return false;

    }
}
