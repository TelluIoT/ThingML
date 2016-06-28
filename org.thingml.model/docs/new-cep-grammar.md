# New ThingML grammar for Complex Event Processing

**Disclaimer**: This document describes features that are not and probably will never be implemented into the ThingML framework, for the actual Complex Event Processing features usage please refer to the according [documentation](cep-semantics.md).

---

Focus on this new grammar is to distinguish precisely the aggregation, from the selection, from the trigger and finally from the production.

```
stream _name_
from (simple_source) | (join_source)+
(trigger (source (by _exp_)?) | (every _exp_)
select _action_)*

simple_source = _event_ : attributes? _source_
join_source = _event_ : [ join_source (& join_source)+ (within _time_)? ]
attributes = (guard _exp_)? (size _exp_)? (ttl _exp_)? (persistent)?
```

Most previous CEP annotations have been turned into keywords.

`@Buffer` is now replaced by `size`, `@TTL` by `ttl` and `@UseOnce "False"` is now `persistent`.

The Tellu use case can then be written as follow:

```
stream tellu
from j:[ p : port?pres persistent & t : port?temp], comp : port?cmp size 3
select var cp : Float = comp(comp.v[])
port!comp(cp)
port!tp(t.v, cp)
```
