import React from "react"
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { withRouter } from "../with-router";
import AuthenticationServices from "../PageServices/AuthenticationServices";

const required = value =>{
    if(!value){
        return(
            <div role="alert">
                This field is required!
            </div>
        );
    }
};



class LoginComponent extends React.Component
{
    constructor(props){
        super(props)

        this.state = {
            username :"",
            password :"",
            loading : false,
            message : ""
        };
        
        this.handleLogin = this.handleLogin.bind(this);
        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
    }

    onChangeUsername(e)
    {
        this.setState({
            username: e.target.value
        });
    }

    onChangePassword(e)
    {
        this.setState({
            password: e.target.value
        });
    }

    handleLogin(e)
    {
        e.preventDefault();

        this.setState({
            message: "",
            loading: true
        });

        this.form.validateAll();

        if(this.checkBtn.context._errors.lenght === 0){
            AuthenticationServices.loginOperation(this.state.username,this.state.password).then(
                () => {
                    this.props.router.navigate("/profile");
                    window.location.reload();
                },
                error =>{
                    const resMessage = 
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                        error.message ||
                        error.toString();

                    this.setState({
                        loading : false,
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
                <Form onSubmit={this.handleLogin}
                ref ={c=> {
                    this.form = c;
                }}
                >
                    <div>
                    <label htmlFor="username">Username</label>
                    <Input
                    type = "text" name="username"
                    value={this.state.username}
                    onChange={this.onChangeUsername}
                    validations={[required]}
                    />
                    </div>
                    <div>
                    <label htmlFor="password">Password</label>
                    <Input
                    type = "password" name="password"
                    value={this.state.password}
                    onChange={this.onChangePassword}
                    validations={[required]}
                    />
                    </div>
                    <div>
                        <button
                        type="submit"
                        disabled={this.state.loading}
                        >{this.state.loading && (
                            <span></span>
                        )}
                        <span>Login!</span>
                        </button>
                    </div>
                    {this.state.message &&(
                        <div>
                            <div role="alert">
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
export default withRouter(LoginComponent);