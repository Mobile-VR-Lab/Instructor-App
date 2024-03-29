const express = require('express');
const ip = require('ip'); // To get ip of localhost easily

const app = express();

// Option to access the data sent in the body of a post request
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

// Print out relevant data about request
app.use((req, res, next) => {
    console.log('\nTime: ', new Date(Date.now()).toString());
    console.log('Request type: ', req.method);
    console.log('Request path:', req.path);
    console.log('Request body:', req.body);
    next();
});


app.get('/headsets', (req, res) => {
  res.send('Successful response.');
});

app.get('/headsets/:id/', (req, res) => {
    console.log("id=", req.params.id);
    res.send('Successful response.');
});

app.get('/commands', (req, res) => {
    res.send('Successful response.');
});

app.post('/command/:type/', (req, res) => {
    console.log("type=", req.params.type);
    let type = parseInt(req.params.type);
    if (type == 0) {
        console.log("Query command");
    }
    if (type == 1) {
        console.log("ChangeScene command");
        console.log("scene=", req.body.scene);
    }
    if (type == 2) {
        console.log("FocusOnObject command");
        console.log("object=", req.body.object);
    }
    res.send('Successful response.');
});


// Start app at: http://localhost:3000/
app.listen(3000, () => console.log('test server is listening on port 3000. \nhttp://' + ip.address() + ':3000/'));
