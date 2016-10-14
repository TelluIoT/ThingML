#Documentation update tools

#Annotation Dictionary

The file Annotation.html contains a dictionary of all annotations currently used by ThingML compilers. It references both the compiler sources where they are used, and the ThingML examples where they appear.

In order to regenerate it, simply run:
```
java -jar target/Documentrospection-0.7.0-SNAPSHOT-jar-with-dependencies.jar
```

#TagMD

TagMD is a tool to reference code snipet from external files in a markdown file. To do so the markdown file must contains something like:
<pre>
<&#33;-- TagMD tag1 ../ref.txt -->
```
void myNewFunction(int16_t i) {
	printf("i: %in", i);
}
```
</pre>
When TagMD is run, it will replace
```
void myNewFunction(int16_t i) {
	printf("i: %in", i);
}
```

by any content found inside the file `../ref.txt` between `¤begin tag1` and `¤end tag1`.
