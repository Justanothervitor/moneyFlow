import React from "react";
import { Routes, Route, Link } from "react-router-dom";
import AuthenticationServices from "./PageServices/AuthenticationServices";
import LoginComponent from "./PageComponents/LoginComponent";
import RegisterComponent from "./PageComponents/RegisterComponent";
import HomeComponent from "./PageComponents/HomeComponent";
import ProfileComponent from "./PageComponents/ProfileComponent";

class App extends React.Component{
  constructor(props){
    super(props);
    
    this.state ={
      currentUser : undefined
    };

    this.logOut = this.logOut.bind(this);
  }
  componentDidMount()
  {
    const user = AuthenticationServices.getCurrentUser();

    if(user)
    {
      this.setState({
        currentUser:user
      });
    }
  }

  logOut(){
    AuthenticationServices.logoutOperation();
    this.setState({
      currentUser : undefined
    });
  }

  render()
  {
    const {currentUser} = this.state;

    return(
      <div>
        <nav>
        <Link to={"/"}>MoneyFlow</Link>
        <div>
          <li>
            <Link to={"/home"}>Home</Link>
          </li>
        {currentUser && (
          <li>
            <Link to={"/user"}>User</Link>
          </li>
        )}
        </div>
        {currentUser ? (
        <div>
          <li>
            <Link to={"/profile"}>{currentUser.username}</Link>
          </li>
          <li>
            <a href="/login" onClick={this.logOut}>Logout</a>
          </li>
        </div>
      ):(
        <div>
        <li>
        <Link to={"/login"}>Login</Link>
      </li>
      <li>
        <Link to={"/register"}>Signup</Link>
      </li>
        </div>
      )}
      </nav>
      <div>
        <Routes>
          <Route path="/" element={<HomeComponent />}/>
          <Route path="/home" element={<HomeComponent />}/>
          <Route path="/login" element={<LoginComponent />}/>
          <Route path="/register" element={<RegisterComponent />}/>
          <Route path="/profile" element={<ProfileComponent />}/>
        </Routes>
      </div>
      </div>
    );
  }
}

export default App;
