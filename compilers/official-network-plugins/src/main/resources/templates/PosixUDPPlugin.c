#include<stdio.h> //printf
#include<string.h> //memset
#include<stdlib.h> //exit(0);
#include<arpa/inet.h>
#include<sys/socket.h>
 
#define /*PORT_NAME*/_BUFLEN 512  //Max length of buffer

#define /*PORT_NAME*/_LOCAL_PORT /*LOCAL_PORT*/ 
#define /*PORT_NAME*/_REMOTE_PORT /*REMOTE_PORT*/ 
#define /*PORT_NAME*/_REMOTE_ADDR "/*REMOTE_ADDR*/"

struct /*PORT_NAME*/_instance_type {
    uint16_t listener_id;
    /*INSTANCE_INFORMATION*/
};

extern struct /*PORT_NAME*/_instance_type /*PORT_NAME*/_instance;

void /*PORT_NAME*/_set_listener_id(uint16_t id) {
	/*PORT_NAME*/_instance.listener_id = id;
}

struct in_addr addr_from_uint32(uint32_t addr) {
    struct in_addr res;
    res.s_addr = addr;
    return res;
}

struct sockaddr_in /*PORT_NAME*/_si_local, /*PORT_NAME*/_si_remote, /*PORT_NAME*/_si_rcv;
int /*PORT_NAME*/_socket;

/*PARSER_IMPLEMENTATION*/

int /*PORT_NAME*/_setup() {
     
    //create a UDP socket
    if ((/*PORT_NAME*/_socket=socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) == -1)
    {
        printf("Error: socket creation\n");
    }
     
    // zero out the structure
    memset((char *) &/*PORT_NAME*/_si_local, 0, sizeof(/*PORT_NAME*/_si_local));
     
    /*PORT_NAME*/_si_local.sin_family = AF_INET;
    /*PORT_NAME*/_si_local.sin_port = htons(/*PORT_NAME*/_LOCAL_PORT);
    /*PORT_NAME*/_si_local.sin_addr.s_addr = htonl(INADDR_ANY);

    /*REMOTE_CFG_SETUP*/
     
    //bind socket to port
    if( bind(/*PORT_NAME*/_socket , (struct sockaddr*)&/*PORT_NAME*/_si_local, sizeof(/*PORT_NAME*/_si_local) ) == -1)
    {
        printf("Error: socket binding\n");
    }
}

void /*PORT_NAME*/_forwardMessage(byte * msg, uint8_t size/*REMOTE_PARAM*/) {
    /*REMOTE_CFG_FORWARD*/
    if (sendto(/*PORT_NAME*/_socket, msg, size, 0, (struct sockaddr*) &/*PORT_NAME*/_si_remote, sizeof(/*PORT_NAME*/_si_remote)) == -1)
    {
        printf("Error: sendto()\n");
    }
    //printf("Sent packet to %s:%d\n", inet_ntoa(/*PORT_NAME*/_si_remote.sin_addr), ntohs(/*PORT_NAME*/_si_remote.sin_port));
}
	
void /*PORT_NAME*/_start_receiver_process()
{
    char buf[/*PORT_NAME*/_BUFLEN];
    int recv_len;
     
    while(1)
    {
        recv_len = recvfrom(/*PORT_NAME*/_socket, buf, /*PORT_NAME*/_BUFLEN, 0, (struct sockaddr *) &/*PORT_NAME*/_si_rcv, &recv_len);
        if (recv_len < 0)
        {
            //printf("Error: recvfrom()\n");
        }
         
        //printf("Received packet from %s:%d\n", inet_ntoa(/*PORT_NAME*/_si_rcv.sin_addr), ntohs(/*PORT_NAME*/_si_rcv.sin_port));
        //printf("Data: %s\n" , buf);
        /*PARSER_CALL*/
    }
 
    close(/*PORT_NAME*/_socket);
}

/*FORWARDERS*/

