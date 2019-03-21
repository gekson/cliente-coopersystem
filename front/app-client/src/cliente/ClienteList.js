import React, { Component } from 'react';
import { getAllClientes, getUserCreatedClientes, getUserVotedClientes } from '../util/APIUtils';
import Cliente from './Cliente';
import { castVote } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import { Button, Icon, notification } from 'antd';
import { CLIENTE_LIST_SIZE } from '../constants';
import { withRouter } from 'react-router-dom';
import './ClienteList.css';

class ClienteList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            clientes: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            currentVotes: [],
            isLoading: false
        };
        this.loadClienteList = this.loadClienteList.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
    }

    loadClienteList(page = 0, size = CLIENTE_LIST_SIZE) {
        let promise;
        
        promise = getAllClientes(page, size);        

        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise            
        .then(response => {
            const clientes = this.state.clientes.slice();
            const currentVotes = this.state.currentVotes.slice();

            this.setState({
                clientes: clientes.concat(response.content),
                page: response.page,
                size: response.size,
                totalElements: response.totalElements,
                totalPages: response.totalPages,
                last: response.last,
                currentVotes: currentVotes.concat(Array(response.content.length).fill(null)),
                isLoading: false
            })
        }).catch(error => {
            this.setState({
                isLoading: false
            })
        });  
        
    }

    componentDidMount() {
        this.loadClienteList();
    }

    componentDidUpdate(nextProps) {
        if(this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.setState({
                clientes: [],
                page: 0,
                size: 10,
                totalElements: 0,
                totalPages: 0,
                last: true,
                currentVotes: [],
                isLoading: false
            });    
            this.loadClienteList();
        }
    }

    handleLoadMore() {
        this.loadClienteList(this.state.page + 1);
    }

    handleVoteChange(event, clienteIndex) {
        const currentVotes = this.state.currentVotes.slice();
        currentVotes[clienteIndex] = event.target.value;

        this.setState({
            currentVotes: currentVotes
        });
    }


    handleVoteSubmit(event, clienteIndex) {
        event.preventDefault();
        if(!this.props.isAuthenticated) {
            this.props.history.push("/login");
            notification.info({
                message: 'Clientes Coopersystem',
                description: "Please login to vote.",          
            });
            return;
        }

        const cliente = this.state.clientes[clienteIndex];
        const selectedChoice = this.state.currentVotes[clienteIndex];

        const voteData = {
            clienteId: cliente.id,
            choiceId: selectedChoice
        };

        castVote(voteData)
        .then(response => {
            const clientes = this.state.clientes.slice();
            clientes[clienteIndex] = response;
            this.setState({
                clientes: clientes
            });        
        }).catch(error => {
            if(error.status === 401) {
                this.props.handleLogout('/login', 'error', 'You have been logged out. Please login to vote');    
            } else {
                notification.error({
                    message: 'Clientes Coopersystem',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });                
            }
        });
    }

    render() {
        const clienteViews = [];
        this.state.clientes.forEach((cliente, clienteIndex) => {
            clienteViews.push(<Cliente 
                key={cliente.id} 
                cliente={cliente}
                currentVote={this.state.currentVotes[clienteIndex]} 
                handleVoteChange={(event) => this.handleVoteChange(event, clienteIndex)}
                handleVoteSubmit={(event) => this.handleVoteSubmit(event, clienteIndex)} />)            
        });

        return (
            <div className="clientes-container">
                {clienteViews}
                {
                    !this.state.isLoading && this.state.clientes.length === 0 ? (
                        <div className="no-clientes-found">
                            <span>Nenhum Cliente encontrado.</span>
                        </div>    
                    ): null
                }  
                {
                    !this.state.isLoading && !this.state.last ? (
                        <div className="load-more-clientes"> 
                            <Button type="dashed" onClick={this.handleLoadMore} disabled={this.state.isLoading}>
                                <Icon type="plus" /> Load more
                            </Button>
                        </div>): null
                }              
                {
                    this.state.isLoading ? 
                    <LoadingIndicator />: null                     
                }
            </div>
        );
    }
}

export default withRouter(ClienteList);