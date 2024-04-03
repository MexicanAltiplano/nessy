package nessy;

public class risk_data_set {
	private 
	String pet;
    Boolean garden;
    String houseColor;
    Double age;
    Boolean children;
    Boolean woodenDoor;
    Double distance;

    public
    risk_data_set() {};
    
    String getPet() { return pet; }
    void setPet(String value) { this.pet = value; }

    Boolean getGarden() { return garden; }
    void setGarden(Boolean value) { this.garden = value; }

    String getHouseColor() { return houseColor; }
    void setHouseColor(String value) { this.houseColor = value; }

    Double getAge() { return age; }
    void setAge(Double value) { this.age = value; }

    Boolean getChildren() { return children; }
    void setChildren(Boolean value) { this.children = value; }

    Boolean getWoodenDoor() { return woodenDoor; }
    void setWoodenDoor(Boolean value) { this.woodenDoor = value; }

    Double getDistance() { return distance; }
    void setDistance(Double value) { this.distance = value; }
}
