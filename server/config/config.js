var uuid = require('uuid');

var settings = {
    'local' : {
        'secret': 'secret',
        'database': 'mongodb://localhost:27017/data-feed-server',
        'core': {
            'host': 'http://localhost:8080'
        }
    },
    'development' : {
        'secret': uuid.v4(),
        'database': 'mongodb://datafeed:datafeed@147.75.205.77:27217/oir',
        'core': {
            'host': 'http://147.75.205.77:32786'
        }
    },
    'production' : {
        'secret': uuid.v4(),
        'database': 'mongodb://localhost:27017/data-feed-server',
        'core': {
            'host': 'http://localhost:8080'
        }
    }
};

module.exports = function(){

    switch(process.env.NODE_ENV){
        case 'local' :
            return settings['local'];
        case 'development':
            return settings['development'];
        case 'production':
            return settings['production'];
        default:
            return settings['local'];
    }
};