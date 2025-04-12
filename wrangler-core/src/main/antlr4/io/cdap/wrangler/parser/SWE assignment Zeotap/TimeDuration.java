public class TimeDuration extends Token {
    private final long nanoseconds;

    public TimeDuration(String value) {
        super(TokenType.TIME_DURATION, value);
        this.nanoseconds = parseDuration(value);
    }

    private long parseDuration(String str) {
        String unit = str.replaceAll("[^A-Za-z]", "").toLowerCase();
        double num = Double.parseDouble(str.replaceAll("[A-Za-z]", ""));
        switch (unit) {
            case "ms": return (long) (num * 1_000_000);
            case "s": return (long) (num * 1_000_000_000);
            case "m": return (long) (num * 60_000_000_000L);
            case "h": return (long) (num * 3_600_000_000_000L);
            case "d": return (long) (num * 86_400_000_000_000L);
            default: throw new IllegalArgumentException("Invalid time unit: " + unit);
        }
    }

    public long getNanoseconds() { return nanoseconds; }
}