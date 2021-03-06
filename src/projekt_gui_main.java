


import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class projekt_gui_main {
    public static void createAndShowGUI() {
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension winDim5 = new Dimension();
        Dimension winDim10 = new Dimension();
        Dimension winDim4 = new Dimension();
        Dimension winDim2 = new Dimension();
        //dimensions...
        JFrame mainFrame = new JFrame();
        mainFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Component c = (Component) e.getSource();
                winDim2.height = c.getHeight();
                winDim2.width = c.getWidth() / 2;
                winDim5.height = c.getHeight();
                winDim5.width = c.getWidth() / 5;
                winDim4.height = c.getHeight();
                winDim4.width = c.getWidth() / 4;
                winDim10.height = c.getHeight();
                winDim10.width = c.getWidth() / 10;
            }
        });
        JLabel centerlabel = new JLabel();
        JPanel centerpanel = new JPanel();
        JPanel gridphots = new JPanel();
        JPanel borderphotos = new JPanel();
        JTextArea author = new JTextArea();
        JTextPane date = new JTextPane();
        JTextArea tags = new JTextArea();
        tags.setLineWrap(true);
        tags.setRows(8);

        JTextPane place = new JTextPane();
        JPanel righttextpanel = new JPanel();

        tags.setWrapStyleWord(true);
        JMenuItem addItem = new JMenuItem("Add Photo");
        JMenuItem openFileItem = new JMenuItem("Open library");
        JMenuItem saveDatabase = new JMenuItem("Save Database");

        JMenuItem sortbyDate = new JMenuItem("date");
        JMenuItem sortbyAuthor = new JMenuItem("author");
        JMenuItem sortbyPlace = new JMenuItem("place");

        JMenuItem dateSearch = new JMenuItem("search by date");

        JMenuItem choosenTag = new JMenuItem("Tag");
        JMenuItem choosenDate = new JMenuItem("Date");
        JMenuItem choosenAuthor = new JMenuItem("Author");
        JMenuItem choosenPlace = new JMenuItem("Place");

        JMenuItem deleteimage = new JMenuItem("delete image");

        JMenuItem editImage = new JMenuItem("edit properties");

        JMenuItem revsortbyDate = new JMenuItem("date");
        JMenuItem revsortbyAuthor = new JMenuItem("author");
        JMenuItem revsortbyPlace = new JMenuItem("place");

        JMenuItem showHighestDate = new JMenuItem("Highest date");
        JMenuItem showHighestAuthor = new JMenuItem("Highest author");
        JMenuItem showHighestPlace = new JMenuItem("Highest place");
        JMenuItem showLowestDate = new JMenuItem("Lowest date");
        JMenuItem showLowestAuthor = new JMenuItem("Lowest author");
        JMenuItem showLowestPlace = new JMenuItem("Lowest place");

        JFileChooser fileChooser = new JFileChooser();
        ArrayList data = new ArrayList();
        ArrayList<Photo> photos = new ArrayList<Photo>();

        JScrollPane scrollphotosleft = new JScrollPane(gridphots);
        //open database code
        openFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openFileItem.addActionListener(e -> {
            int code = fileChooser.showOpenDialog(mainFrame);
            if (code == JFileChooser.APPROVE_OPTION) {
               photos.clear();
                gridphots.removeAll();
                String path = fileChooser.getSelectedFile().getPath();
                Pattern checkdatabase = Pattern.compile(".*\\.txt\\b");
                Matcher matchdatabase = checkdatabase.matcher(path);
                if (matchdatabase.find()) {
                    File file = new File(path);
                    try {
                        Scanner scan = new Scanner(file);
                        while (scan.hasNextLine()) {
                            String dat = scan.nextLine();
                            System.out.println(dat);
                            String[] divided = dat.split(";");
                            Pattern check = Pattern.compile(".*\\.jpg\\b|.*\\.png\\b");
                            Matcher regexMatcher = check.matcher(divided[0]);
                            if (regexMatcher.find()) {
                                photos.add(new Photo(dat));
                                data.add(dat);
                            } else {
                                System.out.println("nullllllllllllllllllllllllllllllllllllllll");
                                JOptionPane.showMessageDialog(null, "wrong extension for " + divided[0]);
                            }
                        }
                    } catch (FileNotFoundException a) {
                        System.out.println("file not found");
                    }
                    photos.get(0).show();
                    recreatebuttons(winDim10, winDim2, centerlabel, centerpanel, gridphots, author, date, tags, place, photos);
                } else JOptionPane.showMessageDialog(null, "wrong database extension");
            }
        });
        //add single image code
        addItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        addItem.addActionListener(h -> {
            JFileChooser selectPhoto = new JFileChooser();
            int code = selectPhoto.showOpenDialog(mainFrame);
            if (code == JFileChooser.APPROVE_OPTION) {
                String path = selectPhoto.getSelectedFile().getPath();
                JFrame addImageWindow = new JFrame();
                addImageWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                JTextArea pathAdd = new JTextArea(path);
                pathAdd.setEditable(false);
                JTextArea authorAdd = new JTextArea("insert author");
                JTextArea tagsAdd = new JTextArea("insert tags");
                JTextArea dateAdd = new JTextArea("dd-mm-yyyy");

                JTextArea placeAdd = new JTextArea("insert place");
                JPanel textAreasLabel = new JPanel();
                textAreasLabel.add(pathAdd);
                textAreasLabel.add(authorAdd);
                textAreasLabel.add(tagsAdd);
                textAreasLabel.add(dateAdd);
                textAreasLabel.add(placeAdd);
                addImageWindow.add(textAreasLabel);
                textAreasLabel.setLayout(new GridLayout(6, 1));
                JButton addButton = new JButton("Add");


                addButton.addActionListener(t -> {
                    System.out.println(dateAdd.toString());
                    if (isApropriateDate(dateAdd)) {
                        Photo newPhoto = new Photo();
                        if (!authorAdd.getText().isEmpty()) {
                            newPhoto.setAuthor(authorAdd.getText());
                        } else newPhoto.setAuthor("No Data");
                        if (!dateAdd.getText().isEmpty()) {
                            newPhoto.setDate(dateAdd.getText());
                        } else newPhoto.setDate("No Data");
                        if (!pathAdd.getText().isEmpty()) {
                            newPhoto.setPath(pathAdd.getText());
                        } else newPhoto.setPath("No Data");
                        if (!tagsAdd.getText().isEmpty()) {
                            newPhoto.setTags(tagsAdd.getText());
                        } else {
                            newPhoto.setTags("No Data");
                            newPhoto.setTagsSplit(new String[]{"No Data"});
                        }
                        if (!placeAdd.getText().isEmpty()) {
                            newPhoto.setPlace(placeAdd.getText());
                        } else newPhoto.setPlace("No Data");
                        photos.add(newPhoto);
                        gridphots.removeAll();
                        recreatebuttons(winDim10, winDim2, centerlabel, centerpanel, gridphots, author, date, tags, place, photos);
                        addImageWindow.setVisible(false);
                    } else JOptionPane.showMessageDialog(null, "incorrect date format, please insert dd-mm-yyyy");
                });
                textAreasLabel.add(addButton);
                addImageWindow.pack();
                addImageWindow.setDefaultCloseOperation(addImageWindow.DISPOSE_ON_CLOSE);
                addImageWindow.setVisible(true);
            }
        });
        //delete image
        deleteimage.addActionListener(e -> {
            if (photos.size() > 0) {
                JFrame removeImage = new JFrame();
                removeImage.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                JPanel deleteImgPanel = new JPanel();
                deleteImgPanel.setLayout(new GridLayout());
                for (int i = 0; i < photos.size(); i++) {
                    JButton[] tab = new JButton[photos.size()];
                    tab[i] = new JButton();
                    ImageIcon img = new ImageIcon(photos.get(i).getPath());
                    Image scaleImage = img.getImage().getScaledInstance(winDim10.width - 50, 100, Image.SCALE_DEFAULT);
                    tab[i].setIcon(new ImageIcon(scaleImage));
                    deleteImgPanel.add(tab[i]);
                    System.out.println(photos.get(i).getPath());
                    deleteImgPanel.setLayout(new GridLayout());
                    int m = i;

                    tab[i].addActionListener(a -> {
                        photos.remove(m);
                        gridphots.removeAll();
                        recreatebuttons(winDim10, winDim2, centerlabel, centerpanel, gridphots, author, date, tags, place, photos);

                        gridphots.revalidate();
                        gridphots.repaint();
                        removeImage.setVisible(false);

                    });

                    gridphots.revalidate();
                    gridphots.repaint();
                }
                removeImage.add(deleteImgPanel);
                removeImage.pack();
                removeImage.setVisible(true);
            } else JOptionPane.showMessageDialog(null, "no images found");
        });
        //edit properties
        editImage.addActionListener(ds -> {
            if (photos.size() > 0) {
                JFrame chooseImage = new JFrame();
                chooseImage.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                JPanel chooseImagePanel = new JPanel();
                chooseImagePanel.setLayout(new GridLayout());
                for (int i = 0; i < photos.size(); i++) {
                    JButton[] tab = new JButton[photos.size()];
                    tab[i] = new JButton();
                    ImageIcon img = new ImageIcon(photos.get(i).getPath());
                    Image scaleImage = img.getImage().getScaledInstance(winDim10.width - 50, 100, Image.SCALE_DEFAULT);
                    tab[i].setIcon(new ImageIcon(scaleImage));
                    chooseImagePanel.add(tab[i]);
                    System.out.println(photos.get(i).getPath());
                    chooseImagePanel.setLayout(new GridLayout());
                    int m = i;

                    tab[m].addActionListener(a -> {
                        JFrame properties = new JFrame();
                        properties.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        JPanel propertiesPanel = new JPanel();
                        propertiesPanel.setLayout(new GridLayout(5, 1));
                        JTextArea editAuthor = new JTextArea(photos.get(m).getAuthor());
                        JTextArea editPlace = new JTextArea(photos.get(m).getPlace());
                        JTextArea editDate = new JTextArea(photos.get(m).getDate());
                        JTextArea editTags = new JTextArea(photos.get(m).getTags());
                        editTags.setLineWrap(true);
                        propertiesPanel.add(editAuthor);
                        propertiesPanel.add(editPlace);
                        propertiesPanel.add(editDate);
                        propertiesPanel.add(editTags);

                        JButton setProperties = new JButton("set properties");
                        propertiesPanel.add(setProperties);

                        setProperties.addActionListener(ag -> {
                            if (isApropriateDate(editDate)) {
                                photos.get(m).setAuthor(editAuthor.getText());
                                photos.get(m).setPlace(editPlace.getText());
                                photos.get(m).setDate(editDate.getText());
                                photos.get(m).setTags(editTags.getText());
                                properties.setVisible(false);
                                chooseImage.setVisible(false);
                            } else
                                JOptionPane.showMessageDialog(null, "incorrect date format, please insert dd-mm-yyyy");
                        });


                        gridphots.removeAll();
                        recreatebuttons(winDim10, winDim2, centerlabel, centerpanel, gridphots, author, date, tags, place, photos);
                        gridphots.revalidate();
                        gridphots.repaint();
                        properties.add(propertiesPanel);
                        properties.pack();
                        properties.setVisible(true);
                        chooseImage.setVisible(false);

                    });

                    gridphots.revalidate();
                    gridphots.repaint();
                }
                chooseImage.add(chooseImagePanel);
                chooseImage.pack();
                chooseImage.setVisible(true);
            } else JOptionPane.showMessageDialog(null, "no images found");
        });
        //sorting buttons
        sortbyAuthor.addActionListener(auth -> {
            Collections.sort(photos, new Comparator<Photo>() {
                @Override
                public int compare(Photo o1, Photo o2) {
                    return o1.getAuthor().compareTo(o2.getAuthor());
                }
            });
            gridphots.removeAll();
            recreatebuttons(winDim10, winDim2, centerlabel, centerpanel, gridphots, author, date, tags, place, photos);
        });
        sortbyDate.addActionListener(auth -> {
            Collections.sort(photos, new Comparator<Photo>() {
                @Override
                public int compare(Photo o1, Photo o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
            gridphots.removeAll();
            recreatebuttons(winDim10, winDim2, centerlabel, centerpanel, gridphots, author, date, tags, place, photos);
        });
        sortbyPlace.addActionListener(auth -> {
            Collections.sort(photos, new Comparator<Photo>() {
                @Override
                public int compare(Photo o1, Photo o2) {
                    return o1.getPlace().compareTo(o2.getPlace());
                }
            });
            gridphots.removeAll();
            recreatebuttons(winDim10, winDim2, centerlabel, centerpanel, gridphots, author, date, tags, place, photos);
        });
        revsortbyAuthor.addActionListener(auth -> {
            Collections.sort(photos, new Comparator<Photo>() {
                @Override
                public int compare(Photo o1, Photo o2) {
                    return o1.getAuthor().compareTo(o2.getAuthor());
                }
            });
            Collections.reverse(photos);
            gridphots.removeAll();
            recreatebuttons(winDim10, winDim2, centerlabel, centerpanel, gridphots, author, date, tags, place, photos);
        });
        revsortbyDate.addActionListener(auth -> {
            Collections.sort(photos, new Comparator<Photo>() {
                @Override
                public int compare(Photo o1, Photo o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
            Collections.reverse(photos);
            gridphots.removeAll();
            recreatebuttons(winDim10, winDim2, centerlabel, centerpanel, gridphots, author, date, tags, place, photos);
        });
        revsortbyPlace.addActionListener(auth -> {
            Collections.sort(photos, new Comparator<Photo>() {
                @Override
                public int compare(Photo o1, Photo o2) {
                    return o1.getPlace().compareTo(o2.getPlace());
                }
            });
            Collections.reverse(photos);
            gridphots.removeAll();
            recreatebuttons(winDim10, winDim2, centerlabel, centerpanel, gridphots, author, date, tags, place, photos);
        });
        //displaying image properties with highest property
        showHighestAuthor.addActionListener(hig -> {
            if (photos.size() > 0) {
                ArrayList<Photo> tempArray = new ArrayList<>(photos);
                Collections.sort(tempArray, new Comparator<Photo>() {
                    @Override
                    public int compare(Photo o1, Photo o2) {
                        return o1.getAuthor().compareTo(o2.getAuthor());
                    }
                });
                Collections.reverse(tempArray);
                JFrame imageHFrame = new JFrame();
                imageHFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                imageHFrame.setLayout(new BorderLayout());
                JLabel imageHLabel = new JLabel();
                JTextArea authorHig = new JTextArea(tempArray.get(0).getAuthor());
                JTextArea pathHig = new JTextArea(tempArray.get(0).getPath());
                JTextArea dateHig = new JTextArea(tempArray.get(0).getDate());
                JTextArea placeHig = new JTextArea(tempArray.get(0).getPlace());
                JTextArea tagsHig = new JTextArea(tempArray.get(0).getTags());
                JPanel textAreas = new JPanel();
                textAreas.setLayout(new GridLayout(5, 1));
                textAreas.add(pathHig);
                textAreas.add(dateHig);
                textAreas.add(authorHig);
                textAreas.add(placeHig);
                textAreas.add(tagsHig);
                pathHig.setEditable(false);
                dateHig.setEditable(false);
                tagsHig.setEditable(false);
                placeHig.setEditable(false);
                authorHig.setEditable(false);
                ImageIcon hImage = new ImageIcon(tempArray.get(0).getPath());
                imageHLabel.setIcon(hImage);
                imageHFrame.add(textAreas, BorderLayout.LINE_START);
                imageHFrame.add(imageHLabel, BorderLayout.LINE_END);
                imageHFrame.pack();
                imageHFrame.setVisible(true);
            } else JOptionPane.showMessageDialog(null, "no images found");

        });
        showHighestDate.addActionListener(hig -> {
            if (photos.size() > 0) {
                ArrayList<Photo> tempArray = new ArrayList<>(photos);
                Collections.sort(tempArray, new Comparator<Photo>() {
                    @Override
                    public int compare(Photo o1, Photo o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });
                Collections.reverse(tempArray);
                JFrame imageHFrame = new JFrame();
                imageHFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                imageHFrame.setLayout(new BorderLayout());
                JLabel imageHLabel = new JLabel();
                JTextArea authorHig = new JTextArea(tempArray.get(0).getAuthor());
                JTextArea pathHig = new JTextArea(tempArray.get(0).getPath());
                JTextArea dateHig = new JTextArea(tempArray.get(0).getDate());
                JTextArea placeHig = new JTextArea(tempArray.get(0).getPlace());
                JTextArea tagsHig = new JTextArea(tempArray.get(0).getTags());
                JPanel textAreas = new JPanel();
                textAreas.setLayout(new GridLayout(5, 1));
                textAreas.add(pathHig);
                textAreas.add(dateHig);
                textAreas.add(authorHig);
                textAreas.add(placeHig);
                textAreas.add(tagsHig);
                pathHig.setEditable(false);
                dateHig.setEditable(false);
                tagsHig.setEditable(false);
                placeHig.setEditable(false);
                authorHig.setEditable(false);
                ImageIcon hImage = new ImageIcon(tempArray.get(0).getPath());
                imageHLabel.setIcon(hImage);
                imageHFrame.add(textAreas, BorderLayout.LINE_START);
                imageHFrame.add(imageHLabel, BorderLayout.LINE_END);
                imageHFrame.pack();
                imageHFrame.setVisible(true);
            } else JOptionPane.showMessageDialog(null, "no images found");
        });
        showHighestPlace.addActionListener(hig -> {
            if (photos.size() > 0) {
                ArrayList<Photo> tempArray = new ArrayList<>(photos);
                Collections.sort(tempArray, new Comparator<Photo>() {
                    @Override
                    public int compare(Photo o1, Photo o2) {
                        return o1.getPlace().compareTo(o2.getPlace());
                    }
                });
                Collections.reverse(tempArray);
                JFrame imageHFrame = new JFrame();
                imageHFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                imageHFrame.setLayout(new BorderLayout());
                JLabel imageHLabel = new JLabel();
                JTextArea authorHig = new JTextArea(tempArray.get(0).getAuthor());
                JTextArea pathHig = new JTextArea(tempArray.get(0).getPath());
                JTextArea dateHig = new JTextArea(tempArray.get(0).getDate());
                JTextArea placeHig = new JTextArea(tempArray.get(0).getPlace());
                JTextArea tagsHig = new JTextArea(tempArray.get(0).getTags());
                JPanel textAreas = new JPanel();
                textAreas.setLayout(new GridLayout(5, 1));
                textAreas.add(pathHig);
                textAreas.add(dateHig);
                textAreas.add(authorHig);
                textAreas.add(placeHig);
                textAreas.add(tagsHig);
                pathHig.setEditable(false);
                dateHig.setEditable(false);
                tagsHig.setEditable(false);
                placeHig.setEditable(false);
                authorHig.setEditable(false);
                ImageIcon hImage = new ImageIcon(tempArray.get(0).getPath());
                imageHLabel.setIcon(hImage);
                imageHFrame.add(textAreas, BorderLayout.LINE_START);
                imageHFrame.add(imageHLabel, BorderLayout.LINE_END);
                imageHFrame.pack();
                imageHFrame.setVisible(true);
            } else JOptionPane.showMessageDialog(null, "no images found");
        });
        showLowestAuthor.addActionListener(hig -> {
            if (photos.size() > 0) {
                ArrayList<Photo> tempArray = new ArrayList<>(photos);
                Collections.sort(tempArray, new Comparator<Photo>() {
                    @Override
                    public int compare(Photo o1, Photo o2) {
                        return o1.getAuthor().compareTo(o2.getAuthor());
                    }
                });

                JFrame imageHFrame = new JFrame();
                imageHFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                imageHFrame.setLayout(new BorderLayout());
                JLabel imageHLabel = new JLabel();
                JTextArea authorHig = new JTextArea(tempArray.get(0).getAuthor());
                JTextArea pathHig = new JTextArea(tempArray.get(0).getPath());
                JTextArea dateHig = new JTextArea(tempArray.get(0).getDate());
                JTextArea placeHig = new JTextArea(tempArray.get(0).getPlace());
                JTextArea tagsHig = new JTextArea(tempArray.get(0).getTags());
                JPanel textAreas = new JPanel();
                textAreas.setLayout(new GridLayout(5, 1));
                textAreas.add(pathHig);
                textAreas.add(dateHig);
                textAreas.add(authorHig);
                textAreas.add(placeHig);
                textAreas.add(tagsHig);
                pathHig.setEditable(false);
                dateHig.setEditable(false);
                tagsHig.setEditable(false);
                placeHig.setEditable(false);
                authorHig.setEditable(false);
                ImageIcon hImage = new ImageIcon(tempArray.get(0).getPath());
                imageHLabel.setIcon(hImage);
                imageHFrame.add(textAreas, BorderLayout.LINE_START);
                imageHFrame.add(imageHLabel, BorderLayout.LINE_END);
                imageHFrame.pack();
                imageHFrame.setVisible(true);
            } else JOptionPane.showMessageDialog(null, "no images found");
        });
        showLowestDate.addActionListener(hig -> {
            if (photos.size() > 0) {
                ArrayList<Photo> tempArray = new ArrayList<>(photos);
                Collections.sort(tempArray, new Comparator<Photo>() {
                    @Override
                    public int compare(Photo o1, Photo o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });

                JFrame imageHFrame = new JFrame();
                imageHFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                imageHFrame.setLayout(new BorderLayout());
                JLabel imageHLabel = new JLabel();
                JTextArea authorHig = new JTextArea(tempArray.get(0).getAuthor());
                JTextArea pathHig = new JTextArea(tempArray.get(0).getPath());
                JTextArea dateHig = new JTextArea(tempArray.get(0).getDate());
                JTextArea placeHig = new JTextArea(tempArray.get(0).getPlace());
                JTextArea tagsHig = new JTextArea(tempArray.get(0).getTags());
                JPanel textAreas = new JPanel();
                textAreas.setLayout(new GridLayout(5, 1));
                textAreas.add(pathHig);
                textAreas.add(dateHig);
                textAreas.add(authorHig);
                textAreas.add(placeHig);
                textAreas.add(tagsHig);
                pathHig.setEditable(false);
                dateHig.setEditable(false);
                tagsHig.setEditable(false);
                placeHig.setEditable(false);
                authorHig.setEditable(false);
                ImageIcon hImage = new ImageIcon(tempArray.get(0).getPath());
                imageHLabel.setIcon(hImage);
                imageHFrame.add(textAreas, BorderLayout.LINE_START);
                imageHFrame.add(imageHLabel, BorderLayout.LINE_END);
                imageHFrame.pack();
                imageHFrame.setVisible(true);
            } else JOptionPane.showMessageDialog(null, "no images found");
        });
        showLowestPlace.addActionListener(hig -> {
            if (photos.size() > 0) {
                ArrayList<Photo> tempArray = new ArrayList<>(photos);
                Collections.sort(tempArray, new Comparator<Photo>() {
                    @Override
                    public int compare(Photo o1, Photo o2) {
                        return o1.getPlace().compareTo(o2.getPlace());
                    }
                });

                JFrame imageHFrame = new JFrame();
                imageHFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                imageHFrame.setLayout(new BorderLayout());
                JLabel imageHLabel = new JLabel();
                JTextArea authorHig = new JTextArea(tempArray.get(0).getAuthor());
                JTextArea pathHig = new JTextArea(tempArray.get(0).getPath());
                JTextArea dateHig = new JTextArea(tempArray.get(0).getDate());
                JTextArea placeHig = new JTextArea(tempArray.get(0).getPlace());
                JTextArea tagsHig = new JTextArea(tempArray.get(0).getTags());
                JPanel textAreas = new JPanel();
                textAreas.setLayout(new GridLayout(5, 1));
                textAreas.add(pathHig);
                textAreas.add(dateHig);
                textAreas.add(authorHig);
                textAreas.add(placeHig);
                textAreas.add(tagsHig);
                pathHig.setEditable(false);
                dateHig.setEditable(false);
                tagsHig.setEditable(false);
                placeHig.setEditable(false);
                authorHig.setEditable(false);
                ImageIcon hImage = new ImageIcon(tempArray.get(0).getPath());
                imageHLabel.setIcon(hImage);
                imageHFrame.add(textAreas, BorderLayout.LINE_START);
                imageHFrame.add(imageHLabel, BorderLayout.LINE_END);
                imageHFrame.pack();
                imageHFrame.setVisible(true);
            } else JOptionPane.showMessageDialog(null, "no images found");
        });
        //displaying images with chosen tag
        choosenTag.addActionListener(chos -> {
            JFrame tagChooseFrame = new JFrame();
            tagChooseFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JTextField tagTArea = new JTextField();
            JButton searchTag = new JButton("searchTag");
            tagTArea.setAutoscrolls(true);
            tagChooseFrame.setLayout(new BorderLayout());
            tagChooseFrame.add(tagTArea, BorderLayout.CENTER);
            tagChooseFrame.add(searchTag, BorderLayout.LINE_END);
            tagChooseFrame.setSize(300, 100);
            tagChooseFrame.setVisible(true);
            searchTag.addActionListener(search -> {
                System.out.println(tagTArea.getText());

                JFrame filteredImagesFrame = new JFrame();
                filteredImagesFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                JPanel filteredImagesPanel = new JPanel();
                ArrayList<Photo> filteredPhotos = new ArrayList<>();
                Pattern tagCheck = Pattern.compile(tagTArea.getText());
                for (Photo photoInLoop : photos) {
                if(!photoInLoop.getTags().isEmpty()) {
                    for (String tagInLoop : photoInLoop.getTagsSplit()) {
                        Matcher tagsMatcher = tagCheck.matcher(tagInLoop);
                        // System.out.println(tagInLoop.toString()+" "+tagsMatcher.find()+" ################# "+filteredPhotos.size()+" "+" "+tagTArea.getText());
                        if (tagsMatcher.matches()) {
                            filteredPhotos.add(photoInLoop);
                            //  System.out.println("ADDED"+photoInLoop.getTags());
                        }
                    }
                }
                }
                System.out.println("size of filteredPhotos is " + filteredPhotos.size());
                if (filteredPhotos.size() > 0) {
                    for (int i = 0; i < filteredPhotos.size(); i++) {
                        JButton[] tabFiltered = new JButton[filteredPhotos.size()];
                        tabFiltered[i] = new JButton();
                        ImageIcon img = new ImageIcon(filteredPhotos.get(i).getPath());
                        Image scaleImage = img.getImage().getScaledInstance(winDim10.width - 50, 100, Image.SCALE_DEFAULT);
                        tabFiltered[i].setIcon(new ImageIcon(scaleImage));
                        filteredImagesPanel.add(tabFiltered[i]);
                        filteredImagesPanel.setLayout(new GridLayout());
                        int m = i;
                        tabFiltered[i].addActionListener(a -> {
                            ImageIcon clickedimage = new ImageIcon(filteredPhotos.get(m).getPath());
                            Image scaleclicked = clickedimage.getImage().getScaledInstance(winDim2.width, winDim2.height, Image.SCALE_DEFAULT);
                            author.setText(filteredPhotos.get(m).getAuthor());
                            tags.setText(filteredPhotos.get(m).getTags());
                            place.setText(filteredPhotos.get(m).getPlace());
                            date.setText(filteredPhotos.get(m).getDate());
                            centerlabel.setIcon(new ImageIcon(scaleclicked));
                            centerpanel.revalidate();
                            centerpanel.repaint();
                            filteredImagesFrame.setVisible(false);

                        });
                    }

                    filteredImagesFrame.setLayout(new GridLayout());
                    filteredImagesFrame.add(filteredImagesPanel);
                    filteredImagesFrame.pack();
                    filteredImagesFrame.setVisible(true);
                } else JOptionPane.showMessageDialog(null, "no photos found");

            });
        });
        choosenDate.addActionListener(chos -> {
            JFrame tagChooseFrame = new JFrame();
            tagChooseFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JTextField tagTArea = new JTextField();
            JButton searchTag = new JButton("searchTag");
            tagTArea.setAutoscrolls(true);
            tagChooseFrame.setLayout(new BorderLayout());
            tagChooseFrame.add(tagTArea, BorderLayout.CENTER);
            tagChooseFrame.add(searchTag, BorderLayout.LINE_END);
            tagChooseFrame.setSize(300, 100);
            tagChooseFrame.setVisible(true);
            searchTag.addActionListener(search -> {
                System.out.println(tagTArea.getText());
                if (isApropriateDate(tagTArea)) {
                    JFrame filteredImagesFrame = new JFrame();
                    filteredImagesFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    JPanel filteredImagesPanel = new JPanel();
                    ArrayList<Photo> filteredPhotos = new ArrayList<>();
                    Pattern tagCheck = Pattern.compile(tagTArea.getText());
                    for (Photo photoInLoop : photos) {
                        if (!photoInLoop.getTags().isEmpty()) {

                                Matcher tagsMatcher = tagCheck.matcher(photoInLoop.getDate());
                                // System.out.println(tagInLoop.toString()+" "+tagsMatcher.find()+" ################# "+filteredPhotos.size()+" "+" "+tagTArea.getText());
                                if (tagsMatcher.matches()) {
                                    filteredPhotos.add(photoInLoop);
                                    //  System.out.println("ADDED"+photoInLoop.getTags());
                                }

                        }
                    }
                    System.out.println("size of filteredPhotos is " + filteredPhotos.size());
                    if (filteredPhotos.size() > 0) {
                        for (int i = 0; i < filteredPhotos.size(); i++) {
                            JButton[] tabFiltered = new JButton[filteredPhotos.size()];
                            tabFiltered[i] = new JButton();
                            ImageIcon img = new ImageIcon(filteredPhotos.get(i).getPath());
                            Image scaleImage = img.getImage().getScaledInstance(winDim10.width - 50, 100, Image.SCALE_DEFAULT);
                            tabFiltered[i].setIcon(new ImageIcon(scaleImage));
                            filteredImagesPanel.add(tabFiltered[i]);
                            filteredImagesPanel.setLayout(new GridLayout());
                            int m = i;
                            tabFiltered[i].addActionListener(a -> {
                                ImageIcon clickedimage = new ImageIcon(filteredPhotos.get(m).getPath());
                                Image scaleclicked = clickedimage.getImage().getScaledInstance(winDim2.width, winDim2.height, Image.SCALE_DEFAULT);
                                author.setText(filteredPhotos.get(m).getAuthor());
                                tags.setText(filteredPhotos.get(m).getTags());
                                place.setText(filteredPhotos.get(m).getPlace());
                                date.setText(filteredPhotos.get(m).getDate());
                                centerlabel.setIcon(new ImageIcon(scaleclicked));
                                centerpanel.revalidate();
                                centerpanel.repaint();
                                filteredImagesFrame.setVisible(false);

                            });
                        }

                        filteredImagesFrame.setLayout(new GridLayout());
                        filteredImagesFrame.add(filteredImagesPanel);
                        filteredImagesFrame.pack();
                        filteredImagesFrame.setVisible(true);
                    } else JOptionPane.showMessageDialog(null, "no photos found");
                } else JOptionPane.showMessageDialog(null, "please enter apropriate date");
            });
        });
        choosenAuthor.addActionListener(chos -> {
            JFrame tagChooseFrame = new JFrame();
            tagChooseFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JTextField tagTArea = new JTextField();
            JButton searchTag = new JButton("searchTag");
            tagTArea.setAutoscrolls(true);
            tagChooseFrame.setLayout(new BorderLayout());
            tagChooseFrame.add(tagTArea, BorderLayout.CENTER);
            tagChooseFrame.add(searchTag, BorderLayout.LINE_END);
            tagChooseFrame.setSize(300, 100);
            tagChooseFrame.setVisible(true);
            searchTag.addActionListener(search -> {
                System.out.println(tagTArea.getText());

                    JFrame filteredImagesFrame = new JFrame();
                    filteredImagesFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    JPanel filteredImagesPanel = new JPanel();
                    ArrayList<Photo> filteredPhotos = new ArrayList<>();
                    Pattern tagCheck = Pattern.compile(tagTArea.getText());
                    for (Photo photoInLoop : photos) {
                        if (!photoInLoop.getTags().isEmpty()) {

                            Matcher tagsMatcher = tagCheck.matcher(photoInLoop.getAuthor());
                            // System.out.println(tagInLoop.toString()+" "+tagsMatcher.find()+" ################# "+filteredPhotos.size()+" "+" "+tagTArea.getText());
                            if (tagsMatcher.matches()) {
                                filteredPhotos.add(photoInLoop);
                                //  System.out.println("ADDED"+photoInLoop.getTags());
                            }

                        }
                    }
                    System.out.println("size of filteredPhotos is " + filteredPhotos.size());
                    if (filteredPhotos.size() > 0) {
                        for (int i = 0; i < filteredPhotos.size(); i++) {
                            JButton[] tabFiltered = new JButton[filteredPhotos.size()];
                            tabFiltered[i] = new JButton();
                            ImageIcon img = new ImageIcon(filteredPhotos.get(i).getPath());
                            Image scaleImage = img.getImage().getScaledInstance(winDim10.width - 50, 100, Image.SCALE_DEFAULT);
                            tabFiltered[i].setIcon(new ImageIcon(scaleImage));
                            filteredImagesPanel.add(tabFiltered[i]);
                            filteredImagesPanel.setLayout(new GridLayout());
                            int m = i;
                            tabFiltered[i].addActionListener(a -> {
                                ImageIcon clickedimage = new ImageIcon(filteredPhotos.get(m).getPath());
                                Image scaleclicked = clickedimage.getImage().getScaledInstance(winDim2.width, winDim2.height, Image.SCALE_DEFAULT);
                                author.setText(filteredPhotos.get(m).getAuthor());
                                tags.setText(filteredPhotos.get(m).getTags());
                                place.setText(filteredPhotos.get(m).getPlace());
                                date.setText(filteredPhotos.get(m).getDate());
                                centerlabel.setIcon(new ImageIcon(scaleclicked));
                                centerpanel.revalidate();
                                centerpanel.repaint();
                                filteredImagesFrame.setVisible(false);

                            });
                        }

                        filteredImagesFrame.setLayout(new GridLayout());
                        filteredImagesFrame.add(filteredImagesPanel);
                        filteredImagesFrame.pack();
                        filteredImagesFrame.setVisible(true);
                    } else JOptionPane.showMessageDialog(null, "no photos found");

            });
        });
        choosenPlace.addActionListener(chos -> {
            JFrame tagChooseFrame = new JFrame();
            tagChooseFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JTextField tagTArea = new JTextField();
            JButton searchTag = new JButton("searchTag");
            tagTArea.setAutoscrolls(true);
            tagChooseFrame.setLayout(new BorderLayout());
            tagChooseFrame.add(tagTArea, BorderLayout.CENTER);
            tagChooseFrame.add(searchTag, BorderLayout.LINE_END);
            tagChooseFrame.setSize(300, 100);
            tagChooseFrame.setVisible(true);
            searchTag.addActionListener(search -> {
                System.out.println(tagTArea.getText());

                JFrame filteredImagesFrame = new JFrame();
                filteredImagesFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                JPanel filteredImagesPanel = new JPanel();
                ArrayList<Photo> filteredPhotos = new ArrayList<>();
                Pattern tagCheck = Pattern.compile(tagTArea.getText());
                for (Photo photoInLoop : photos) {
                    if (!photoInLoop.getTags().isEmpty()) {

                        Matcher tagsMatcher = tagCheck.matcher(photoInLoop.getPlace());
                        // System.out.println(tagInLoop.toString()+" "+tagsMatcher.find()+" ################# "+filteredPhotos.size()+" "+" "+tagTArea.getText());
                        if (tagsMatcher.matches()) {
                            filteredPhotos.add(photoInLoop);
                            //  System.out.println("ADDED"+photoInLoop.getTags());
                        }

                    }
                }
                System.out.println("size of filteredPhotos is " + filteredPhotos.size());
                if (filteredPhotos.size() > 0) {
                    for (int i = 0; i < filteredPhotos.size(); i++) {
                        JButton[] tabFiltered = new JButton[filteredPhotos.size()];
                        tabFiltered[i] = new JButton();
                        ImageIcon img = new ImageIcon(filteredPhotos.get(i).getPath());
                        Image scaleImage = img.getImage().getScaledInstance(winDim10.width - 50, 100, Image.SCALE_DEFAULT);
                        tabFiltered[i].setIcon(new ImageIcon(scaleImage));
                        filteredImagesPanel.add(tabFiltered[i]);
                        filteredImagesPanel.setLayout(new GridLayout());
                        int m = i;
                        tabFiltered[i].addActionListener(a -> {
                            ImageIcon clickedimage = new ImageIcon(filteredPhotos.get(m).getPath());
                            Image scaleclicked = clickedimage.getImage().getScaledInstance(winDim2.width, winDim2.height, Image.SCALE_DEFAULT);
                            author.setText(filteredPhotos.get(m).getAuthor());
                            tags.setText(filteredPhotos.get(m).getTags());
                            place.setText(filteredPhotos.get(m).getPlace());
                            date.setText(filteredPhotos.get(m).getDate());
                            centerlabel.setIcon(new ImageIcon(scaleclicked));
                            centerpanel.revalidate();
                            centerpanel.repaint();
                            filteredImagesFrame.setVisible(false);

                        });
                    }

                    filteredImagesFrame.setLayout(new GridLayout());
                    filteredImagesFrame.add(filteredImagesPanel);
                    filteredImagesFrame.pack();
                    filteredImagesFrame.setVisible(true);
                } else JOptionPane.showMessageDialog(null, "no photos found");

            });
        });
        //search by date
        dateSearch.addActionListener(search -> {
            JFrame dateSearchFrame = new JFrame();
            dateSearchFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JTextArea dateArea = new JTextArea();
            JButton greaterThan = new JButton("Greater Than");
            JButton lessThan = new JButton("Lesser Than");
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new GridLayout(2, 1));
            buttonsPanel.add(greaterThan);
            buttonsPanel.add(lessThan);
            dateSearchFrame.setLayout(new BorderLayout());
            dateSearchFrame.add(dateArea, BorderLayout.CENTER);
            dateSearchFrame.add(buttonsPanel, BorderLayout.LINE_END);
            dateSearchFrame.setSize(300, 100);
            dateSearchFrame.setVisible(true);
            greaterThan.addActionListener(great -> {
                ArrayList<Photo> filteredByDate = new ArrayList<>();
                if (isApropriateDate(dateArea)) {
                    String[] dateDivided = dateArea.getText().split("-");
                    for (Photo iter : photos) {
                        String[] tempDateArr = iter.getDate().split("-");
                        if (isGreaterDate(dateDivided, tempDateArr)) {
                            filteredByDate.add(iter);
                        }
                    }
                } else JOptionPane.showMessageDialog(null, "incorrect date");
                if (filteredByDate.size() > 0) {
                    dateSearchFrame.setVisible(false);
                    JFrame filteredByDateFrame = new JFrame();
                    filteredByDateFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    JPanel filteredByDatePanel = new JPanel();
                    for (int i = 0; i < filteredByDate.size(); i++) {
                        JButton[] tabFiltered = new JButton[filteredByDate.size()];
                        tabFiltered[i] = new JButton();
                        ImageIcon img = new ImageIcon(filteredByDate.get(i).getPath());
                        Image scaleImage = img.getImage().getScaledInstance(winDim10.width - 50, 100, Image.SCALE_DEFAULT);
                        tabFiltered[i].setIcon(new ImageIcon(scaleImage));
                        filteredByDatePanel.add(tabFiltered[i]);
                        filteredByDatePanel.setLayout(new GridLayout());
                        int m = i;
                        tabFiltered[i].addActionListener(a -> {
                            ImageIcon clickedimage = new ImageIcon(filteredByDate.get(m).getPath());
                            Image scaleclicked = clickedimage.getImage().getScaledInstance(winDim2.width, winDim2.height, Image.SCALE_DEFAULT);
                            author.setText(filteredByDate.get(m).getAuthor());
                            tags.setText(filteredByDate.get(m).getTags());
                            place.setText(filteredByDate.get(m).getPlace());
                            date.setText(filteredByDate.get(m).getDate());
                            centerlabel.setIcon(new ImageIcon(scaleclicked));
                            centerpanel.revalidate();
                            centerpanel.repaint();
                            filteredByDateFrame.setVisible(false);

                        });
                    }
                    filteredByDateFrame.setLayout(new GridLayout());
                    filteredByDateFrame.add(filteredByDatePanel);
                    filteredByDateFrame.pack();
                    filteredByDateFrame.setVisible(true);
                }
                if (filteredByDate.size() == 0) {
                    JOptionPane.showMessageDialog(null, "no photos found");
                }
            });
            lessThan.addActionListener(great -> {
                ArrayList<Photo> filteredByDate = new ArrayList<>();
                if (isApropriateDate(dateArea)) {
                    String[] dateDivided = dateArea.getText().split("-");
                    for (Photo iter : photos) {
                        String[] tempDateArr = iter.getDate().split("-");
                        if (isLesserDate(dateDivided, tempDateArr)) {
                            filteredByDate.add(iter);
                        }
                    }
                } else JOptionPane.showMessageDialog(null, "incorrect date");
                if (filteredByDate.size() > 0) {
                    dateSearchFrame.setVisible(false);
                    JFrame filteredByDateFrame = new JFrame();
                    filteredByDateFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    JPanel filteredByDatePanel = new JPanel();
                    for (int i = 0; i < filteredByDate.size(); i++) {
                        JButton[] tabFiltered = new JButton[filteredByDate.size()];
                        tabFiltered[i] = new JButton();
                        ImageIcon img = new ImageIcon(filteredByDate.get(i).getPath());
                        Image scaleImage = img.getImage().getScaledInstance(winDim10.width - 50, 100, Image.SCALE_DEFAULT);
                        tabFiltered[i].setIcon(new ImageIcon(scaleImage));
                        filteredByDatePanel.add(tabFiltered[i]);
                        filteredByDatePanel.setLayout(new GridLayout());
                        int m = i;
                        tabFiltered[i].addActionListener(a -> {
                            ImageIcon clickedimage = new ImageIcon(filteredByDate.get(m).getPath());
                            Image scaleclicked = clickedimage.getImage().getScaledInstance(winDim2.width, winDim2.height, Image.SCALE_DEFAULT);
                            author.setText(filteredByDate.get(m).getAuthor());
                            tags.setText(filteredByDate.get(m).getTags());
                            place.setText(filteredByDate.get(m).getPlace());
                            date.setText(filteredByDate.get(m).getDate());
                            centerlabel.setIcon(new ImageIcon(scaleclicked));
                            centerpanel.revalidate();
                            centerpanel.repaint();
                            filteredByDateFrame.setVisible(false);

                        });
                    }
                    filteredByDateFrame.setLayout(new GridLayout());
                    filteredByDateFrame.add(filteredByDatePanel);
                    filteredByDateFrame.pack();
                    filteredByDateFrame.setVisible(true);
                }
                if (filteredByDate.size() == 0) {
                    JOptionPane.showMessageDialog(null, "no photos found");
                }
            });
        });
        //save to chosen file
        saveDatabase.addActionListener(sData -> {
            int code = fileChooser.showSaveDialog(mainFrame);
            if (code == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getPath();
                Pattern checkdatabase = Pattern.compile(".*\\.txt\\b");
                Matcher matchdatabase = checkdatabase.matcher(path);
                if (matchdatabase.find()) {
                    try {
                        FileWriter databaseWriter = new FileWriter(path);
                        if (photos.size() > 0) {
                            for (Photo iter : photos) {
                                databaseWriter.write(iter.getPath() + ";" + iter.getTags() + ";" + iter.getDate() + ";" + iter.getAuthor() + ";" + iter.getPlace() + ";" + "\r\n");

                                System.out.println("zapisano " + iter.getPath());
                            }
                            databaseWriter.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("exception z zapisu bazy");
                    }
                } else JOptionPane.showMessageDialog(null, "please select .txt file");
            }
        });

        centerpanel.setBackground(Color.BLUE);
        centerpanel.add(centerlabel);

        borderphotos.setLayout(new BorderLayout());
        borderphotos.add(centerpanel, BorderLayout.CENTER);

        righttextpanel.setLayout(new GridLayout(4, 1));

        author.setEditable(false);
        tags.setEditable(false);
        date.setEditable(false);
        place.setEditable(false);
        righttextpanel.add(author);
        righttextpanel.add(tags);
        righttextpanel.add(date);
        righttextpanel.add(place);
        righttextpanel.setPreferredSize(new Dimension(screenDim.width / 16, screenDim.height));
        borderphotos.add(righttextpanel, BorderLayout.LINE_END);


        scrollphotosleft.setPreferredSize(winDim5);
        scrollphotosleft.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        borderphotos.add(scrollphotosleft, BorderLayout.LINE_START);
        mainFrame.add(borderphotos);
        mainFrame.setVisible(true);

        JMenu sortBy = new JMenu("sort by");
        sortBy.add(sortbyAuthor);
        sortBy.add(sortbyDate);
        sortBy.add(sortbyPlace);

        JMenu reversesortBy = new JMenu("reverse sort by");
        reversesortBy.add(revsortbyAuthor);
        reversesortBy.add(revsortbyDate);
        reversesortBy.add(revsortbyPlace);

        JMenu fileMenu = new JMenu("file");
        fileMenu.add(addItem);
        fileMenu.add(openFileItem);
        fileMenu.add(saveDatabase);

        JMenu showItemWith = new JMenu("Show image with");
        showItemWith.add(showHighestAuthor);
        showItemWith.add(showHighestDate);
        showItemWith.add(showHighestPlace);
        showItemWith.add(showLowestAuthor);
        showItemWith.add(showLowestDate);
        showItemWith.add(showLowestPlace);

        JMenu searchBy = new JMenu("Search By");
        searchBy.add(choosenTag);
        searchBy.add(choosenDate);
        searchBy.add(choosenAuthor);
        searchBy.add(choosenPlace);


        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(sortBy);
        menuBar.add(reversesortBy);
        menuBar.add(deleteimage);
        menuBar.add(editImage);
        menuBar.add(showItemWith);
        menuBar.add(searchBy);
        menuBar.add(dateSearch);

        mainFrame.setJMenuBar(menuBar);
        mainFrame.setSize(screenDim.width / 2, screenDim.height / 2);
        mainFrame.setLocation(screenDim.width / 4, screenDim.height / 2);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

    }

    private static void recreatebuttons(Dimension winDim10, Dimension winDim2, JLabel centerlabel, JPanel centerpanel, JPanel gridphots, JTextArea author, JTextPane date, JTextArea tags, JTextPane place, ArrayList<Photo> photos) {
        for (int i = 0; i < photos.size(); i++) {
            JButton[] tab = new JButton[photos.size()];
            tab[i] = new JButton();
            ImageIcon img = new ImageIcon(photos.get(i).getPath());
            Image scaleImage = img.getImage().getScaledInstance(winDim10.width - 50, 100, Image.SCALE_DEFAULT);
            tab[i].setIcon(new ImageIcon(scaleImage));
            gridphots.add(tab[i]);
            System.out.println(photos.get(i).getPath());
            gridphots.setLayout(new GridLayout(tab.length / 2 + 1, 2));
            int m = i;

            tab[i].addActionListener(a -> {
                ImageIcon clickedimage = new ImageIcon(photos.get(m).getPath());
                Image scaleclicked = clickedimage.getImage().getScaledInstance(winDim2.width, winDim2.height, Image.SCALE_DEFAULT);
                System.out.println("click" + m + photos.get(m).getPath());
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

    public static Boolean isApropriateDate(JTextArea date) {
        String dat = date.getText();
        Pattern datepat = Pattern.compile("([12]?[0-9]|[3][01])-([0]?[0-9]|[1][012])-([0-9]{4})");
        Matcher datematch = datepat.matcher(dat);
        if (datematch.find()) {
            return true;
        } else return false;

    }

    public static Boolean isApropriateDate(JTextField date) {
        System.out.println("uzyto metody 2");
        String dat = date.getText();
        Pattern datepat = Pattern.compile("([12]?[0-9]|[3][01])-([0]?[0-9]|[1][012])-([0-9]{4})");
        Matcher datematch = datepat.matcher(dat);
        if (datematch.find()) {
            return true;
        } else return false;

    }
    public static Boolean isGreaterDate(String[] arrayFromTextArea, String[] arrayFromList) {
        if (Integer.valueOf(arrayFromTextArea[2]) < Integer.valueOf(arrayFromList[2])) {
            return true;
        }

        if (Integer.valueOf(arrayFromTextArea[2]) == Integer.valueOf(arrayFromList[2])) {
        } else if (Integer.valueOf(arrayFromTextArea[1]) < Integer.valueOf(arrayFromList[1])) {
            return true;
        }

        if (Integer.valueOf(arrayFromTextArea[2]) == Integer.valueOf(arrayFromList[2])) {
        } else if (Integer.valueOf(arrayFromTextArea[1]) == Integer.valueOf(arrayFromList[1])) {
        } else if (Integer.valueOf(arrayFromTextArea[0]) < Integer.valueOf(arrayFromList[0])) {
            return true;
        }

        if (Integer.valueOf(arrayFromTextArea[2]) > Integer.valueOf(arrayFromList[2])) {
            return false;
        }

        if (Integer.valueOf(arrayFromTextArea[2]) == Integer.valueOf(arrayFromList[2])) {
        } else if (Integer.valueOf(arrayFromTextArea[1]) > Integer.valueOf(arrayFromList[1])) {
            return false;
        }

        if (Integer.valueOf(arrayFromTextArea[2]) == Integer.valueOf(arrayFromList[2])) {
        } else if (Integer.valueOf(arrayFromTextArea[1]) == Integer.valueOf(arrayFromList[1])) {
        } else if (Integer.valueOf(arrayFromTextArea[0]) > Integer.valueOf(arrayFromList[0])) {
            return false;
        }
        System.out.println("something went wring in date comparison");
        return true;
    }

    public static Boolean isLesserDate(String[] arrayFromTextArea, String[] arrayFromList) {
        if (Integer.valueOf(arrayFromTextArea[2]) < Integer.valueOf(arrayFromList[2])) {
            return false;
        }

        if (Integer.valueOf(arrayFromTextArea[2]) == Integer.valueOf(arrayFromList[2])) {
        } else if (Integer.valueOf(arrayFromTextArea[1]) < Integer.valueOf(arrayFromList[1])) {
            return false;
        }

        if (Integer.valueOf(arrayFromTextArea[2]) == Integer.valueOf(arrayFromList[2])) {
        } else if (Integer.valueOf(arrayFromTextArea[1]) == Integer.valueOf(arrayFromList[1])) {
        } else if (Integer.valueOf(arrayFromTextArea[0]) < Integer.valueOf(arrayFromList[0])) {
            return false;
        }

        if (Integer.valueOf(arrayFromTextArea[2]) > Integer.valueOf(arrayFromList[2])) {
            return true;
        }

        if (Integer.valueOf(arrayFromTextArea[2]) == Integer.valueOf(arrayFromList[2])) {
        } else if (Integer.valueOf(arrayFromTextArea[1]) > Integer.valueOf(arrayFromList[1])) {
            return true;
        }

        if (Integer.valueOf(arrayFromTextArea[2]) == Integer.valueOf(arrayFromList[2])) {
        } else if (Integer.valueOf(arrayFromTextArea[1]) == Integer.valueOf(arrayFromList[1])) {
        } else if (Integer.valueOf(arrayFromTextArea[0]) > Integer.valueOf(arrayFromList[0])) {
            return true;
        }
        System.out.println("something went wring in date comparison");
        return true;
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

