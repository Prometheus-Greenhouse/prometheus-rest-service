```
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```
Usage: kafkacat <options> [file1 file2 .. | topic1 topic2 ..]]
kafkacat - Apache Kafka producer and consumer tool
https://github.com/edenhill/kafkacat
Copyright (c) 2014-2019, Magnus Edenhill
Version 1.5.0 (JSON, Avro, librdkafka 1.1.0 builtin.features=gzip,snappy,ssl,sasl,regex,lz4,sasl_gssapi,sasl_plain,sasl_scram,plugins,zstd,sasl_oauthbearer)


General options:
-C | -P | -L | -Q  Mode: Consume, Produce, Metadata List, Query mode
-G <group-id>      Mode: High-level KafkaConsumer (Kafka >=0.9 balanced consumer groups)
Expects a list of topics to subscribe to
-t <topic>         Topic to consume from, produce to, or list
-p <partition>     Partition
-b <brokers,..>    Bootstrap broker(s) (host[:port])
-D <delim>         Message delimiter character:
a-z.. | \r | \n | \t | \xNN
Default: \n
-E                 Do not exit on non fatal error
-K <delim>         Key delimiter (same format as -D)
-c <cnt>           Limit message count
-F <config-file>   Read configuration properties from file,
file format is "property=value".
The KAFKACAT_CONFIG=path environment can also be used, but -F takes preceedence.
The default configuration file is $HOME/.config/kafkacat.conf
-X list            List available librdkafka configuration properties
-X prop=val        Set librdkafka configuration property.
Properties prefixed with "topic." are
applied as topic properties.
-X schema.registry.prop=val Set libserdes configuration property for the Avro/Schema-Registry client.
-X dump            Dump configuration and exit.
-d <dbg1,...>      Enable librdkafka debugging:
all,generic,broker,topic,metadata,feature,queue,msg,protocol,cgrp,security,fetch,interceptor,plugin,consumer,admin,eos
-q                 Be quiet (verbosity set to 0)
-v                 Increase verbosity
-V                 Print version
-h                 Print usage help

Producer options:
-z snappy|gzip|lz4 Message compression. Default: none
-p -1              Use random partitioner
-D <delim>         Delimiter to split input into messages
-K <delim>         Delimiter to split input key and message
-k <str>           Use a fixed key for all messages.
If combined with -K, per-message keys
takes precendence.
-H <header=value>  Add Message Headers (may be specified multiple times)
-l                 Send messages from a file separated by
delimiter, as with stdin.
(only one file allowed)
-T                 Output sent messages to stdout, acting like tee.
-c <cnt>           Exit after producing this number of messages
-Z                 Send empty messages as NULL messages
file1 file2..      Read messages from files.
With -l, only one file permitted.
Otherwise, the entire file contents will
be sent as one single message.

Consumer options:
-o <offset>        Offset to start consuming from:
beginning | end | stored |
<value>  (absolute offset) |
-<value> (relative offset from end)
s@<value> (timestamp in ms to start at)
e@<value> (timestamp in ms to stop at (not included))
-e                 Exit successfully when last message received
-f <fmt..>         Output formatting string, see below.
Takes precedence over -D and -K.
-J                 Output with JSON envelope
-s key=<serdes>    Deserialize non-NULL keys using <serdes>.
-s value=<serdes>  Deserialize non-NULL values using <serdes>.
-s <serdes>        Deserialize non-NULL keys and values using <serdes>.
Available deserializers (<serdes>):
<pack-str> - A combination of:
<: little-endian,
>: big-endian (recommended),
b: signed 8-bit integer
B: unsigned 8-bit integer
h: signed 16-bit integer
H: unsigned 16-bit integer
i: signed 32-bit integer
I: unsigned 32-bit integer
q: signed 64-bit integer
Q: unsigned 64-bit integer
c: ASCII character
s: remaining data is string
$: match end-of-input (no more bytes remaining or a parse error is raised).
Not including this token skips any
remaining data after the pack-str is
exhausted.
avro       - Avro-formatted with schema in Schema-Registry (requires -r)
E.g.: -s key=i -s value=avro - key is 32-bit integer, value is Avro.
or: -s avro - both key and value are Avro-serialized
-r <url>           Schema registry URL (requires avro deserializer to be used with -s)
-D <delim>         Delimiter to separate messages on output
-K <delim>         Print message keys prefixing the message
with specified delimiter.
-O                 Print message offset using -K delimiter
-c <cnt>           Exit after consuming this number of messages
-Z                 Print NULL values and keys as "NULL"instead of empty.
For JSON (-J) the nullstr is always null.
-u                 Unbuffered output

Metadata options (-L):
-t <topic>         Topic to query (optional)

Query options (-Q):
-t <t>:<p>:<ts>    Get offset for topic <t>,
partition <p>, timestamp <ts>.
Timestamp is the number of milliseconds
since epoch UTC.
Requires broker >= 0.10.0.0 and librdkafka >= 0.9.3.
Multiple -t .. are allowed but a partition
must only occur once.

Format string tokens:
%s                 Message payload
%S                 Message payload length (or -1 for NULL)
%R                 Message payload length (or -1 for NULL) serialized
as a binary big endian 32-bit signed integer
%k                 Message key
%K                 Message key length (or -1 for NULL)
%T                 Message timestamp (milliseconds since epoch UTC)
%h                 Message headers (n=v CSV)
%t                 Topic
%p                 Partition
%o                 Message offset
\n \r \t           Newlines, tab
\xXX \xNNN         Any ASCII character
Example:
-f 'Topic %t [%p] at offset %o: key %k: %s\n'

JSON message envelope (on one line) when consuming with -J:
{ "topic": str, "partition": int, "offset": int,
"tstype": "create|logappend|unknown", "ts": int, // timestamp in milliseconds since epoch
"headers": { "<name>": str, .. }, // optional
"key": str|json, "payload": str|json,
"key_error": str, "payload_error": str } //optional
(note: key_error and payload_error are only included if deserialization failed)

Consumer mode (writes messages to stdout):
kafkacat -b <broker> -t <topic> -p <partition>
or:
kafkacat -C -b ...

High-level KafkaConsumer mode:
kafkacat -b <broker> -G <group-id> topic1 top2 ^aregex\d+

Producer mode (reads messages from stdin):
... | kafkacat -b <broker> -t <topic> -p <partition>
or:
kafkacat -P -b ...

Metadata listing:
kafkacat -L -b <broker> [-t <topic>]

Query offset by timestamp:
kafkacat -Q -b broker -t <topic>:<partition>:<timestamp>
