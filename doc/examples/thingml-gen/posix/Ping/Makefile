# CC = /*CC*/
LIBS = -lpthread 
CFLAGS = -O2 -w
SRCS = PingServer.c PingClient.c Ping_cfg.c runtime.c
OBJS = PingServer.o PingClient.o Ping_cfg.o runtime.o

all : Ping

.c.o :
	${CC} ${CFLAGS} -c $< 

.cpp.o :
	${CC} ${CFLAGS} -c $< 

Ping : $(OBJS)
	$(CC) -o $@ $(OBJS) $(LIBS) -lm

clean:
	rm -f *.o *~ Ping
