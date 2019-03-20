import React, { Component } from 'react';
import './Cliente.css';
import { Avatar, Icon } from 'antd';
import { Link } from 'react-router-dom';
import { getAvatarColor } from '../util/Colors';
import { formatDateTime } from '../util/Helpers';

import { Radio, Button } from 'antd';
const RadioGroup = Radio.Group;

class Cliente extends Component {
    calculatePercentage = (choice) => {
        if(this.props.cliente.totalVotes === 0) {
            return 0;
        }
        return (choice.voteCount*100)/(this.props.cliente.totalVotes);
    };

    isSelected = (choice) => {
        return this.props.cliente.selectedChoice === choice.id;
    }

    getWinningChoice = () => {
        return this.props.cliente.choices.reduce((prevChoice, currentChoice) => 
            currentChoice.voteCount > prevChoice.voteCount ? currentChoice : prevChoice, 
            {voteCount: -Infinity}
        );
    }

    getTimeRemaining = (cliente) => {
        const expirationTime = new Date(cliente.expirationDateTime).getTime();
        const currentTime = new Date().getTime();
    
        var difference_ms = expirationTime - currentTime;
        var seconds = Math.floor( (difference_ms/1000) % 60 );
        var minutes = Math.floor( (difference_ms/1000/60) % 60 );
        var hours = Math.floor( (difference_ms/(1000*60*60)) % 24 );
        var days = Math.floor( difference_ms/(1000*60*60*24) );
    
        let timeRemaining;
    
        if(days > 0) {
            timeRemaining = days + " days left";
        } else if (hours > 0) {
            timeRemaining = hours + " hours left";
        } else if (minutes > 0) {
            timeRemaining = minutes + " minutes left";
        } else if(seconds > 0) {
            timeRemaining = seconds + " seconds left";
        } else {
            timeRemaining = "less than a second left";
        }
        
        return timeRemaining;
    }

    render() {
        const clienteChoices = [];
        if(this.props.cliente.selectedChoice || this.props.cliente.expired) {
            const winningChoice = this.props.cliente.expired ? this.getWinningChoice() : null;

            this.props.cliente.choices.forEach(choice => {
                clienteChoices.push(<CompletedOrVotedClienteChoice 
                    key={choice.id} 
                    choice={choice}
                    isWinner={winningChoice && choice.id === winningChoice.id}
                    isSelected={this.isSelected(choice)}
                    percentVote={this.calculatePercentage(choice)} 
                />);
            });                
        } else {
             
        }        
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
                                {this.props.cliente.createdBy.name}
                            </span>
                            <span className="cliente-creator-username">
                                @{this.props.cliente.createdBy.login}
                            </span>
                            <span className="cliente-creation-date">
                                {formatDateTime(this.props.cliente.creationDateTime)}
                            </span>
                        </Link>
                    </div>
                    <div className="cliente-question">
                        {this.props.cliente.question}
                    </div>
                </div>
                <div className="cliente-choices">
                    <RadioGroup 
                        className="cliente-choice-radio-group" 
                        onChange={this.props.handleVoteChange} 
                        value={this.props.currentVote}>
                        { clienteChoices }
                    </RadioGroup>
                </div>
                <div className="cliente-footer">
                    { 
                        !(this.props.cliente.selectedChoice || this.props.cliente.expired) ?
                        (<Button className="vote-button" disabled={!this.props.currentVote} onClick={this.props.handleVoteSubmit}>Vote</Button>) : null 
                    }
                    <span className="total-votes">{this.props.cliente.totalVotes} votes</span>
                    <span className="separator">•</span>
                    <span className="time-left">
                        {
                            this.props.cliente.expired ? "Final results" :
                            this.getTimeRemaining(this.props.cliente)
                        }
                    </span>
                </div>
            </div>
        );
    }
}

function CompletedOrVotedClienteChoice(props) {
    return (
        <div className="cv-cliente-choice">
            <span className="cv-cliente-choice-details">
                <span className="cv-choice-percentage">
                    {Math.round(props.percentVote * 100) / 100}%
                </span>            
                <span className="cv-choice-text">
                    {props.choice.text}
                </span>
                {
                    props.isSelected ? (
                    <Icon
                        className="selected-choice-icon"
                        type="check-circle-o"
                    /> ): null
                }    
            </span>
            <span className={props.isWinner ? 'cv-choice-percent-chart winner': 'cv-choice-percent-chart'} 
                style={{width: props.percentVote + '%' }}>
            </span>
        </div>
    );
}


export default Cliente;