#include <stdlib.h>

int da_precise_create (struct dynamic_array * ar, uint32_t init_size, uint32_t expansion_factor) {
    ar = malloc(sizeof(struct dynamic_array));
    if (ar == NULL) {
        return -1;
    }
    ar->array = malloc(sizeof(void *) * init_size);
    if (ar->array == NULL) {
        return -1;
    }
    ar->size = init_size;
    ar->expansion_factor = expansion_factor;
    return 0;
}

int da_create (struct dynamic_array * ar) {
    return da_precise_create(ar, 8, 2);
}

int64_t da_add(struct dynamic_array * ar, void * val) {
    uint32_t i;
    for(i = 0; i < ar->size; i++) {
        if(ar->array[i] == NULL) {
            ar->array[i] = val;
            return i;
        }
    }
    if(da_expand(ar) != -1) {
        return -1;
    }
    ar->array[i] = val;
    return i;
}

int da_expand(struct dynamic_array * ar) {
    ar->array = realloc(ar->array, sizeof(void *) * ar->size * ar->expansion_factor);
    if (ar->array == NULL) {
        return -1;
    }
    ar->size *= ar->expansion_factor;
    return 0;
}

void da_delete(struct dynamic_array * ar) {
    free(ar->array);
    free(ar);
}