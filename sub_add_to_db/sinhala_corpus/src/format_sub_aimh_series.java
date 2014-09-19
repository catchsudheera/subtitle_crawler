import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by dammina on 9/7/14.
 */
public class format_sub_aimh_series {
    public static int line_no=1001;
    public static void main(String [] args){
        PrintWriter writer = null;
        File file;
        format_sub_aimh_series fsas = new format_sub_aimh_series();
        try {
            writer = new PrintWriter("/home/dammina/Work/aimh_series.sql", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.01.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"3","405");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.02.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"3","406");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.03.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"3","408");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.04.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"3","432");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.05.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"3","539");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.06.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"3","457");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.07.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"3","492");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.08.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"3","486");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.09.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"2","373");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.10.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"1","390");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.11.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"3","362");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.12.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"3","380");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.13.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"4","475");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.14.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"1","491");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.15.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"1","356");
        file=new File("/home/dammina/Work/subtitles/Autumn.In.My.Heart.16.DVDRip.XviD-MyT.srt");
        fsas.format(file,writer,"3","365");
        writer.close();
    }

    public void format(File file, PrintWriter writer,String begin,String end){

        String prev_line="";
        String db_line="";

        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader(file)));
            String line="";

            //patterns
            String pattern1 = "^\\d+$"; // line with just an integer
            String pattern2 = "^\\d{2}:\\d{2}:\\d{2},[0-9]* --> \\d{2}:\\d{2}:\\d{2},[0-9]*$"; // line with time definition
            String pattern3 = "^```.*```$";
            String pattern4 = "^```.*$";
            String pattern5 = "^.*```$";
            Pattern r1 = Pattern.compile(pattern1);
            Pattern r2 = Pattern.compile(pattern2);
            Pattern r3 = Pattern.compile(pattern3);
            Pattern r4 = Pattern.compile(pattern4);
            Pattern r5 = Pattern.compile(pattern5);

            while(!line.equals(begin)){
                line=scan.nextLine();
            }

            while(!line.equals(end)){

                line = scan.nextLine();

                if(r1.matcher(line).matches() || r2.matcher(line).matches() || r3.matcher(line).matches() || r4.matcher(line).matches() || r5.matcher(line).matches())
                    continue;

                if(line.equals(""))
                    continue;
                if(line.contains("\"")){
                    line=line.replaceAll("\""," ");
                }
                if(line.contains("<i>")){
                    line=line.replaceAll("<i>","");
                }
                if(line.contains("</i>")){
                    line=line.replaceAll("</i>","");
                }
                if(line.contains("-")){
                    line=line.replaceAll("-","");
                }
                if(line.contains("\'")){
                    line=line.replaceAll("\'"," ");
                }
                if(line.contains("`")){
                    line=line.replaceAll("`"," ");
                }
                while(line.contains("(") && line.contains(")")){
                    line = line.substring(0,line.lastIndexOf("("))+line.substring(line.lastIndexOf(")")+1);
                }
                if(line.contains("...")){
                    int index01=line.indexOf("...");
                    line = line.substring(0,index01)+line.substring(index01+3);
                }
                /*if(line.contains("..")){
                    int index01=line.indexOf("..");
                    line = line.substring(0,index01)+line.substring(index01+2);
                }*/
                if(line.contains(".")){
                    db_line = prev_line+line.substring(0,line.indexOf(".")+1);
                    db_line = set_the_starting_point_of_string(db_line);

                    write_to_file(db_line, writer);
                    prev_line = line.substring(line.indexOf(".")+1)+" ";
                }
                else if(line.contains("?")){
                    db_line=prev_line+line.substring(0,line.indexOf("?")+1);
                    db_line = set_the_starting_point_of_string(db_line);

                    write_to_file(db_line, writer);
                    prev_line = line.substring(line.indexOf("?")+1)+" ";
                }
                else if(line.contains("!")){
                    db_line=prev_line+line.substring(0,line.indexOf("!")+1);
                    db_line = set_the_starting_point_of_string(db_line);

                    write_to_file(db_line, writer);
                    prev_line = line.substring(line.indexOf("!")+1)+" ";
                }
                else {
                    prev_line=prev_line+line+" ";
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            System.out.println("## "+db_line);
            ex.printStackTrace();
        }
    }
    public String set_the_starting_point_of_string(String dialogue){
        String pattern1 = "[0-9]";
        Pattern r1 = Pattern.compile(pattern1);

        int i=0;
        char a='a';
        while(a<128){
            if(r1.matcher(a+"").matches())
                return dialogue;
            a=dialogue.charAt(i);
            i++;
        }
        dialogue=dialogue.substring(i-1);
        return dialogue;
    }
    public void write_to_file(String dialogue, PrintWriter writer){
        int b_index=0;
        char x;
        String to_write;
        for(int i=0;i<dialogue.length();i++){
            to_write="";
            x=dialogue.charAt(i);
            if(x=='.'){
                to_write=dialogue.substring(b_index,i+1);
                b_index=i+1;
            }
            else if(x=='!'){
                to_write=dialogue.substring(b_index,i+1);
                b_index=i+1;
            }
            else if(x=='?'){
                to_write=dialogue.substring(b_index,i+1);
                b_index=i+1;
            }
            if(!to_write.equals("")){
                to_write=final_format(to_write);
                System.out.println(to_write);
                writer.println("("+line_no+", '"+to_write+"', 0, '', 'no', 0, 0, 0, 0),");
                line_no++;
            }
        }
    }
    public String final_format(String dialogue){
        if(dialogue.contains("  ")){
            dialogue=dialogue.replaceAll("  "," ");
        }
        if(dialogue.contains("..")){
            dialogue=dialogue.replaceAll("..",".");
        }
        String pattern1 = "[0-9]";
        Pattern r1 = Pattern.compile(pattern1);
        int i=0;
        char a='a';
        while(a<128){
            if(r1.matcher(a+"").matches())
                return dialogue;
            a=dialogue.charAt(i);
            i++;
        }
        dialogue=dialogue.substring(i-1);
        return dialogue;
    }
}
