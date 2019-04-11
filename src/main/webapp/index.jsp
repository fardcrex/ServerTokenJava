<html>
<body>
    <h2>API RESTful Web Application!</h2>
    <p><a href="v1/opciones">Jair Conislla</a>
    <p>Visit <a href="https://github.com/fardcrex/JavaJuniorAppBackend">JavaJunior</a>
       welcome developers!
        <a href="./apiprueba.html">html</a>


        <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
        <script>
            let token ="",url="http://localhost:8090/JavaJuniorAppBackend/v1/usuarios"

            fetch("http://localhost:8090/JavaJuniorAppBackend/v1/opciones")
                .then(function(response) {
                    return response.json();
                }).catch(error => console.error('error:', error))
                .then(response => console.log('success:', response));

            fetch("http://localhost:8090/JavaJuniorAppBackend/v1/autentificacion?correo=root&password=root321")
                .then(function(response) {
                    return response.json();
                }).catch(error => console.error('error:', error))
                .then(response => console.log('success:', response));

             url = 'http://localhost:8090/JavaJuniorAppBackend/v1/usuarios';


            var data =
                {   correo: "jjjSSSSSSSjjj",
                    imagen: "imagenDefault.jpg",
                    nombre: "administrador",
                    password: "xxxxxx",
                    role: {
                        id: 1,
                       name:"sasxa"
                    }
                };

            fetch(url, {
                method: 'POST', // or 'PUT'
                body: JSON.stringify(data), // data can be `string` or {object}!
                headers:{
                    'Content-Type': 'application/json',
                    'Authorization':'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbiIsImV4cCI6MTU1NDk1MzI1NywidXNlcl9pZCAiOjUxLCJyb2xlX2lkIjoxfQ.QtAMTKAZERgGHhH8vQ4h7zRAKxR9OD4lteXLWBrVB6TXkkzJrAsKR8HxviPF4yuLBEb9Py1e79bLZeYp0to9IA'
                }
            }).then(res => res.json())
                .catch(error => console.error('Error:', error))
                .then(response => console.log('Success:', response));

        </script>





</body>
</html>
