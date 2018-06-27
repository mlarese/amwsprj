var express     = require('express');
var app         = express();
var bodyParser  = require('body-parser');
var morgan      = require('morgan');
var mongoose    = require('mongoose');
var passport	= require('passport');
var jwt         = require('jwt-simple');
var Config      = require('./config/config.js'); // get db config file
var config      = new Config();
var port        = process.env.PORT || 3000;
var cors        = require('cors');


var settings = {
    config: config
};

require('./config/passport')(passport);

// demo Route (GET http://localhost:8080)
app.get('/', function(req, res) {
    res.send('Hello! The API is at http://localhost:' + port + '/api');
});

// get our request parameters
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

// log to console
app.use(morgan('dev'));

// Use the passport package in our application
app.use(passport.initialize());

// connect to database
mongoose.connect(config.database);

app.use(cors());

// connect the api routes under /api/*

var apiRoutes = require('./app/routes/api/v1')(settings);
app.use('/api/v1', apiRoutes);

// Start the server
app.listen(port);
console.log('There will be dragons: http://localhost:' + port);

// Thanks to http://blog.matoski.com/articles/jwt-express-node-mongoose/
