CC = /*CC*/
LIBS = -lpthread /*LIBS*/
/*CFLAGS*/
SRCS = /*SOURCES*/ runtime.c
OBJS = /*OBJECTS*/ runtime.o

all : /*NAME*/

.c.o :
	${CC} ${CFLAGS} -c $< /*PREPROC_DIRECTIVES*/

.cpp.o :
	${CC} ${CFLAGS} -c $< /*PREPROC_DIRECTIVES*/

/*NAME*/ : $(OBJS)
	$(CC) /*STATIC*/-o $@ $(OBJS) $(LIBS) -lm

clean:
	rm -f *.o *~ /*NAME*/
