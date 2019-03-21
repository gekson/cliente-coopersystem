import React, { Component } from 'react';
import { createCliente } from '../util/APIUtils';
import { CLIENTE_NOME_MAX_LENGTH } from '../constants';
import './NewCliente.css';  
import { Form, Input, Button, Icon, Select, Col, notification } from 'antd';
const Option = Select.Option;
const FormItem = Form.Item;
const { TextArea } = Input

class NewCliente extends Component {
    constructor(props) {
        super(props);
        this.state = {
            nome: {
                text: ''
            },
            cpf: {
                text: ''
            },
            cep: {
                text: ''
            },
            logradouro: {
                text: ''
            },
            bairro: {
                text: ''
            },
            cidade: {
                text: ''
            },
            uf: {
                text: ''
            },
            complemento: {
                text: ''
            }
        };
        
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleNomeChange = this.handleNomeChange.bind(this);        
        this.isFormInvalid = this.isFormInvalid.bind(this);
        this.handleCpfChange = this.handleCpfChange.bind(this);
        this.handleBairroChange = this.handleBairroChange.bind(this);
        this.handleCepChange = this.handleCepChange.bind(this);
        this.handleLogradouroChange = this.handleLogradouroChange.bind(this);
        this.handleCidadeChange = this.handleCidadeChange.bind(this);
        this.handleUfChange = this.handleUfChange.bind(this);
    }
    

    handleSubmit(event) {
        event.preventDefault();
        const clienteData = {
            nome: this.state.nome.text,
            cpf: this.state.cpf.text,
            cep: this.state.cep.text,
            logradouro: this.state.logradouro.text,
            bairro: this.state.bairro.text,
            cidade: this.state.cidade.text,
            uf: this.state.uf.text
        };

        createCliente(clienteData)
        .then(response => {
            this.props.history.push("/");
        }).catch(error => {
            if(error.status === 401) {
                this.props.handleLogout('/login', 'error', 'Você foi desconectado. Por favor faça o login para criar um cliente.');    
            } else {
                notification.error({
                    message: 'Clientes Coopersystem',
                    description: error.message || 'Desculpe! Algo deu errado. Por favor, tente novamente!'
                });              
            }
        });
    }

    validateNome = (nomeText) => {
        if(nomeText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Insira seu nome!'
            }
        } else if (nomeText.length > CLIENTE_NOME_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `O nome é muito grande (Maximo de  ${CLIENTE_NOME_MAX_LENGTH} caracters)`
            }    
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }

    handleNomeChange(event) {
        const value = event.target.value;
        this.setState({
            nome: {
                text: value,
                ...this.validateNome(value)
            }
        });
    }

    validateCpf = (cpfText) => {
        if(cpfText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Insira seu nome!'
            }
        } else if (cpfText.length > CLIENTE_NOME_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `O nome é muito grande (Maximo de  ${CLIENTE_NOME_MAX_LENGTH} caracters)`
            }    
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }

    handleCpfChange(event) {
        const value = event.target.value;
        this.setState({
            cpf: {
                text: value,
                ...this.validateCpf(value)
            }
        });
    }

    handleCepChange(event) {
        const value = event.target.value;
        this.setState({
            cep: {
                text: value,
                ...this.validateCpf(value)
            }
        });
    }

    handleLogradouroChange(event) {
        const value = event.target.value;
        this.setState({
            logradouro: {
                text: value,
                ...this.validateCpf(value)
            }
        });
    }

    handleBairroChange(event) {
        const value = event.target.value;
        this.setState({
            bairro: {
                text: value,
                ...this.validateCpf(value)
            }
        });
    }

    handleCidadeChange(event) {
        const value = event.target.value;
        this.setState({
            cidade: {
                text: value,
                ...this.validateCpf(value)
            }
        });
    }

    handleUfChange(event) {
        const value = event.target.value;
        this.setState({
            uf: {
                text: value,
                ...this.validateCpf(value)
            }
        });
    }

    isFormInvalid() {
        if(this.state.nome.validateStatus !== 'success') {
            return true;
        }
    
        // if(this.state.cpf.validateStatus !== 'success') {
        //     return true;
        // }
    }

    render() {        

        return (
            <div className="new-cliente-container">
                <h1 className="page-title">Novo Cliente</h1>
                <div className="new-cliente-content">
                    <Form onSubmit={this.handleSubmit} className="create-cliente-form">
                        <FormItem validateStatus={this.state.nome.validateStatus}
                            help={this.state.nome.errorMsg} className="cliente-form-row">
                        <input 
                            placeholder="Entre com seu nome"
                            style = {{ fontSize: '16px' }}                              
                            name = "nome"
                            value = {this.state.nome.text}
                            onChange = {this.handleNomeChange} />
                        </FormItem>
                        <FormItem  className="cliente-form-row">
                        <input 
                            placeholder="CPF"
                            style = {{ fontSize: '16px' }}                              
                            name = "cpf"
                            value = {this.state.cpf.text}
                            onChange = {this.handleCpfChange} />
                        </FormItem>
                        <FormItem  className="cliente-form-row">
                        <input 
                            placeholder="CEP"
                            style = {{ fontSize: '16px' }}                              
                            name = "cep"
                            value = {this.state.cep.text}
                            onChange = {this.handleCepChange} />
                        </FormItem>
                        <FormItem  className="cliente-form-row">
                        <input 
                            placeholder="Logradouro"
                            style = {{ fontSize: '16px' }}                              
                            name = "logradouro"
                            value = {this.state.logradouro.text}
                            onChange = {this.handleLogradouroChange} />
                        </FormItem>
                        <FormItem  className="cliente-form-row">
                        <input 
                            placeholder="Bairro"
                            style = {{ fontSize: '16px' }}                              
                            name = "bairro"
                            value = {this.state.bairro.text}
                            onChange = {this.handleBairroChange} />
                        </FormItem>
                        <FormItem  className="cliente-form-row">
                        <input 
                            placeholder="Cidade"
                            style = {{ fontSize: '16px' }}                              
                            name = "cidade"
                            value = {this.state.cidade.text}
                            onChange = {this.handleCidadeChange} />
                        </FormItem>
                        <FormItem  className="cliente-form-row">
                        <input 
                            placeholder="UF"
                            style = {{ fontSize: '16px' }}                              
                            name = "uf"
                            value = {this.state.uf.text}
                            onChange = {this.handleUfChange} />
                        </FormItem>
                        
                        <FormItem className="cliente-form-row">
                            <Button type="primary" 
                                htmlType="submit" 
                                size="large" 
                                disabled={this.isFormInvalid()}
                                className="create-cliente-form-button">Adicionar Cliente</Button>
                        </FormItem>
                    </Form>
                </div>    
            </div>
        );
    }
}


export default NewCliente;