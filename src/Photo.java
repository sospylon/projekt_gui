import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Photo {
    String path;
    String date;
    String author;
    String tags;
    String place;

    public String[] getTagsSplit() {
        return tagsSplit;
    }

    public void setTagsSplit(String[] tagsSplit) {
        this.tagsSplit = tagsSplit;
    }

    String[] tagsSplit;

    public void setPath(String path) {
        this.path = path;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setPlace(String place) {
        this.place = place;
    }


    public String getPath() {
        return path;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getTags() {
        return tags;
    }

    public String getPlace() {
        return place;
    }

    public Photo (){}

    public Photo (String data) {

            //Pattern check = Pattern.compile("([A-Z]:\\\\.*\\\\.*\\.png|[A-Z]:\\\\.*\\\\.*\\.jpg);(.*,);([0-9]*\\.[0-9]{1,2}\\.[0-9]{1,2});(.*);(.*)");
            //Matcher regexMatcher = check.matcher(data);
            //System.out.println(regexMatcher.find());

        Pattern check = Pattern.compile(".*\\.jpg\\b|.*\\.png\\b");
        String[] divided = data.split(";");
        Matcher regexMatcher = check.matcher(divided[0]);

        if (regexMatcher.find()) {
            this.path = divided[0];
            if(!divided[1].isEmpty()) {
                this.tags = divided[1];
                System.out.println("not null");
            } else this.tags= "No Data";
            if(!divided[2].isEmpty()) {
                System.out.println("not null");
                this.date = divided[2];
            }else this.date = "No Data" ;
            if(!divided[3].isEmpty()) {
                System.out.println("not null");
                this.author = divided[3];
            } else this.author ="No Data";
            if(!divided[4].isEmpty()) {
                System.out.println("not null");
                this.place = divided[4];
            } else this.place = "No Data";

                this.tagsSplit = divided[1].split(",");


        }
        else {
            JOptionPane.showMessageDialog(null, "wrong image extension");

        }
        /*
        this.path = regexMatcher.group(1);
        this.date = regexMatcher.group(3);
        this.author = regexMatcher.group(4);
        this.tags = regexMatcher.group(2);
        this.place = regexMatcher.group(5);
        */
}



    public void show()
    {
        System.out.println(this.path+" "+ this.date+" "+this.author +" "+this.tags +" "+this.place);
    }
}
