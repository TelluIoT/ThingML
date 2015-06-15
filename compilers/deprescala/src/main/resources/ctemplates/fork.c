// Fork a thread to execute the function /*NAME*/

// Struct for the parameters of the function  /*NAME*/
struct /*NAME*/_struct {
  /*STRUCT_PARAMS*/
  pthread_mutex_t *lock;
  pthread_cond_t *cv;
};

// Definition of function start_receiver_process (executed in a separate thread)
void /*NAME*/_run(/*PARAMS*/)
{
  /*CODE*/
}


void /*NAME*/_proc(void * p)
{
  // Get parameters from the main thread
  struct /*NAME*/_struct params = *((struct /*NAME*/_struct *) p);
  
  // Unblock the main thread
  pthread_mutex_lock(params.lock);
  pthread_cond_signal(params.cv);
  pthread_mutex_unlock(params.lock);
  
  // Run the function
  ///*NAME*/_run(params._instance, params.esums_device);
  /*NAME*/_run(/*ACTUAL_PARAMS*/);
}

// Operation called by the runtime and forking a new thread
void /*NAME*/(/*PARAMS*/)
{
  // Store parameters
  struct /*NAME*/_struct params;
  pthread_mutex_t lock;
  pthread_cond_t cv;
  params.lock = &lock;
  params.cv = &cv;
  /*STORE_PARAMS*/
  pthread_mutex_init(params.lock, NULL);
  pthread_cond_init(params.cv, NULL);
  //Start the new thread
  pthread_mutex_lock(params.lock);
  pthread_t thread; 
  pthread_create( &thread, NULL, /*NAME*/_proc, (void*) &params);
  // Wait until it has started and read its parameters
  pthread_cond_wait(params.cv, params.lock);
  // Realease mutex
  pthread_mutex_unlock(params.lock);
}