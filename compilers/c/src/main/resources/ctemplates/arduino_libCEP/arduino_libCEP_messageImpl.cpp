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

void stream_/*STREAM_NAME*/::/*MESSAGE_NAME*/_queueEvent(/*MESSAGE_PARAMETERS*/)
{
}

void stream_/*STREAM_NAME*/::/*MESSAGE_NAME*/_popEvent()
{
}
