package com.example.demo5;
import java.text.DecimalFormat;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class MainController{
    @FXML
    private AnchorPane Map;

    @FXML
    private TextField destTF;

    @FXML
    private TextField outputTF;

    @FXML
    private TextField seedTF;

    @FXML
    private TextField sizeTF;

    @FXML
    private TextField srcTF;

    private String mode = "green";

    private ObservableList<Integer> pathCities = FXCollections.observableArrayList();
    private ArrayList<GraphNode> cities = new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<Label> label = new ArrayList<>();

    private HashMap<Integer, Node> table = new HashMap<Integer, Node>();
    private String sor = "";

    @FXML
    void generateBT(ActionEvent event) {
        for (int i = 0; i < cities.size(); i++) {
            Map.getChildren().remove(cities.get(i).getCircle());
            Map.getChildren().remove(label.get(i));
        }
        cities.clear();
        int n = Integer.parseInt(sizeTF.getText());
        int s = Integer.parseInt(seedTF.getText());

        getCities(s,n);
        initalizeMap();
    }

    @FXML
    void RandomBT(ActionEvent event){
        Random x = new Random();
        int random = x.nextInt(1000);
        seedTF.setText(String.valueOf(random));
    }

    @FXML
    void computeCostBT(ActionEvent event) {
        if (srcTF.getText() != "" && destTF.getText() != "") {



            /*
             * ----> run "shortest path"
             */

            if (!getGraphNode(Integer.parseInt(srcTF.getText())).equals(getGraphNode(Integer.parseInt(destTF.getText())))) {
                for (int i = 0; i < pathCities.size(); i++) {
                    getGraphNode(pathCities.get(i)).getCircle().setFill(Color.web("#008CBA"));

                }
                if (!(sor == ""))
                    getGraphNode(Integer.parseInt(sor)).getCircle().setFill(Color.web("#008CBA"));
                sor = srcTF.getText();
                getShortestPath(getGraphNode(Integer.parseInt(srcTF.getText())), getGraphNode(Integer.parseInt(destTF.getText())));
                for (int i = 0; i < lines.size(); i++) {
                    lines.get(i).setStrokeWidth(2);
                    Map.getChildren().add(lines.get(i));

                }
            } else {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("heeeey!");
                alert.setContentText("You are already in the city");
                alert.show();
            }

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("heeeey!");
            alert.setContentText("You have to choose two cities");
            alert.show();
        }
        srcTF.setText("");
        destTF.setText("");
    }


    @SuppressWarnings("unlikely-arg-type")
    @FXML
    void clearBT(ActionEvent event) {
        Map.getChildren().clear();

        outputTF.setText("");
        srcTF.setText("");
        destTF.setText("");

        for (int i = 0; i < lines.size(); i++) {
            Map.getChildren().remove(lines.get(i));
        }
        if (!(sor == ""))
            getGraphNode(Integer.parseInt(sor)).getCircle().setFill(Color.web("#008CBA"));

        for (int i = 0; i < pathCities.size(); i++) {
            getGraphNode(pathCities.get(i)).getCircle().setFill(Color.web("#008CBA"));

        }

        for (int i = 0; i < cities.size(); i++) {
            Map.getChildren().remove(cities.get(i).getCircle());
            Map.getChildren().remove(label.get(i));
            System.out.println(cities.get(i).getName());
        }

        cities.clear();
        label.clear();
        pathCities.clear();
        lines.clear();
        table.clear();
        sor = "";

    }

    public void initalizeMap() {
        for (int i = 0; i < cities.size(); i++) {
            // the circle that represents the city on the map
            Circle point = new Circle(5);

            // a label that hold the city name
            Label cityName = new Label(cities.get(i).getName() + "");

            final double MAX_FONT_SIZE = 9;
            cityName.setStyle("-fx-font-width: bold;");
            cityName.setFont(new Font(MAX_FONT_SIZE));
            // set circle coordinates


            point.setCenterY((cities.get(i).getCoordinateY()));
            point.setCenterX((cities.get(i).getCoordinateX()));

            // set label beside the circle
            cityName.setLayoutY((cities.get(i).getCoordinateY()));
            cityName.setLayoutX((cities.get(i).getCoordinateX())+3);
            label.add(cityName);

            point.setFill(Color.web("#008CBA"));

            point.setStroke(Color.BLACK);
            point.setStrokeWidth(.3);

            Tooltip tooltip = new Tooltip(
                    cities.get(i).toString().replaceAll("_", " ").replaceAll("Y", "X").replaceFirst("X", " Y"));
            tooltip.setAutoFix(true);
            Tooltip.install(point, tooltip);

            // setting city circle to the circle above
            cities.get(i).setCircle(point);

            // add the circle and the label to the scene
            Map.getChildren().addAll(cities.get(i).getCircle(), cityName);
            String temp = cities.get(i).toString();

            /*
             * Get city name and coordinates, and choose it in the choice box(if it is null)
             * when clicking on the circle
             */

            point.setOnMouseClicked(e -> {
                String[] sp = temp.split("[-]");
                if (mode.equals("green")){
                        srcTF.setText(sp[0].trim());
                        mode = "red";
                    }else if (mode.equals("red")) {
                        destTF.setText(sp[0].trim());
                        mode = "green";
                }
            });

            point.setOnMouseEntered(e -> {
                if (mode.equals("green")){
                    point.setFill(Color.LIGHTGREEN);
                } else if (mode.equals("red"))
                    point.setFill(Color.RED);
            });
            point.setOnMouseExited(e -> {
                point.setFill(Color.web("#008CBA"));
            });
        }
    }

    private void getShortestPath(GraphNode sourceCity, GraphNode targetCity) {
        // reset the table to start new one

        buildTable(sourceCity, targetCity);

        // remove previous lines
        for (int i = 0; i < lines.size(); i++) {
            Map.getChildren().remove(lines.get(i));
        }
        // clear lines Array List
        lines.clear();

        // clear the Observable List that holds all cities between the target and source
        // cities
        pathCities.clear();
        // clear the list view that shows all cities between the target and source
        // cities

        // check if there is path
        if (table.get(targetCity.getName()).getDistance() != Double.POSITIVE_INFINITY
                && table.get(targetCity.getName()).getDistance() != 0) {
            shortestPath(sourceCity, targetCity);
            DecimalFormat df = new DecimalFormat("###.#");
            Node t = table.get(targetCity.getName());
            outputTF.setText(df.format(t.getDistance()));
            sourceCity.getCircle().setFill(Color.web("#1ACF26"));
            targetCity.getCircle().setFill(Color.web("#DB241A"));
            /*
             * Add all the cities that were found between the source and target cities
             */DecimalFormat ds = new DecimalFormat("###.##");
            double path = getDistance(getGraphNode(sourceCity.getName()).getCoordinateX(),
                    getGraphNode(sourceCity.getName()).getCoordinateY(),
                    getGraphNode(pathCities.get(pathCities.size() - 1)).getCoordinateX(),
                    getGraphNode(pathCities.get(pathCities.size() - 1)).getCoordinateY());
            Label l = new Label(ds.format(path) + "");
            l.setTextFill(Color.BLUE);
            // l.setText(sor)t
            l.setLayoutX((getGraphNode(sourceCity.getName()).getCoordinateX()
                    + getGraphNode(pathCities.get(pathCities.size() - 1)).getCoordinateX())/2);
            l.setLayoutY((getGraphNode(sourceCity.getName()).getCoordinateY()
                    + getGraphNode(pathCities.get(pathCities.size() - 1)).getCoordinateY())/2);
            Map.getChildren().add(l);

            for (int i = pathCities.size() - 1; i >= 0; i--) {

                if (i == 0) {
                } else {
                    path = getDistance(getGraphNode(pathCities.get(i)).getCoordinateX(),
                            getGraphNode(pathCities.get(i)).getCoordinateY(),
                            getGraphNode(pathCities.get(i - 1)).getCoordinateX(),
                            getGraphNode(pathCities.get(i - 1)).getCoordinateY());

                    l = new Label(ds.format(path) + "");
                    l.setTextFill(Color.BLUE);

                    // l.setText(sor)t
                    l.setLayoutX((getGraphNode(pathCities.get(i)).getCoordinateX()
                            + getGraphNode(pathCities.get(i - 1)).getCoordinateX())/2);
                    l.setLayoutY((getGraphNode(pathCities.get(i)).getCoordinateY()
                            + getGraphNode(pathCities.get(i - 1)).getCoordinateY())/2);
                    Map.getChildren().add(l);
                }
            }
        } else {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Ther is no path!");
            alert.setContentText("Missing Edges!");
            alert.show();
        }

    }

    private void shortestPath(GraphNode sourceCity, GraphNode targetCity) {
        pathCities.add(targetCity.getName());

        Node t = table.get(targetCity.getName());

        if (t.getSrc() == null) {
            return;
        }
        if (t.getSrc() == sourceCity) {

            if (sourceCity != targetCity) {
                t.getSrc().getCircle().setFill(Color.web("#FFA419"));
                targetCity.getCircle().setFill(Color.web("#FFA419"));

                lines.add(new Line((t.getSrc().getCoordinateX()),
                        (t.getSrc().getCoordinateY()),
                        (targetCity.getCoordinateX()),
                        (targetCity.getCoordinateY())));
            }
            return;
        }

        t.getSrc().getCircle().setFill(Color.web("#FFA419"));
        targetCity.getCircle().setFill(Color.web("#FFA419"));
        lines.add(new Line((t.getSrc().getCoordinateX()),(t.getSrc().getCoordinateY()),(targetCity.getCoordinateX()),(targetCity.getCoordinateY())));
        shortestPath(sourceCity, t.getSrc());
    }

    private void buildTable(GraphNode source, GraphNode targetCity) {

        table.clear();
        for (GraphNode i : cities) {
            table.put(i.getName(), new Node(i, false, Double.POSITIVE_INFINITY, null));
        }

        TableCompare comp = new TableCompare();
        PriorityQueue<Node> q = new PriorityQueue<Node>(9, comp);

        Node node = table.get(source.getName());
        node.setDistance(0.0);
        node.setKnown(true);
        q.add(node);

        while (!q.isEmpty()) {

            Node temp = q.poll();

            temp.setKnown(true);

            if (temp.getCurrentNode() == targetCity) {
                break;
            }
            ArrayList<Adjacent> adj = temp.getCurrentNode().getAdjacent();

            for (Adjacent c : adj) {
                Node t = table.get(c.getGraphNode().getName());
                if (t.isKnown()) {
                    continue;
                }

                // && its distance >= the distance between it and the current source city + all
                // previous path distance
                // && are adjacent
                double newDis = c.getDistance() + temp.getDistance();
                if (newDis < t.getDistance()) {
                    t.setSrc(temp.getCurrentNode());
                    t.setDistance(newDis);
                }
                q.add(t);
            }

        }
    }


    public double getDistance(
            double x1,
            double y1,
            double x2,
            double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }


    private GraphNode getGraphNode(int cityNumber) {

        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName() == cityNumber) {
                return cities.get(i);
            }
        }
        return null;
    }

    private void getCities(int seed,int n) {
        double Width = Map.getWidth();
        double Height = Map.getHeight();
        double x;
        double y;
        Random rx = new Random(seed);
        Random ry = new Random(seed);
        y = ry.nextDouble(Height);
        try {
            System.out.println("Width " + Width + "Height " + Height);

            for (int i = 0; i < n; i++) {
                x = rx.nextDouble(Width);
                y = ry.nextDouble(Height);
                cities.add(new GraphNode(x, y, i, new Circle()));
                System.out.println("x " + x + "y " + y);

            }

            Random rand = new Random(seed);

            // Edit: Add exclude to the random!

            for (int i = 0; i < cities.size(); i++) {
                for (int j = 0; j < 3; j++) {
                    int r = rand.nextInt(cities.size());
                    GraphNode c = getGraphNode(r);
                    Adjacent s = new Adjacent(c, getDistance(c.getCoordinateX(), c.getCoordinateY(),
                            cities.get(i).getCoordinateX(), cities.get(i).getCoordinateY()));

                    cities.get(i).getAdjacent().add(s);

                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
