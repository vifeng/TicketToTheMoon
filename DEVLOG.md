# Notes de développement

## Info
Suivre le projet 
https://github.com/users/vifeng/projects/2/views/1
Suivre le git et ses issues
https://github.com/vifeng/TicketToTheMoon

## WIP 8-env-vuejs3-setup
### CORS issue: solutions à tester
1. Axios error - not that -> detail errors and config ok
2. jar n'inclue pas le front
3. installer Ngnix
4. first request temporary access https://cors-anywhere.herokuapp.com/corsdemo then try to append https://cors-anywhere.herokuapp.com/  to your url -> should work for all situation but big latency.
   or go to https://robwu.nl/cors-anywhere.html and enter your url.
   1. ECONNREFUSED means there is no server process listening at the specified port. What hostname and port are you using? Can you connect directly (without the proxy)?
    P.S. Unrelated to ECONNREFUSED, but you should also set changeOrigin in the options passed to proxy.web:
    proxy.web(req, res, {
        target: 'http://' + hostname + ':' + port,
        changeOrigin: true
    });
5. try to build your own proxy with node https://medium.com/@dtkatz/3-ways-to-fix-the-cors-error-and-how-access-control-allow-origin-works-d97d55946d9 
6. https://github.com/expressjs/cors 
7. Installer un proxy dans `vite.config.js`
  Grâce à ce proxy, le navigateur interagira uniquement avec le serveur `node`, typiquement sur le port `3000`,
  et cachera le serveur `Spring` sur le port `8080`.
~~~json
,
  server: {
    proxy: {
      "/api": 'http://localhost:8080'     
    }
  }
~~~
=> FAILED

2. install and test
~~~json
  task install(type: Copy) {
    description('install the packed js and css files in the proper folder for production.')
    dependsOn build
    from 'dist'
    into '../back-end/src/main/resources/static'
  }
~~~
