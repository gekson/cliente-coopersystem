import React, { Component } from 'react';
import './Cliente.css';
import { Avatar, Icon } from 'antd';
import { Link } from 'react-router-dom';
import { getAvatarColor } from '../util/Colors';
import { formatDateTime } from '../util/Helpers';

import { Radio, Button } from 'antd';
const RadioGroup = Radio.Group;

class Cliente extends Component {

    render() {
        
        return (
            <div className="cliente-content">
                <div className="cliente-header">
                    <div className="cliente-creator-info">
                        <Link className="creator-link" to={`/users/}`}>
                            <Avatar className="cliente-creator-avatar" 
                                style={{ backgroundColor: getAvatarColor(this.props.cliente.createdBy.nome)}} >
                                {this.props.cliente.createdBy.nome[0].toUpperCase()}
                            </Avatar>
                            <span className="cliente-creator-name">
                                {this.props.cliente.createdBy.nome}
                            </span>
                            <span className="cliente-creator-username">
                                @{this.props.cliente.createdBy.login}
                            </span>
                            <span className="cliente-creation-date">
                                {formatDateTime(this.props.cliente.dataCriacao)}
                            </span>
                        </Link>
                    </div>
                    <div className="cliente-nome">
                        Nome: {this.props.cliente.nome}
                    </div>
                    <div className="cliente-nome">
                        CPF: {this.props.cliente.cpf}
                    </div>
                </div>
                
                <div className="cliente-footer">
                    
                    <span className="separator">•</span>
                    <span className="time-left">
                        
                    </span>
                </div>
            </div>
        );
    }
}


export default Cliente;