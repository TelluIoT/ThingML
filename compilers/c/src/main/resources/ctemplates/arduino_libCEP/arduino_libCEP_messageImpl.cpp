int stream_/*STREAM_NAME*/::/*MESSAGE_NAME*/_length()
{
    if (/*MESSAGE_NAME*/_fifo_tail >= /*MESSAGE_NAME*/_fifo_head)
        return /*MESSAGE_NAME*/_fifo_tail - /*MESSAGE_NAME*/_fifo_head;
    return /*MESSAGE_NAME*/_fifo_tail + /*MESSAGE_NAME_UPPER*/_FIFO_SIZE - /*MESSAGE_NAME*/_fifo_head;
}

int stream_/*STREAM_NAME*/::/*MESSAGE_NAME*/_available()
{
    return /*MESSAGE_NAME_UPPER*/_FIFO_SIZE - 1 - /*MESSAGE_NAME*/_length();
}

inline boolean stream_/*STREAM_NAME*/::/*MESSAGE_NAME*/_isEmpty()
{
    return /*MESSAGE_NAME*/_fifo_tail == /*MESSAGE_NAME*/_fifo_head;
}

void stream_/*STREAM_NAME*/::/*MESSAGE_NAME*/_queueEvent/*MESSAGE_PARAMETERS*/
{
    if (/*MESSAGE_NAME*/_available() > /*MESSAGE_NAME_UPPER*/_ELEMENT_SIZE)
    {
        union stamp_t {
            unsigned long time;
            byte time_buffer[TIMESTAMP_DATASIZE];
        } stamp;
        stamp.time = millis();
        /*MESSAGE_NAME*/_fifo[(/*MESSAGE_NAME*/_fifo_tail + 0) % /*MESSAGE_NAME_UPPER*/_FIFO_SIZE] = stamp.time_buffer[3] & 0xFF;
        /*MESSAGE_NAME*/_fifo[(/*MESSAGE_NAME*/_fifo_tail + 1) % /*MESSAGE_NAME_UPPER*/_FIFO_SIZE] = stamp.time_buffer[2] & 0xFF;
        /*MESSAGE_NAME*/_fifo[(/*MESSAGE_NAME*/_fifo_tail + 2) % /*MESSAGE_NAME_UPPER*/_FIFO_SIZE] = stamp.time_buffer[1] & 0xFF;
        /*MESSAGE_NAME*/_fifo[(/*MESSAGE_NAME*/_fifo_tail + 3) % /*MESSAGE_NAME_UPPER*/_FIFO_SIZE] = stamp.time_buffer[0] & 0xFF;

/*QUEUE_IMPL*/

        /*MESSAGE_NAME*/_fifo_tail = (/*MESSAGE_NAME*/_fifo_tail + /*MESSAGE_NAME_UPPER*/_ELEMENT_SIZE) % /*MESSAGE_NAME_UPPER*/_FIFO_SIZE;

        checkTrigger(_instance);
    }
}

void stream_/*STREAM_NAME*/::/*MESSAGE_NAME*/_popEvent()
{
    if (!/*MESSAGE_NAME*/_isEmpty())
    {
        union stamp_t {
            unsigned long time;
            byte time_buffer[TIMESTAMP_DATASIZE];
        } stamp;
        stamp.time_buffer[3] = /*MESSAGE_NAME*/_fifo[/*MESSAGE_NAME*/_fifo_head];
        stamp.time_buffer[2] = /*MESSAGE_NAME*/_fifo[(/*MESSAGE_NAME*/_fifo_head + 1) % /*MESSAGE_NAME_UPPER*/_FIFO_SIZE];
        stamp.time_buffer[1] = /*MESSAGE_NAME*/_fifo[(/*MESSAGE_NAME*/_fifo_head + 2) % /*MESSAGE_NAME_UPPER*/_FIFO_SIZE];
        stamp.time_buffer[0] = /*MESSAGE_NAME*/_fifo[(/*MESSAGE_NAME*/_fifo_head + 3) % /*MESSAGE_NAME_UPPER*/_FIFO_SIZE];

/*POP_IMPL*/

        /*MESSAGE_NAME*/_fifo_head = (/*MESSAGE_NAME*/_fifo_head + /*MESSAGE_NAME_UPPER*/_ELEMENT_SIZE) % /*MESSAGE_NAME_UPPER*/_FIFO_SIZE;
    }
}
