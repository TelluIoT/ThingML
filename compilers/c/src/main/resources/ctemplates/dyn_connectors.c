
struct Msg_Handler * /*CONFIGURATION*/_dyn_co_handlers[/*NB_INSTANCE_PORT*/];
struct Msg_Handler *** /*CONFIGURATION*/_dyn_co_rlist_head[/*NB_INSTANCE_PORT*/];
struct Msg_Handler *** /*CONFIGURATION*/_dyn_co_rlist_tail[/*NB_INSTANCE_PORT*/];

//Init
void /*CONFIGURATION*/_init_dyn_co() {
	/*
        /*COMMENT_ID_PORT*/
        */
	
	/*INIT_DYN_CO*/
}

/*
void print_receivers_table() {
	printf("[reiceivers] -------- Print ---------\n");
	int g = 0;
	for(g = 0; g < NB_MAX_CONNEXION; g++) {
		printf("[reiceivers] %i:%i\n", g, /*CONFIGURATION*/_receivers[g]);
	}
	printf("[reiceivers] -------- End ---------\n");
}

void print_handlers_table() {
	printf("[handlers] -------- Print ---------\n");
	int g = 0;
	for(g = 0; g < /*NB_INSTANCE_PORT*/; g++) {
		printf("[handlers] %i:%i\n", g, /*CONFIGURATION*/_dyn_co_handlers[g]);
	}
	printf("[handlers] -------- End ---------\n");
}

void print_ht_table() {
	printf("[ht] -------- Print ---------\n");
	int g = 0;
	for(g = 0; g < /*NB_INSTANCE_PORT*/; g++) {
		if(*/*CONFIGURATION*/_dyn_co_rlist_head[g] != NULL) {
			printf("[ht] %i h:%i t:%i\n", g, **/*CONFIGURATION*/_dyn_co_rlist_head[g], **/*CONFIGURATION*/_dyn_co_rlist_tail[g]);
		} else {
			printf("[ht] %i h:%i t:%i\n", g, 0, **/*CONFIGURATION*/_dyn_co_rlist_tail[g]);
		}
	}
	printf("[ht] -------- End ---------\n");
}
*/

void /*CONFIGURATION*/_dyn_unidir_connect(uint16_t p1_id, uint16_t p2_id) {
	struct Msg_Handler ** lookup;
	struct Msg_Handler ** cur;
	struct Msg_Handler * tmp;
	struct Msg_Handler * to_copy;

	bool found = false;
	if(/*CONFIGURATION*/_dyn_co_handlers[p2_id] != NULL) {
		if(*/*CONFIGURATION*/_dyn_co_rlist_head[p1_id] != NULL) {
			lookup = */*CONFIGURATION*/_dyn_co_rlist_head[p1_id];
			while((*lookup != **/*CONFIGURATION*/_dyn_co_rlist_tail[p1_id]) && (!found)) {
				if(*lookup == /*CONFIGURATION*/_dyn_co_handlers[p2_id]) {
					found = true;
					break;
				}
				lookup++;
			}
			
			if((*lookup == /*CONFIGURATION*/_dyn_co_handlers[p2_id]) && (!found)) {
				found = true;
			}
		} else {
			lookup = */*CONFIGURATION*/_dyn_co_rlist_tail[p1_id];
		}
		
		if(!found) {
			cur = */*CONFIGURATION*/_dyn_co_rlist_tail[p1_id];
			to_copy = /*CONFIGURATION*/_dyn_co_handlers[p2_id];

			while((to_copy != NULL) 
				&& (cur != &/*CONFIGURATION*/_receivers[NB_MAX_CONNEXION-1])) {
				tmp = *cur;
				*cur = to_copy;
				to_copy = tmp;
				cur++;
			}

			int k;
			for(k = p1_id + 1; k < /*NB_INSTANCE_PORT*/; k++) {
				if(*/*CONFIGURATION*/_dyn_co_rlist_head[k] != NULL) {
					(*/*CONFIGURATION*/_dyn_co_rlist_head[k])++;
				}
				(*/*CONFIGURATION*/_dyn_co_rlist_tail[k])++;
			}
			if(*/*CONFIGURATION*/_dyn_co_rlist_head[p1_id] == NULL) {
				*/*CONFIGURATION*/_dyn_co_rlist_head[p1_id] = */*CONFIGURATION*/_dyn_co_rlist_tail[p1_id];
			} else {
				(*/*CONFIGURATION*/_dyn_co_rlist_tail[p1_id])++;
			}
		}
	}
}

void /*CONFIGURATION*/_dyn_connect(uint16_t p1_id, uint16_t p2_id) {
	
	/*CONFIGURATION*/_dyn_unidir_connect(p1_id, p2_id);
	/*CONFIGURATION*/_dyn_unidir_connect(p2_id, p1_id);
}

void /*CONFIGURATION*/_dyn_unidir_deconnect(uint16_t p1_id, uint16_t p2_id) {
	struct Msg_Handler ** cur;
	struct Msg_Handler ** tmp;

	bool found = false;
	if(/*CONFIGURATION*/_dyn_co_handlers[p2_id] != NULL) {
		if(*/*CONFIGURATION*/_dyn_co_rlist_head[p1_id] != NULL) {
			cur = */*CONFIGURATION*/_dyn_co_rlist_head[p1_id];
			while((*cur != **/*CONFIGURATION*/_dyn_co_rlist_tail[p1_id]) && (!found)) {
				if(*cur == /*CONFIGURATION*/_dyn_co_handlers[p2_id]) {
					found = true;
					break;
				}
				cur++;
			}
			if((*cur == /*CONFIGURATION*/_dyn_co_handlers[p2_id]) && (!found)) {
				found = true;
			}
		
			if(found) {
				while((*cur != NULL) 
					&& (cur != &/*CONFIGURATION*/_receivers[NB_MAX_CONNEXION-2])) {
					tmp = cur;
					tmp++;
					*cur = *tmp;
					cur++;
				}
				if(cur == &/*CONFIGURATION*/_receivers[NB_MAX_CONNEXION-2]) {
					tmp = cur;
					tmp++;
					*cur = *tmp;
					cur++;
					*cur = NULL;
				}

				int k;
				for(k = p1_id + 1; k < /*NB_INSTANCE_PORT*/; k++) {
					if(*/*CONFIGURATION*/_dyn_co_rlist_head[k] != NULL) {
						(*/*CONFIGURATION*/_dyn_co_rlist_head[k])--;
					}
					(*/*CONFIGURATION*/_dyn_co_rlist_tail[k])--;
				}
				if(*/*CONFIGURATION*/_dyn_co_rlist_head[p1_id] == */*CONFIGURATION*/_dyn_co_rlist_tail[p1_id]) {
					*/*CONFIGURATION*/_dyn_co_rlist_head[p1_id] = NULL;
				} else {
					(*/*CONFIGURATION*/_dyn_co_rlist_tail[p1_id])--;
				}
			}
		}
	}
}

void /*CONFIGURATION*/_dyn_deconnect(uint16_t p1_id, uint16_t p2_id) {
	
	/*CONFIGURATION*/_dyn_unidir_deconnect(p1_id, p2_id);
	/*CONFIGURATION*/_dyn_unidir_deconnect(p2_id, p1_id);
}
