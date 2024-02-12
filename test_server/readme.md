# Test Server
A test server created with NodeJS and Express to facilitate receiving & responding to queries that would be sent to the server. 
Prints out the time, type, path, & body of the request. 

## Setup
- Run `node index.js`

### Testing with cURL:
- `curl -X GET http://localhost:3000/headsets/`
- `curl -X GET http://localhost:3000/headsets/5/`
- `curl -X GET http://localhost:3000/commands/`
- `curl -X POST http://localhost:3000/command/0`
- `curl -X POST http://localhost:3000/command/1/ -d "scene=10"`
- `curl -X POST http://10.21.149.117:3000/command/1/ -d {"scene":3}`
- `curl -X POST http://localhost:3000/command/2/ -d "object=12345"`
