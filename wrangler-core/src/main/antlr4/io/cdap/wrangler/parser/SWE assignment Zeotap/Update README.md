## New Features: Byte Size and Time Duration Parsers

### Usage:
- **Byte Size**: Use units like `B`, `KB`, `MB`, `GB`, `TB` (case-insensitive).  
  Example: `10KB`, `5.5MB`.

- **Time Duration**: Use units like `ms`, `s`, `m`, `h`, `d` (case-insensitive).  
  Example: `150ms`, `2.5h`.

### Directive: `aggregate-stats`
Aggregates values from columns containing byte sizes and time durations.  
Example recipe: