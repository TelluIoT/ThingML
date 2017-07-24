/*C_HEADERS*/

/*CONFIGURATION*/

/*C_GLOBALS*/

int main(){
	initialize_timer();

	/*INIT_CODE*/

	while(1) {
	/*POLL_CODE*/
	    processMessageQueue();
	}

}