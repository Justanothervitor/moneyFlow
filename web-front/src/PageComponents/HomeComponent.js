import React from "react";
import UserServices from "../PageServices/UserServices"

export default class HomeComponent extends React.Component{
    constructor(props){
        super(props);

        this.state = {
            content : ""
        };
    }

    componentDidMount(){
        UserServices.getPublicContent().then(
            response => {
                this.setState({
                    content : response.data
                });
            },

            error => {
                this.setState({
                    content:
                    (error.response && error.response.data)||
                    error.message ||
                    error.toString()
                });
            }
        );
    }

    render(){
        return(
            <div>
                <h3>{this.state.content}</h3>
            </div>
        );
    }
}