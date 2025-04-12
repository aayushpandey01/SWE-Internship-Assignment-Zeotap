@Plugin(type = Directive.TYPE)
@Name("aggregate-stats")
@Description("Aggregates byte sizes and time durations.")
public final class AggregateStatsDirective implements Directive {
    private String sourceSizeCol, sourceTimeCol, targetSizeCol, targetTimeCol;
    private String sizeUnit = "B", timeUnit = "ns";
    private String aggType = "total";

    @Override
    public UsageDefinition define() {
        return Arguments.builder()
            .required(sourceSizeCol, "Source column for byte size", TokenType.COLUMN_NAME)
            .required(sourceTimeCol, "Source column for time duration", TokenType.COLUMN_NAME)
            .required(targetSizeCol, "Target column for total size", TokenType.COLUMN_NAME)
            .required(targetTimeCol, "Target column for total time", TokenType.COLUMN_NAME)
            .optional("sizeUnit", "Output unit for size (B, KB, MB, etc.)", TokenType.TEXT, sizeUnit)
            .optional("timeUnit", "Output unit for time (ns, ms, s, etc.)", TokenType.TEXT, timeUnit)
            .optional("aggType", "Aggregation type (total, average)", TokenType.TEXT, aggType)
            .build();
    }

    @Override
    public void initialize(Arguments args) {
        sourceSizeCol = args.value("sourceSizeCol");
        sourceTimeCol = args.value("sourceTimeCol");
        targetSizeCol = args.value("targetSizeCol");
        targetTimeCol = args.value("targetTimeCol");
        sizeUnit = args.value("sizeUnit");
        timeUnit = args.value("timeUnit");
        aggType = args.value("aggType");
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) {
        Store store = context.getStore();
        for (Row row : rows) {
            long bytes = new ByteSize(row.getValue(sourceSizeCol).getBytes();
            long nanos = new TimeDuration(row.getValue(sourceTimeCol)).getNanoseconds();
            store.increment("totalBytes", bytes);
            store.increment("totalNanos", nanos);
            store.increment("count", 1);
        }
        return rows;
    }

    @Override
    public List<Row> finalize(ExecutorContext context) {
        Store store = context.getStore();
        long totalBytes = store.get("totalBytes");
        long totalNanos = store.get("totalNanos");
        long count = store.get("count");

        // Convert to desired units
        double outputSize = convertBytes(totalBytes, sizeUnit);
        double outputTime = convertNanos(totalNanos, timeUnit);

        if ("average".equals(aggType)) {
            outputSize /= count;
            outputTime /= count;
        }

        Row result = new Row();
        result.add(targetSizeCol, outputSize);
        result.add(targetTimeCol, outputTime);
        return Collections.singletonList(result);
    }

    private double convertBytes(long bytes, String unit) {
        switch (unit.toUpperCase()) {
            case "KB": return bytes / 1024.0;
            case "MB": return bytes / (1024.0 * 1024);
            case "GB": return bytes / (1024.0 * 1024 * 1024);
            case "TB": return bytes / (1024.0 * 1024 * 1024 * 1024);
            default: return bytes;
        }
    }

    private double convertNanos(long nanos, String unit) {
        switch (unit.toLowerCase()) {
            case "ms": return nanos / 1_000_000.0;
            case "s": return nanos / 1_000_000_000.0;
            case "m": return nanos / 60_000_000_000.0;
            case "h": return nanos / 3_600_000_000_000.0;
            case "d": return nanos / 86_400_000_000_000.0;
            default: return nanos;
        }
    }
}