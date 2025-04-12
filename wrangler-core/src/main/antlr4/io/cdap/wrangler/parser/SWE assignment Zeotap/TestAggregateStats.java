public class TestAggregateStats {
    @Test
    public void testAggregateTotal() throws Exception {
        List<Row> rows = Arrays.asList(
            new Row("data_size", "10KB").add("response_time", "100ms"),
            new Row("data_size", "1MB").add("response_time", "200ms")
        );

        String[] recipe = {
            "aggregate-stats :data_size :response_time total_size_mb total_time_sec MB s"
        };

        List<Row> results = TestingRig.execute(recipe, rows);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(10.0 + 1024.0, results.get(0).getValue("total_size_mb"), 0.001); // 10KB = 10/1024 MB, 1MB = 1 MB
        Assert.assertEquals(0.3, results.get(0).getValue("total_time_sec"), 0.001); // 100ms + 200ms = 0.3s
    }
}