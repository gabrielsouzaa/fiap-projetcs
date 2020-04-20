var express = require('express');
var app = express();

// respond with "hello world" when a GET request is made to the homepage
app.get('/', function(req, res) {
  res.send('RAIZ');
});

app.get('/fiap', function(req, res) {
    res.send('FIAP');
  });

app.listen(3000, function() {
    console.log("Exemplo API - Escutando porta 3000")
});