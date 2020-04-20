var eventEmitter = require('events').EventEmitter;
var counter = 0;
var em = new eventEmitter();
setInterval(function() { em.emit('contador', counter++); }, 3000);
em.on('contador', function(data) {
 console.log('counter' + data );
});
