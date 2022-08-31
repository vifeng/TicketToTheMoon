import axios from 'axios'

export default axios.create({
    baseURL: "http://localhost:8080/api",
    // baseURL: "https://cors-anywhere.herokuapp.com/http://localhost:8080/api",
    headers: {
        'Content-Type': 'application/json'
    }
})
