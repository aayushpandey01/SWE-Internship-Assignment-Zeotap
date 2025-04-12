// Add lexer rules for BYTE_SIZE and TIME_DURATION
fragment DIGIT: [0-9];
NUMBER: DIGIT+ ('.' DIGIT+)?;

// Byte size units (case-insensitive)
fragment B: [bB];
fragment K: [kK];
fragment M: [mM];
fragment G: [gG];
fragment T: [tT];
fragment BYTE_UNIT: B | K B | M B | G B | T B;
BYTE_SIZE: NUMBER BYTE_UNIT;

// Time duration units (case-insensitive)
fragment MS: [mM][sS];
fragment S: [sS];
fragment MIN: [mM];
fragment H: [hH];
fragment D: [dD];
fragment TIME_UNIT: MS | S | MIN | H | D;
TIME_DURATION: NUMBER TIME_UNIT;

// Update parser rules
byteSizeArg: BYTE_SIZE;
timeDurationArg: TIME_DURATION;
value: ... | byteSizeArg | timeDurationArg;
