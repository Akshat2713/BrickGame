public enum Difficulty {
    EASY,
    MEDIUM,
    HARD;

    public String getDisplayName() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase(); // Capitalizes the first letter and lowercases the rest
    }
}
