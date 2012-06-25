CC = cc
LIBS = -lpthread
CFLAGS = -O -w
SRCS = /*SOURCES*/ runtime.c
OBJS = /*OBJECTS*/ runtime.o

all : /*NAME*/

.c.o :
	${CC} ${CFLAGS} -c $<

/*NAME*/ : $(OBJS)
	$(CC) -o $@ $(OBJS) $(LIBS) -lm

clean:
	rm -f *.o *~ /*NAME*/
