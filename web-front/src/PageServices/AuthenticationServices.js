import axios from "axios";

const API_AUTH = "http://localhost:8080/api/auth/";

class AuthenticationServices{
 
   loginOperation(username,password){
    axios
    .post("http://localhost:8080/api/auth/login",{loginObject:{username:this.username,password:this.password}}).then(response => {
      if(response.data.token){
         localStorage.setItem("user",JSON.stringify(response.data));
      }
      return response.data;
   });
   }

   logoutOperation()
   {
      localStorage.removeItem("user");
   }

   signupOperation(username,email,password)
   {
    return axios.post(API_AUTH+"signup",{username,email,password});
   }

   getCurrentUser()
   {
      return JSON.parse(localStorage.getItem('user'));
   }

}
//eslint-disable-next-line
export default new AuthenticationServices();