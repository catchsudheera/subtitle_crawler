import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dammina on 8/17/14.
 */
public class format_sub_apocalypto {
    public static void main(String [] args){
        new format_sub_apocalypto().format();
    }
    public void format(){
        File file=new File("/home/dammina/Work/subtitles/Apocalypto.2006.1080p.Bluray.x264.anoXmous.srt");
        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader(file)));
            String line="";

            //patterns
            String pattern1 = "^\\d+$"; // line with just an integer
            String pattern2 = "^\\d{2}:\\d{2}:\\d{2},[0-9]* --> \\d{2}:\\d{2}:\\d{2},[0-9]*$"; // line with time definition
            Pattern r1 = Pattern.compile(pattern1);
            Pattern r2 = Pattern.compile(pattern2);

            Matcher m;

            while(!line.equals("3")){
                line=scan.nextLine();
            }
            String prev_line="";
            String db_line="";
            while(!line.equals("451")){

                line = scan.nextLine();

                if(r1.matcher(line).matches())
                    continue;

                if(r2.matcher(line).matches())
                    continue;

                if(line.equals(""))
                    continue;
                if(line.contains("\"")){
                    line=line.replaceAll("\""," ");
                }
                while(line.contains("(") && line.contains(")")){
                    line = line.substring(0,line.lastIndexOf("("))+line.substring(line.lastIndexOf(")")+1);
                }
                if(line.contains("...")){
                    int index01=line.indexOf("...");
                    line = line.substring(0,index01)+line.substring(index01+3);
                }
                if(line.contains(".")){
                    db_line = prev_line+line.substring(0,line.indexOf(".")+1);
//                    System.out.println(db_line);
                    write_to_DB(db_line);
                    prev_line = line.substring(line.indexOf(".")+1)+" ";
                }
                else if(line.contains("?")){
                    db_line=prev_line+line.substring(0,line.indexOf("?")+1);
//                    System.out.println(db_line);
                    write_to_DB(db_line);
                    prev_line = line.substring(line.indexOf("?")+1)+" ";
                }
                else if(line.contains("!")){
//                    System.out.println(prev_line+line.substring(0,line.indexOf("!")+1));
                    prev_line = line.substring(line.indexOf("!")+1)+" ";
                }
                else {
                    prev_line=prev_line+line+" ";
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void write_to_DB(String dialogue){
        int index=0;
        int i=0;
        char a='a';
        while(a<128){
            a=dialogue.charAt(i);
            i++;
        }
        dialogue=dialogue.substring(i-1);
        System.out.println(dialogue);
        java.sql.Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/apocalypto?useUnicode=yes&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
        String user = "dammina1";
        String password = "damminapw";
        try
        {
            con = DriverManager.getConnection(url, user, password);
            Statement st = (Statement) con.createStatement();

            st.executeUpdate("INSERT INTO dialogues (dialogue)" + "VALUES ('"+dialogue+"')");

            con.close();
        }
        catch (SQLException e) {
            System.out.println(dialogue+ "EXCEPTION!!!");
            e.printStackTrace();

        }
    }
}