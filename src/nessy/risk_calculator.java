package nessy;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class risk_calculator {
	private
	Double com_risk	= 0.012;
	Double actual_risk;
	Double house_color_risk;
	Double replace_garden_risk;
	Double replace_children_risk;
	
	HashMap<String, Double> pet_risks = new HashMap<>();
	HashMap<Boolean, Double> garden_risks = new HashMap<>();
	HashMap<String, Double> house_color_summand = new HashMap<>();
	
	risk_data_set data_set = new risk_data_set();
	
	public risk_calculator(risk_data_set data) {
		pet_risks.put("HUND", 1.7);
		pet_risks.put("KATZE", 1.27);
		pet_risks.put("SCHILDKROETE", 0.83);
		pet_risks.put("DEFAULT", 1.31);
		
		garden_risks.put(true, 1.22);
		garden_risks.put(false, 0.64);
		
		house_color_summand.put("GELB", 0.2);
		house_color_summand.put("WEISS", 0.1);
		house_color_summand.put("GRUEN", -0.8);
		house_color_summand.put("BLAU", -0.105);
		house_color_summand.put("DEFAULT", 0.15);
		house_color_risk = 1.1;
		
		data_set = data;

		set_replace_values();
	}
	
	Double calculate_risk() {
		actual_risk = com_risk
				* pet_risks.getOrDefault(data_set.getPet(), pet_risks.get("DEFAULT"))
				* garden_risks.getOrDefault(data_set.getGarden(), replace_garden_risk)
				* (house_color_risk + house_color_summand.getOrDefault(data_set.getHouseColor(), house_color_summand.get("DEFAULT")))
				* getAgeFactor(data_set.getAge())
				;
		actual_risk = actual_risk * getDistanceFactor(data_set.getDistance());
		

		if(data_set.getWoodenDoor() == null) {
			if(data_set.getChildren() == null)
				actual_risk = (actual_risk + com_risk) / 2;//4;
			else if(data_set.getChildren() == true)
				actual_risk = com_risk;// / 2;
			else
				actual_risk = actual_risk / 1;//2;
		}
		else if(data_set.getWoodenDoor() == true)
			actual_risk = .0;
		else {
			if(data_set.getChildren() == null)
				actual_risk = (actual_risk + com_risk) / 2;
			else if(data_set.getChildren() == true)
				actual_risk = com_risk;
		}
		
		return actual_risk;
	}
	
	void set_replace_values() {
		find_replace_value(pet_risks);
		find_replace_value(house_color_summand);
		replace_garden_risk = (garden_risks.get(true) + garden_risks.get(false)) / 2;
	}
	
	void find_replace_value(HashMap<String, Double> list) {
		Double replace_value;
        Double highestValue = Double.MIN_VALUE;
        Double secondHighestValue = Double.MIN_VALUE;
        
        for (Map.Entry<String, Double> entry : list.entrySet()) {
            if (entry.getValue() > highestValue) {
                secondHighestValue = highestValue;
                highestValue = entry.getValue();
            } else if (entry.getValue() > secondHighestValue) {
                secondHighestValue = entry.getValue();
            }
        }
        replace_value = ((secondHighestValue + highestValue)/2);
        
        list.put(null, replace_value);
	}
	
	Double getAgeFactor(Double age) {
		if(age == null)
			return (1.1 + 2.3) / 2;
					
		if(.0 <= age && age < 2.0)
			return 0.7;
		else if(2.0 <= age && age < 5.0)
			return 1.1;
		else
			return 2.3;
	}
	
	Double getDistanceFactor(Double distance) {
		if(distance == null)
			return 1.0;//0.5;
		else
			return Math.exp(-distance);
	}
	
	void setDataSet(String jsonString) {
		Gson gson = new Gson();
		GsonBuilder builder = new GsonBuilder();
		
		gson = builder.create();
		this.data_set = gson.fromJson(jsonString, risk_data_set.class);
	};
}


//Double calculate_risk() {
//	if(data_set.getWoodenDoor() == true)
//		return .0;
//	
//	actual_risk = com_risk
//			* pet_risks.getOrDefault(data_set.getPet(), pet_risks.get("DEFAULT"))
//			* garden_risks.get(data_set.getGarden())
//			* (house_color_risk + house_color_summand.getOrDefault(data_set.getHouseColor(), house_color_summand.get("DEFAULT")))
//			* getAgeRisk(data_set.getAge())
//			;
//	actual_risk = actual_risk / Math.exp(data_set.getDistance());
//	
//	if(data_set.getChildren() == true)
//		return com_risk;
//	else if(data_set.getChildren() == false)
//		return actual_risk;
//	else
//		return (com_risk + actual_risk)/2;		
//}
