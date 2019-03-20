import React, { Component } from 'react';
import { createCliente } from '../util/APIUtils';
import { MAX_CHOICES, POLL_QUESTION_MAX_LENGTH, POLL_CHOICE_MAX_LENGTH } from '../constants';
import './NewCliente.css';  
import { Form, Input, Button, Icon, Select, Col, notification } from 'antd';
const Option = Select.Option;
const FormItem = Form.Item;
const { TextArea } = Input

class NewCliente extends Component {
    constructor(props) {
        super(props);
        this.state = {
            question: {
                text: ''
            },
            choices: [{
                text: ''
            }, {
                text: ''
            }],
            clienteLength: {
                days: 1,
                hours: 0
            }
        };
        this.addChoice = this.addChoice.bind(this);
        this.removeChoice = this.removeChoice.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleQuestionChange = this.handleQuestionChange.bind(this);
        this.handleChoiceChange = this.handleChoiceChange.bind(this);
        this.handleClienteDaysChange = this.handleClienteDaysChange.bind(this);
        this.handleClienteHoursChange = this.handleClienteHoursChange.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    addChoice(event) {
        const choices = this.state.choices.slice();        
        this.setState({
            choices: choices.concat([{
                text: ''
            }])
        });
    }

    removeChoice(choiceNumber) {
        const choices = this.state.choices.slice();
        this.setState({
            choices: [...choices.slice(0, choiceNumber), ...choices.slice(choiceNumber+1)]
        });
    }

    handleSubmit(event) {
        event.preventDefault();
        const clienteData = {
            question: this.state.question.text,
            choices: this.state.choices.map(choice => {
                return {text: choice.text} 
            }),
            clienteLength: this.state.clienteLength
        };

        createCliente(clienteData)
        .then(response => {
            this.props.history.push("/");
        }).catch(error => {
            if(error.status === 401) {
                this.props.handleLogout('/login', 'error', 'You have been logged out. Please login create cliente.');    
            } else {
                notification.error({
                    message: 'Clientes Coopersystem',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });              
            }
        });
    }

    validateQuestion = (questionText) => {
        if(questionText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Please enter your question!'
            }
        } else if (questionText.length > POLL_QUESTION_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Question is too long (Maximum ${POLL_QUESTION_MAX_LENGTH} characters allowed)`
            }    
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }

    handleQuestionChange(event) {
        const value = event.target.value;
        this.setState({
            question: {
                text: value,
                ...this.validateQuestion(value)
            }
        });
    }

    validateChoice = (choiceText) => {
        if(choiceText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Please enter a choice!'
            }
        } else if (choiceText.length > POLL_CHOICE_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Choice is too long (Maximum ${POLL_CHOICE_MAX_LENGTH} characters allowed)`
            }    
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }

    handleChoiceChange(event, index) {
        const choices = this.state.choices.slice();
        const value = event.target.value;

        choices[index] = {
            text: value,
            ...this.validateChoice(value)
        }

        this.setState({
            choices: choices
        });
    }


    handleClienteDaysChange(value) {
        const clienteLength = Object.assign(this.state.clienteLength, {days: value});
        this.setState({
            clienteLength: clienteLength
        });
    }

    handleClienteHoursChange(value) {
        const clienteLength = Object.assign(this.state.clienteLength, {hours: value});
        this.setState({
            clienteLength: clienteLength
        });
    }

    isFormInvalid() {
        if(this.state.question.validateStatus !== 'success') {
            return true;
        }
    
        for(let i = 0; i < this.state.choices.length; i++) {
            const choice = this.state.choices[i];            
            if(choice.validateStatus !== 'success') {
                return true;
            }
        }
    }

    render() {
        const choiceViews = [];
        this.state.choices.forEach((choice, index) => {
            choiceViews.push(<ClienteChoice key={index} choice={choice} choiceNumber={index} removeChoice={this.removeChoice} handleChoiceChange={this.handleChoiceChange}/>);
        });

        return (
            <div className="new-cliente-container">
                <h1 className="page-title">Create Cliente</h1>
                <div className="new-cliente-content">
                    <Form onSubmit={this.handleSubmit} className="create-cliente-form">
                        <FormItem validateStatus={this.state.question.validateStatus}
                            help={this.state.question.errorMsg} className="cliente-form-row">
                        <TextArea 
                            placeholder="Enter your question"
                            style = {{ fontSize: '16px' }} 
                            autosize={{ minRows: 3, maxRows: 6 }} 
                            name = "question"
                            value = {this.state.question.text}
                            onChange = {this.handleQuestionChange} />
                        </FormItem>
                        {choiceViews}
                        <FormItem className="cliente-form-row">
                            <Button type="dashed" onClick={this.addChoice} disabled={this.state.choices.length === MAX_CHOICES}>
                                <Icon type="plus" /> Add a choice
                            </Button>
                        </FormItem>
                        <FormItem className="cliente-form-row">
                            <Col xs={24} sm={4}>
                                Cliente length: 
                            </Col>
                            <Col xs={24} sm={20}>    
                                <span style = {{ marginRight: '18px' }}>
                                    <Select 
                                        name="days"
                                        defaultValue="1" 
                                        onChange={this.handleClienteDaysChange}
                                        value={this.state.clienteLength.days}
                                        style={{ width: 60 }} >
                                        {
                                            Array.from(Array(8).keys()).map(i => 
                                                <Option key={i}>{i}</Option>                                        
                                            )
                                        }
                                    </Select> &nbsp;Days
                                </span>
                                <span>
                                    <Select 
                                        name="hours"
                                        defaultValue="0" 
                                        onChange={this.handleClienteHoursChange}
                                        value={this.state.clienteLength.hours}
                                        style={{ width: 60 }} >
                                        {
                                            Array.from(Array(24).keys()).map(i => 
                                                <Option key={i}>{i}</Option>                                        
                                            )
                                        }
                                    </Select> &nbsp;Hours
                                </span>
                            </Col>
                        </FormItem>
                        <FormItem className="cliente-form-row">
                            <Button type="primary" 
                                htmlType="submit" 
                                size="large" 
                                disabled={this.isFormInvalid()}
                                className="create-cliente-form-button">Create Cliente</Button>
                        </FormItem>
                    </Form>
                </div>    
            </div>
        );
    }
}

function ClienteChoice(props) {
    return (
        <FormItem validateStatus={props.choice.validateStatus}
        help={props.choice.errorMsg} className="cliente-form-row">
            <Input 
                placeholder = {'Choice ' + (props.choiceNumber + 1)}
                size="large"
                value={props.choice.text} 
                className={ props.choiceNumber > 1 ? "optional-choice": null}
                onChange={(event) => props.handleChoiceChange(event, props.choiceNumber)} />

            {
                props.choiceNumber > 1 ? (
                <Icon
                    className="dynamic-delete-button"
                    type="close"
                    disabled={props.choiceNumber <= 1}
                    onClick={() => props.removeChoice(props.choiceNumber)}
                /> ): null
            }    
        </FormItem>
    );
}


export default NewCliente;