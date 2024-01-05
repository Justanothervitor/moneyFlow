import React from "react"
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import {isEmail} from "validator"
import AuthenticationServices from "../PageServices/AuthenticationServices";

const required = value =>{
    if(!value){
        return(
            <div>
                This field is required!
            </div>
        );
    }
};

const vemail = value =>{
    if(!isEmail(value)){
        return(
            <div>
                This is not a valid email!!!
            </div>
        );
    }
};

const vusername = value =>{
    if(value.lenght<3 || value.lenght>20){
        return(
            <div>
                The Username must be between 3 and 20 characters.
            </div>
        );
    }
};

const vpassword = value =>{
    if(value.lenght<6 || value.lenght>40){
        return(
            <div>
                The password must be between 6 and 40 characters.
            </div>
        );
    }
};

class RegisterComponent extends React.Component
{
    constructor(props){
        super(props);

        this.state = {
            username :"",
            email :"",
            password :"",
            successful : false,
            message : ""
        };
        
        this.handleRegister = this.handleRegister.bind(this);
        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
    }

    onChangeUsername(e)
    {
        this.setState({
            username: e.target.value
        });
    }

    onChangeEmail(e)
    {
        this.setState({
           email: e.target.value
        });
    }

    onChangePassword(e)
    {
        this.setState({
            password: e.target.value
        });
    }

    handleRegister(e)
    {
        e.preventDefault();

        this.setState({
            message: "",
            successful: false
        });

        this.form.validateAll();

        if(this.checkBtn.context._errors.lenght === 0){
            AuthenticationServices.signupOperation(this.state.username,this.state.email,this.state.password).then(
                    response =>{
                        this.setState({
                            message: response.data.message,
                            successful: true
                        });
                    },
                error =>{
                    const resMessage = 
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                        error.message ||
                        error.toString();

                    this.setState({
                        successful : false,
                        message : resMessage
                    });
                }
            );
        }else{
            this.setState({
                loading : false
            });
        }
    }

    render(){
        return(
            <div>
                <Form onSubmit ={this.handleRegister}
                ref ={c=>{
                    this.form = c;
                }}
                >
                    {!this.state.successful && (
                <div>        
                    <div>
                    <label htmlFor="username">Username</label>
                    <Input
                    type = "text" name="username"
                    value={this.state.username}
                    onChange={this.onChangeUsername}
                    validations={[required,vusername]}
                    />
                    </div>
                    <div>
                    <label htmlFor="email">Email</label>
                    <Input
                    type = "text" name="email"
                    value={this.state.email}
                    onChange={this.onChangeEmail}
                    validations={[required,vemail]}
                    />
                    </div>
                    <div>
                    <label htmlFor="password">Password</label>
                    <Input
                    type = "password" name="password"
                    value={this.state.password}
                    onChange={this.onChangePassword}
                    validations={[required,vpassword]}
                    />
                    </div>
                    <div>
                        <button>Signup</button>
                    </div>
                    </div>
                    )}
                    {this.state.message &&(
                        <div>
                            <div
                                className={this.state.successful
                            }
                                role = "alert"
                            >
                                {this.state.message}
                            </div>
                        </div>
                    )}
                    <CheckButton
                    style={{display : "none"}}
                    ref={c => {
                        this.checkBtn = c;
                    }}
                    />
                </Form>
            </div>    
        );
    }
}
export default RegisterComponent;