@Override
public TokenGroup visitByteSizeArg(DirectivesParser.ByteSizeArgContext ctx) {
    return new TokenGroup().add(new ByteSize(ctx.getText()));
}

@Override
public TokenGroup visitTimeDurationArg(DirectivesParser.TimeDurationArgContext ctx) {
    return new TokenGroup().add(new TimeDuration(ctx.getText()));
}