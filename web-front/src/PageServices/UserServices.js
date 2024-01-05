import axios from 'axios'
import authHeader from './authHeader'

const API_USER = 'http://localhost:8080/api/test/';

class UserServices
{
    getPublicContent()
    {
        return axios.get(API_USER+'all');
    }

    getUserBoard()
    {
        return axios.get(API_USER+'user',{headers: authHeader()});
    }

    getEnterpressBoard()
    {
        return axios.get(API_USER+'enterpress',{headers: authHeader()});
    }

    getAdminBoard()
    {
        return axios.get(API_USER+'admin',{headers: authHeader()});
    }
}
//eslint-disable-next-line
export default new UserServices();