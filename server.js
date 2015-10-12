var http = require("http"), 
express = require('express'), 
url = require('url'), 
fs = require("fs"), 
redis = require('redis');

var app = express(), 
client = redis.createClient();

const FILE_NAME = 'attributes.json';
const COUNT = 'count';

app.get('/', function(req, res) {
	res.send('This URl is not being handled.');
});


app.get('/get_count', function(req, response) {
	response.writeHead(200, {
		"Content-Type" : "text/plain"
	});
	console.log('get_count invoked.');
	var cnt = client.get(COUNT, function(err, val) {
		if (err !== null)
			console.log("error: " + err);
		else {
			console.log("The 'count' value: " + val);
			var c = {
				'count_value' : val
			};
			var cJSON = JSON.stringify(c);
			fs.writeFileSync('count_value.json', cJSON);
		}
	});
	response.write("OK");
	response.end();
});

function getMessage(request, response) {
	response.writeHead(200, {
		"Content-Type" : "text/plain"
	});
	console.log('user request');
	var queryObject = url.parse(request.url, true).query;
	response.write("Processing attributes: ");
	for ( var param in queryObject) {
		response.write(param + " ", "utf8");
	}

	/* update 'count' parameter in database redis */
	if (queryObject.count) {

		client.incrby(COUNT, queryObject.count, function(error, val) {
			if (error !== null)
				console.log("error: " + error);
			else {
				console.log("The value for 'count' key is " + val);
			}
		});

	}

	//console.log(queryObject);

	/* append to json file */
	if (fs.existsSync(FILE_NAME)) {
		var configFile = fs.readFileSync(FILE_NAME);
		var config = JSON.parse(configFile);
		//console.log(config);

		for ( var param in queryObject) {
			config[param] = queryObject[param];
		}

	} else {
		var config = queryObject;
	}

	var configJSON = JSON.stringify(config);
	fs.writeFileSync(FILE_NAME, configJSON);
	
	/* async write caused error: */
/*	fs.writeFile(FILE_NAME, configJSON, function(error) {
		if (error !== null) 
			console.log("error: " + error);
	});
*/
	response.end();
}

app.get("/track", getMessage);

var server = http.createServer(app);

server.listen(8888);