import axios from 'axios';
import http from '../http-common'

class EmployeeService{
    getEmployees(){
        // return axios.get(EMPLOYEE_API_BASE_URL);
        return http.get('/employees').catch(function (error) {
                        if (error.response) {
                          // Request made and server responded
                          console.log(error.response.data);
                          console.log(error.response.status);
                          console.log(error.response.headers);
                        } else if (error.request) {
                          // The request was made but no response was received
                          console.log(error.request);
                        } else {
                          // Something happened in setting up the request that triggered an Error
                          console.log('Error', error.message);
                        }
        });
    }
}

export default new EmployeeService();