import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class projekt_gui_main  {
    public static void createAndShowGUI() {
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension winDim5 = new Dimension();
        Dimension winDim10 = new Dimension();
        Dimension winDim4 = new Dimension();
        Dimension winDim2 = new Dimension();

        JFrame mainFrame = new JFrame();
        mainFrame.addComponentListener(new ComponentAdapter() {
            public void  componentResized(ComponentEvent e){
                Component c = (Component)e.getSource();
                winDim2.height = c.getHeight();
                winDim2.width = c.getWidth()/2;
                winDim5.height = c.getHeight();
                winDim5.width = c.getWidth()/5;
                winDim4.height = c.getHeight();
                winDim4.width = c.getWidth()/4;
                winDim10.height = c.getHeight();
                winDim10.width = c.getWidth()/10;
            }
        });
        JLabel centerlabel = new JLabel();
        JPanel centerpanel = new JPanel();
        JPanel gridphots = new JPanel();
        JPanel borderphotos = new JPanel();
        JTextArea author = new JTextArea();
        JTextPane date= new JTextPane();
        JTextArea tags= new JTextArea();
        tags.setLineWrap(true);
        tags.setRows(8);

        JTextPane place = new JTextPane();
        JPanel righttextpanel = new JPanel();

        tags.setWrapStyleWord(true);
        JMenuItem addItem = new JMenuItem("Add Photo");
        JMenuItem openFileItem = new JMenuItem("Open library");
        JFileChooser fileChooser = new JFileChooser();
        ArrayList data = new ArrayList();
        ArrayList<Photo> photos = new ArrayList<Photo>();

        JScrollPane scrollphotosleft = new JScrollPane(gridphots);
        openFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openFileItem.addActionListener(e -> {
            int code = fileChooser.showOpenDialog(mainFrame);
            if (code == JFileChooser.APPROVE_OPTION) {
                gridphots.removeAll();
                String path = fileChooser.getSelectedFile().getPath();
                File file = new File(path);
                try {
                    Scanner scan = new Scanner(file);
                    while (scan.hasNextLine()) {
                        String dat = scan.nextLine();
                        System.out.println(dat);
                        String[] divided = dat.split(";");
                        Pattern check = Pattern.compile(".*\\.jpg\\b|.*\\.png\\b");
                        Matcher regexMatcher = check.matcher(divided[0]);
                        if(regexMatcher.find()) {
                            photos.add(new Photo(dat));
                            data.add(dat);
                        } else {
                            System.out.println("nullllllllllllllllllllllllllllllllllllllll");
                            JOptionPane.showMessageDialog(null, "wrong extension for "+ divided[0] );
                        }
                        }
                } catch (FileNotFoundException a) {
                    System.out.println("file not found");
                }
                photos.get(0).show();
                for(int i=0;i<photos.size();i++){
                    JButton[]tab = new JButton[photos.size()];
                    tab[i] = new JButton();
                    ImageIcon img = new ImageIcon(photos.get(i).getPath());
                    Image scaleImage = img.getImage().getScaledInstance(winDim10.width-50,100,Image.SCALE_DEFAULT);
                    tab[i].setIcon(new ImageIcon(scaleImage));
                    gridphots.add(tab[i]);
                    System.out.println(photos.get(i).getPath());
                    gridphots.setLayout(new GridLayout(tab.length/2+1,2));
                    int m =i;

                    tab[i].addActionListener(a->{
                        ImageIcon clickedimage =  new ImageIcon(photos.get(m).getPath());
                        Image scaleclicked = clickedimage.getImage().getScaledInstance(winDim2.width,winDim2.height,Image.SCALE_DEFAULT);
                        System.out.println("click"+m+photos.get(m).getPath());
                        author.setText(photos.get(m).getAuthor());
                        tags.setText(photos.get(m).getTags());
                        place.setText(photos.get(m).getPlace());
                        date.setText(photos.get(m).getDate());
                        centerlabel.setIcon(new ImageIcon(scaleclicked));
                        centerpanel.revalidate();
                        centerpanel.repaint();
                    });
                    gridphots.revalidate();
                    gridphots.repaint();
                }
            }
        });
        addItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
        addItem.addActionListener(h->{
            JFileChooser selectPhoto = new JFileChooser();
            int code = selectPhoto.showOpenDialog(mainFrame);
            if (code == JFileChooser.APPROVE_OPTION) {
                String path = selectPhoto.getSelectedFile().getPath();
                JFrame addImageWindow = new JFrame();
                JTextArea pathAdd= new JTextArea(path);
                pathAdd.setEditable(false);
                JTextArea authorAdd = new JTextArea("insert author");
                JTextArea tagsAdd = new JTextArea("insert tags");
                JTextArea dateAdd = new JTextArea("dd.mm.yyyy");

                JTextArea placeAdd = new JTextArea("insert place");
                JPanel textAreasLabel = new JPanel();
                textAreasLabel.add(pathAdd);
                textAreasLabel.add(authorAdd);
                textAreasLabel.add(tagsAdd);
                textAreasLabel.add(dateAdd);
                textAreasLabel.add(placeAdd);
                addImageWindow.add(textAreasLabel);
                textAreasLabel.setLayout(new GridLayout(6,1));
                JButton addButton = new JButton("Add");


                addButton.addActionListener(t->{
                    System.out.println(dateAdd.toString());
                    if(isApropriateDate(dateAdd)){
                    Photo newPhoto = new Photo();
                    newPhoto.setAuthor( authorAdd.getText());
                    newPhoto.setDate(dateAdd.getText());
                    newPhoto.setPath(pathAdd.getText());
                    newPhoto.setTags(tagsAdd.getText());
                    newPhoto.setPlace(placeAdd.getText());
                    photos.add(newPhoto);
                    gridphots.removeAll();
                    for(int i=0;i<photos.size();i++){
                        JButton[]tab = new JButton[photos.size()];
                        tab[i] = new JButton();
                        ImageIcon img = new ImageIcon(photos.get(i).getPath());
                        Image scaleImage = img.getImage().getScaledInstance(winDim10.width-50,100,Image.SCALE_DEFAULT);
                        tab[i].setIcon(new ImageIcon(scaleImage));
                        gridphots.add(tab[i]);
                        System.out.println(photos.get(i).getPath());
                        gridphots.setLayout(new GridLayout(tab.length/2+1,2));
                        int m =i;

                        tab[i].addActionListener(a->{
                            ImageIcon clickedimage =  new ImageIcon(photos.get(m).getPath());
                            Image scaleclicked = clickedimage.getImage().getScaledInstance(winDim2.width,winDim2.height,Image.SCALE_DEFAULT);
                            System.out.println("click"+m+photos.get(m).getPath());
                            author.setText(photos.get(m).getAuthor());
                            tags.setText(photos.get(m).getTags());
                            place.setText(photos.get(m).getPlace());
                            date.setText(photos.get(m).getDate());
                            centerlabel.setIcon(new ImageIcon(scaleclicked));
                            centerpanel.revalidate();
                            centerpanel.repaint();
                        });
                        gridphots.revalidate();
                        gridphots.repaint();
                    }
                }});
                textAreasLabel.add(addButton);
                addImageWindow.pack();
                addImageWindow.setDefaultCloseOperation(addImageWindow.DISPOSE_ON_CLOSE);
                addImageWindow.setVisible(true);
            }
        });

        centerlabel.setBackground(Color.BLACK);
        centerpanel.setBackground(Color.BLUE);
        centerpanel.add(centerlabel);

        borderphotos.setLayout(new BorderLayout());
        borderphotos.add(centerpanel,BorderLayout.CENTER);

        righttextpanel.setLayout(new GridLayout(4,1));

        author.setEditable(false);
        tags.setEditable(false);
        date.setEditable(false);
        place.setEditable(false);
        righttextpanel.add(author);
        righttextpanel.add(tags);
        righttextpanel.add(date);
        righttextpanel.add(place);
        righttextpanel.setPreferredSize(new Dimension(screenDim.width/16,screenDim.height));
        borderphotos.add(righttextpanel, BorderLayout.LINE_END);


        scrollphotosleft.setPreferredSize(winDim5);
        scrollphotosleft.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        borderphotos.add(scrollphotosleft,BorderLayout.LINE_START);
        mainFrame.add(borderphotos);
        mainFrame.setVisible(true);

       JMenu fileMenu = new JMenu("file");
       fileMenu.add(addItem);
       fileMenu.add(openFileItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);

        mainFrame.setJMenuBar(menuBar);
        mainFrame.setSize(screenDim.width / 2, screenDim.height / 2);
        mainFrame.setLocation(screenDim.width / 4, screenDim.height / 2);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

    }
public static Boolean isApropriateDate (JTextArea date){
        String dat = date.getText();
        Pattern datepat = Pattern.compile("([123]?[0-9])-([1]?[0-9])-([0-9]{4})");
        Matcher datematch = datepat.matcher(dat);
        if (datematch.find()){
            return  true;
        } else return false;

}


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

}

