package example;

public abstract class Discipline {
    private String name;
    private String description;

    public Discipline(String name) {
        this.name = name;
    }

    public Discipline(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
