import java.io.*;
import java.net.URLEncoder;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: dammina
 * Date: 7/10/14
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class format_sub {
    public static int count=0;
    public static void main(String [] args)
    {
        new format_sub().getFiles();
    }

    public void getFiles()
    {
        //you need change this path to the directory which contains subtitles in your system
        String RESOURCE_DIRECTORY_PATH = "/home/dammina/Work/subtitles";
        File directory = new File(RESOURCE_DIRECTORY_PATH);
        File[] listOfFiles = directory.listFiles();

        for(File file : Arrays.asList(listOfFiles))
        {
            format(file);
        }
    }
    public void format(File original_file){

        Scanner scan= null;

        String pattern1 = "^\\d+$"; //"(.*)(\\d+)(.*)";
        String pattern2 = "^\\d{2}:\\d{2}:\\d{2},[0-9]* --> \\d{2}:\\d{2}:\\d{2},[0-9]*$";
        Pattern r1 = Pattern.compile(pattern1);
        Pattern r2 = Pattern.compile(pattern2);
        Matcher m;

        String line,nextline;
        boolean dialogues_started;

        try
        {
            scan = new Scanner(new BufferedReader(new FileReader(original_file.getAbsoluteFile())));
            dialogues_started = false;
            System.out.println(original_file.getName()+" STARTED");
            while(scan.hasNext())
            {
                line = scan.nextLine();
                m = r1.matcher(line);
                if(m.matches())
                {
                    dialogues_started=true;
                    nextline = scan.nextLine();
                    m = r2.matcher(nextline);
                    if(!m.matches())
                    {
                        if(line.contains("'")) {
                            line = URLEncoder.encode(line,"UTF-8");
                            line.replace("'", "\\'");
                        }
                        if(nextline.contains("'")) {
                            nextline = URLEncoder.encode(line,"UTF-8");
                            nextline.replace("'", "\\'");
                        }
                        if(line.length()<500)
                            write_to_DB(line);
                        write_to_DB(nextline);
                    }
                }
                else if(line.contains("<b")){
                    while(!line.contains("</b")){
                        if(scan.hasNext())
                            line = scan.nextLine();
                        else
                            break;
                    }
                }
                else if(dialogues_started && !line.equals("") && !line.contains("<"))
                {
                    if(line.contains("'"))
                    {
                        line = URLEncoder.encode(line,"UTF-8");
                        line.replace("'","\\'");
                    }
                    if(line.length()<500)
                        write_to_DB(line);
                }
            }
            count++;
            System.out.println("File #"+count+" : "+original_file.getName()+" DONE");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    public void write_to_DB(String dialogue){
        java.sql.Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
//        String url = "jdbc:mysql://localhost:3306/sinhala_corpus?useUnicode=yes&characterEncoding=utf-8";
//        String user = "si-admin";
//        String password = "si-admin-pw";
        String url = "jdbc:mysql://localhost:3306/corpus?useUnicode=yes&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
        String user = "dammina";
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