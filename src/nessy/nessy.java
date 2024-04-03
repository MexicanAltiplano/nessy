package nessy;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class nessy extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	JTextArea textarea;
    JButton button;
    JTextField textfield;
    JLabel label;
    JPanel panel;
    Double actual_risk;
    String jsonString;
    risk_calculator calculator;
    Boolean test;
 
    public nessy(){
        this.setTitle("Nessy");
        this.setSize(550, 370);
        panel = new JPanel();
        textarea = new JTextArea(10, 30);
        button = new JButton("Calc");
        textfield = new JTextField(null, 15);
        Font font = new Font(Font.SANS_SERIF,Font.PLAIN,20);
        
		jsonString = "{\n\t\"pet\": \"HUND\", \n\t\"garden\": true, \n\t\"houseColor\": \"BLAU\", \n\t\"age\": 1, \n\t\"children\": false, \n\t\"woodenDoor\": false, \n\t\"distance\": 0\n}"; // 0.017334492
//		jsonString = "{\"pet\": \"KATZE\", \"garden\": false, \"houseColor\": \"GELB\", \"age\": 2, \"children\": false, \"woodenDoor\": false, \"distance\": 0}"; // 0.013947648
//		jsonString = "{\"pet\": \"GORILLA\", \"garden\": false, \"houseColor\": \"BLAU\", \"age\": 3, \"children\": false, \"woodenDoor\": true, \"distance\": 0}"; // 0
//		jsonString = "{\"pet\": \"SCHILDKROETE\", \"garden\": false, \"houseColor\": \"GRAU\", \"age\": 4, \"children\": true, \"woodenDoor\": false, \"distance\": 0}"; // 0.012
//		jsonString = "{\"pet\": \"PAPAGEI\", \"garden\": true, \"houseColor\": \"null\", \"age\": 5, \"children\": false, \"woodenDoor\": false, \"distance\": 0}"; // 0.056240658
//		jsonString = "{\"pet\": \"HAMSTER\", \"garden\": true, \"houseColor\": \"GRUEN\", \"age\": 6, \"children\": false, \"woodenDoor\": \"null\", \"distance\": 0}"; // ?
        
        textarea.setText(jsonString);
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setTabSize(3);
        textarea.setFont(font);
        button.addActionListener(this);
        button.setFont(font);
        textfield.setFont(font);
        panel.add(textarea);
        panel.add(button);
        panel.add(textfield);
        this.add(panel);
				
		Gson gson = new Gson();
		GsonBuilder builder = new GsonBuilder();
		
		gson = builder.create();
		risk_data_set data_set = gson.fromJson(jsonString, risk_data_set.class);
		calculator = new risk_calculator(data_set);
		actual_risk = calculator.calculate_risk();

		System.out.println("Tatsächliches Risiko: " + actual_risk.toString());
    }
 
    public static void main(String[] args)
    {
    	nessy inputOutputFrame = new nessy();
        inputOutputFrame.setVisible(true);
    }
 
    public void actionPerformed (ActionEvent ae){
		jsonString = textarea.getText();
		this.calculator.setDataSet(jsonString);
		this.actual_risk = this.calculator.calculate_risk();
    	textfield.setText(actual_risk.toString());
    }
}