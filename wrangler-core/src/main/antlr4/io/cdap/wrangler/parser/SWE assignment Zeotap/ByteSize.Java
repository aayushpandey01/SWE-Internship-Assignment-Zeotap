public class ByteSize extends Token {
    private final long bytes;

    public ByteSize(String value) {
        super(TokenType.BYTE_SIZE, value);
        this.bytes = parseBytes(value);
    }

    private long parseBytes(String str) {
        String unit = str.replaceAll("[^A-Za-z]", "").toUpperCase();
        double num = Double.parseDouble(str.replaceAll("[A-Za-z]", ""));
        switch (unit) {
            case "B": return (long) num;
            case "KB": return (long) (num * 1024);
            case "MB": return (long) (num * 1024 * 1024);
            case "GB": return (long) (num * 1024 * 1024 * 1024);
            case "TB": return (long) (num * 1024 * 1024 * 1024 * 1024);
            default: throw new IllegalArgumentException("Invalid byte unit: " + unit);
        }
    }

    public long getBytes() { return bytes; }
}