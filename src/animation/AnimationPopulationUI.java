package animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cellSociety.CellSociety;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import page.Page;

public class AnimationPopulationUI extends Page {
	
	public AnimationPopulationUI(CellSociety cs, String language) {
		super(cs, language);
	}

	private List<Color> colorKey;
	
	public void createChart(Map<Color, Integer> quantityMap){
		
		final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        
        final BarChart<Number, String> populationChart = new BarChart<Number,String>(xAxis,yAxis);
        
        populationChart.setTitle("Population Levels");
        xAxis.setLabel("Quantity");  
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Colors");
        
        XYChart.Series<Number, String> populationSeries = new Series<Number, String>();
        
        colorKey = new ArrayList<Color>(quantityMap.keySet());
        
        for (int x = 0; x<quantityMap.keySet().size(); x++){
        	String color = colorKey.get(x).toString();
        	Number quantity = quantityMap.get(colorKey.get(x));
        	populationSeries.getData().add(new Data<Number, String>(quantity, color));
        }
        
        Timeline time = new Timeline();
        time.getKeyFrames().add(new KeyFrame(Duration.millis(60), 
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                for (Series<Number, String> series : populationChart.getData()) {
                    for (int x = 0; x<populationChart.getData().size(); x++) {
                    	XYChart.Data<Number, String> data = series.getData().get(x);
                        data.setXValue(quantityMap.get(colorKey.get(x)));
                    }
                }
            }
        }));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
	}
	
	
}
