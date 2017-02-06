//IMPORTANT: Redis must be installed on your computer first.
//For Windows: https://github.com/MSOpenTech/redis/releases
//For Linux (Ubuntu-based): sudo apt-get -y install redis-server

//Redis back-end
const RedisServer = require('redis-server');
const redisServerInstance = new RedisServer(6379);
redisServerInstance.open((error) => {
  if (error) {
    console.log("Cannot start the Redis backend");
    throw new Error(error);
  }
});

const mosca = require('mosca')

const ascoltatore = {
  type: 'redis',
  redis: require('redis'),
  db: 12,
  port: 6379,
  return_buffers: true, // to handle binary payloads
  host: "localhost"
};

const moscaSettings = {
  port: /*$PORT$*/,
  backend: ascoltatore,
  persistence: {
    factory: mosca.persistence.Redis
  }
};

const server = new mosca.Server(moscaSettings);
server.on('ready', setup);

server.on('clientConnected', (client) => {
    console.log('client connected', client.id);
});

// fired when a message is received
server.on('published', (packet, client) => {
  console.log('Published', packet.payload);
});

// fired when the mqtt server is ready
function setup() {
  console.log('Mosca server is up and running')
}