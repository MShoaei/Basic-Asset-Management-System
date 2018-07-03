class Location {
    private String school;
    private String owner;
    private String holdingPlace;

    Location(String school, String owner, String holdingPlace) {
        this.setSchool(school);
        this.setOwner(owner);
        this.setHoldingPlace(holdingPlace);
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getHoldingPlace() {
        return holdingPlace;
    }

    public void setHoldingPlace(String holdingPlace) {
        this.holdingPlace = holdingPlace;
    }
}