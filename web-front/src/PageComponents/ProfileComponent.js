import React from "react";
import {Navigate} from "react-router-dom";
import AuthenticationServices from "../PageServices/AuthenticationServices";

export default class ProfileComponent extends React.Component{
    constructor(props){
        super(props);

        this.state ={
            redirect : null,
            userReady : false,
            currentUser : {username: ""}
        };
    }

    componentDidMount()
    {
        const currentUser = AuthenticationServices.getCurrentUser();

        if(!currentUser)this.setState({redirect:"/home"});
        this.setState({currentUser:currentUser,userReady: true})
    }

    render(){
        if(this.state.redirect){
            return<Navigate to={this.state.redirect}/>
        }
        const {currentUser} = this.state;

        return(
            <div>
                {(this.state.userReady)?
                <div>
                    <h3>
                        <strong>{currentUser.username}</strong>Profile
                    </h3>
                    <p>
                        <strong>Token:</strong>{" "}
                        {currentUser.acessToken.substring(0,20)}...{" "}
                        {currentUser.acessToken.substr(currentUser.acessToken.lenght - 20)}
                    </p>
                    <p>
                        <strong>Id:</strong>{" "}
                        {currentUser.id}
                    </p>
                    <p>
                        <strong>Email:</strong>{""}
                        {currentUser.email}
                    </p>
                    <p>
                        <strong>Authorities:</strong>
                        <ul>
                            {currentUser.roles &&
                            currentUser.roles.map((role,index) => <li key={index}>{role}</li>)}
                        </ul>
                    </p>
                </div>:null}
            </div>
        );
    }
}