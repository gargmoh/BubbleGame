package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import javafx.util.Duration;

public class Main extends Application {
	protected static final int MAX_SIZE = 150;
	Color[] colors = { Color.RED, Color.AQUA, Color.DARKGOLDENROD, Color.BLANCHEDALMOND, Color.GREENYELLOW };
	private Canvas canvas = new Canvas(WIDTH, HEIGHT);
	private static int WIDTH = 1050;
	private static int HEIGHT = 650;
	private HBox hb1;
	private int counter = 1;
	private boolean bulletExists = false;
	private Timeline animation;
	private Timeline shooting;
	ArrayList<Circle> list1 = new ArrayList<Circle>();
	ArrayList<Square> list2 = new ArrayList<Square>();
	GraphicsContext gc = canvas.getGraphicsContext2D();
	private Circle c;
	private Square s;
	private int counter2 = 1;
	Line bullet;

	@Override
	public void start(Stage primaryStage) {
		try {

			primaryStage.setTitle("My project Game");
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

			Button btn1 = new Button("Circle");

			btn1.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// System.out.println("It's a circle");
					GraphicsContext gc = canvas.getGraphicsContext2D();

					c = new Circle();
					Random r = new Random();
					c.x = r.nextInt(WIDTH);
					c.y = r.nextInt(HEIGHT);
					c.d = r.nextInt(MAX_SIZE);
					list1.add(c);
					c.index = pickRandomColor();
					gc.setStroke(colors[c.index]);

					gc.strokeOval(c.x, c.y, c.d, c.d);
				}
			});
			Button btn2 = new Button("Square");

			btn2.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// System.out.println("It's a square");
					GraphicsContext gc = canvas.getGraphicsContext2D();
					s = new Square();
					Random r = new Random();

					s.a = r.nextInt(WIDTH);
					s.b = r.nextInt(HEIGHT);
					s.z = r.nextInt(MAX_SIZE);
					list2.add(s);
					s.index = pickRandomColor();
					gc.setStroke(colors[s.index]);

					gc.strokeRect(s.a, s.b, s.z, s.z);
				}
			});
			Button btn3 = new Button("Start");
			btn3.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					gc.clearRect(0, 0, WIDTH, HEIGHT);
					if (counter == 1) {
						animation = new Timeline(new KeyFrame(Duration.millis(10), e -> stepit()));
						animation.setCycleCount(Timeline.INDEFINITE);
						animation.play(); // Start animation
						counter++;
					}
				}

			});
			Button btn4 = new Button();
			btn4.setText("Stop");
			btn4.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if(counter2==1){
					animation.pause();
					}
					counter--;
				}
			});
			Button btn5 = new Button("Bullet");
			btn5.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (bulletExists) {
						gc.setStroke(Color.AZURE);
						gc.strokeLine(bullet.x1, bullet.y1, bullet.x2, bullet.y2);
					}

					createBullet();

				}
			});
			Button btn6 = new Button("Shoot");
			btn6.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					shooting = new Timeline(new KeyFrame(Duration.millis(10), e -> moveBullet()));
					shooting.setCycleCount(Timeline.INDEFINITE);
					shooting.play(); // Start animation

				}
			});
			Button btn7 = new Button("Save");
			btn7.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("Save");
					saveCircles();

				}
			});
			Button btn8 = new Button("Restore");
			btn8.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					restoreCirclesandSquares();

				}
			});

			HBox hb2 = new HBox();
			hb2.setAlignment(Pos.CENTER);

			hb1 = new HBox(10);
			hb1.setAlignment(Pos.CENTER);
			hb1.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8);
			BorderPane root = new BorderPane();
			root.setBottom(hb1);
			root.setCenter(canvas);
			Scene scene = new Scene(root, 1100, 700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int pickRandomColor() {
		
		Random r = new Random();
		int i = r.nextInt(5);
		return i;
	}

	public void stepit() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for (Circle c : list1) {

			if (c.yDirection == 1) {
				c.y += 1;
				if (c.y + c.d >= HEIGHT) {
					c.yDirection = -1;

				}
			} else {
				c.y -= 1;
				if (c.y <= 0) {
					c.yDirection = 1;

				}

			}
			if (c.xDirection == 1) {
				c.x += 1;
				if (c.x + c.d >= WIDTH) {
					c.xDirection = -1;
				}

			} else {
				c.x -= 1;
				if (c.x <= 0) {
					c.xDirection = 1;
				}
			}

			gc.setStroke(colors[c.index]);
			gc.strokeOval(c.x, c.y, c.d, c.d);

		}
		for (Square s : list2) {

			if (s.bDirection == 1) {
				s.b += 1;
				if (s.b + s.z >= HEIGHT) {
					s.bDirection = -1;

				}
			} else {
				s.b -= 1;
				if (s.b <= 0) {
					s.bDirection = 1;

				}

			}
			if (s.aDirection == 1) {
				s.a += 1;
				if (s.a + s.z >= WIDTH) {
					s.aDirection = -1;
				}

			} else {
				s.a -= 1;
				if (s.a <= 0) {
					s.aDirection = 1;
				}
			}

			gc.setStroke(colors[s.index]);
			gc.strokeRect(s.a, s.b, s.z, s.z);

		}
		if (bulletExists) {
			if (bullet.y2 < 0) {
				bulletExists = false;
				shooting.pause();
			}

			gc.setStroke(Color.LIME);
			gc.strokeLine(bullet.x1, bullet.y1, bullet.x2, bullet.y2);
			for (Square s : list2) {
				if (bullet.x1 >= s.a && bullet.x1 <= s.a + s.z && bullet.y1 >= s.b && bullet.y1 <= s.b + s.z) {
					list2.remove(s);
				}
			}
			for (Circle c : list1) {
				if (bullet.x1 >= c.x && bullet.x1 <= c.x + c.d && bullet.y1 >= c.y && bullet.y1 <= c.y + c.d) {
					list1.remove(c);
				}

			}

		}

	}

	public void createBullet() {
		bulletExists = true;
		bullet = new Line();

	}

	public void moveBullet() {
		if (counter2 == 1) {
			bullet.y1 -= 3;
			bullet.y2 -= 3;
		}

	}

	public void saveCircles() {

		// Write c.x,c.y,c.size to the text file
		try {
			File file1 = new File("circlegame.txt");
			File file2 = new File("squaregame.txt");
			// if file doesn't exists, then create it
			if (!file1.exists()) {
				file1.createNewFile();
			}

			FileWriter fw = new FileWriter(file1.getAbsoluteFile());
			FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			BufferedWriter bw2 = new BufferedWriter(fw2);

			for (Circle c : list1) {
				bw.write("Circle: " + c.x + "," + c.y + "," + c.d+","+c.index);
				bw.newLine();
			}
			bw.close();
			for (Square s : list2) {
				bw2.write("Square: " + s.a + "," + s.b + "," + s.z+","+s.index);
				bw2.newLine();
			}

			bw2.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}

	}

	public void restoreCirclesandSquares() {
		// The name of the file to open.

		// This will reference one line at a time

		try {

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(new FileReader("circlegame.txt"));
			BufferedReader bufferedReader2 = new BufferedReader(new FileReader("squaregame.txt"));
			String line = bufferedReader.readLine();
			String line2 = bufferedReader2.readLine();
			while (line != null) {
				System.out.println(line);
				convert(line);
				line = bufferedReader.readLine();
			}
			// Always close files.
			bufferedReader.close();
			while (line2 != null) {
				System.out.println(line2);
				convert2(line2);
				line2 = bufferedReader2.readLine();
			}
			// Always close files.
			bufferedReader2.close();

		}

		catch (IOException ex) {
			// ok
		}
	}

	public void convert(String line) {

		String[] splited = line.split(":");
		String shape = splited[0];
		String values = splited[1];
		String[] nums = values.split(",");
		
		int x = Integer.parseInt(nums[0].trim());
		int y = Integer.parseInt(nums[1].trim());
		int d = Integer.parseInt(nums[2].trim());
		int index = Integer.parseInt(nums[3].trim());
	    c = new Circle(x, y, d, index);
		c.x = x;
		c.y = y;
		c.d = d;
        c.index=index;
		list1.add(c);
		
		
		
		gc.setStroke(colors[c.index]);
		gc.strokeOval(c.x, c.y, c.d, c.d);
		
	}
	public void convert2(String line2) {

		String[] splited = line2.split(":");
		String shape2 = splited[0];
		String values2 = splited[1];
		String[] nums2 = values2.split(",");
		int a = Integer.parseInt(nums2[0].trim());
		int b = Integer.parseInt(nums2[1].trim());
		int z = Integer.parseInt(nums2[2].trim());
		int  index= Integer.parseInt(nums2[3].trim());
		s=new Square(a,b,z,index);
		s.a=a;
		s.b=b;
		s.z=z;
		s.index=index;
		list2.add(s);
		

		gc.setStroke(colors[s.index]);
		gc.strokeRect(s.a,s.b,s.z,s.z);
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
