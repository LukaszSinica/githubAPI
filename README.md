# githubAPI
 
GithubAPI is a maven project made to call github API and listing all of the repositories, their branches and last commit by given username. 

In this project I used two paths: 

This path is to get all of the repositories from given username.
```
https://api.github.com/users/{username}/repos
```
The other path I used is the path below. I used this path to get branches and last commit SHA. 
```
https://api.github.com/repos/{owner}/{repo}/branches
```
To call the API from the project, type in url:
```
http://localhost:8080/{username}/repos
```

The response looks like this:
```
{
   "name":"string",
   "owner":{
      "login":"string"
   },
   "branches":[
      {
         "name":"string",
         "commit":{
            "sha":"string"
         }
      }
   ]
}
```

For exceptions, I made custom handler. To get the status and message I used response from Github API and on Errors I give the status and message to the handler.
This is what the response looks like when exception is called.
```
{
"status": "ResponseCode",
"message": "The message given by the Github API"
}
```
