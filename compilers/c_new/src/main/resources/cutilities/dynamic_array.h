#ifndef dynamic_array_h
#define dynamic_array_h


#include <stdlib.h>
#include "dynamic_array.c"

struct dynamic_array {
	uint32_t size;
	uint32_t expansion_factor;
	void ** array;
};

int da_precise_create (struct dynamic_array * ar, uint32_t init_size, uint32_t expansion_factor);

int da_create (struct dynamic_array * ar);

int64_t da_add(struct dynamic_array * ar, void * val);

void da_delete(struct dynamic_array * ar);

#endif