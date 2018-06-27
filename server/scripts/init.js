var mongoose = require('mongoose');

var async = require('async');

var userService = require('./init/init.users');

var Config = require('../config/config'); // get db config file
var config = new Config();

mongoose.connect(config.database);

var db = mongoose.connection;

db.on('error', console.error);
db.once('open', function() {

    userService.init(function(){
        console.log('init users');
    });

});



