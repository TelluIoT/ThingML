CC = cc
LIBS = -lpthread
FLAGS = -O -w
SRCS = /*SOURCES*/
OBJS = /*OBJECTS*/

all : /*NAME*/

.c.o :
	${CC} ${CFLAGS} -c $<

/*NAME*/ : $(OBJS)
	$(CC) -o $@ $(OBJS) $(LIBS) -lm

clean:
	rm -f *.o *~ /*NAME*/
