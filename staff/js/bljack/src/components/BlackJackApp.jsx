import React from 'react';
import Dealer from './Dealer.jsx';
import GameTimer from './GameTimer.jsx';
import Players from './Players.jsx';
import Controls from './Controls.jsx';
import $ from 'jquery';

import './BlackJackApp.less';

export default class BlackJackApp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            "game": this.props.game
        };
    }
    onTimeOff () {
      console.log('time is off!');
      let newGame = this.state.game;
      newGame.controls.balance.value += 1;
      newGame.timer.timeLimit += 2;
      this.setState((prevState) => {
        return {
          game: newGame
        }
      });
    }
    render() {
        let game = this.state.game;
        return (
            <div className="game">
                <Dealer initValues={this.props.initValues.dealer} stateValues={game.dealer}/>
                <GameTimer initValues={this.props.initValues.timer}  stateValues={game.timer} onTimeOff={this.onTimeOff.bind(this)}/>
                <Players initValues={this.props.initValues.players} stateValues={game.players}/>
                <Controls initValues={this.props.initValues.controls} stateValues={game.controls}/>
            </div>
        );
    }
}

let game1 = {
    "dealer": {
        "hand": {
            "score": 10,
            "cards": ["img/card/ace.gif", "img/card/back.gif"]
        }
    },
    "timer": {
        "timeLimit": 25
    },
    "result": "",
    "players": [
        {
            "id": 0,
            "name": "qqq175",
            "img": "img/p_one.jpg",
            "hands": [
                {
                    "score": 10,
                    "cards": [
                        "img/card/ace.gif", "img/card/ace.gif"
                    ],
                    "bet": 14,
                    "isActive": false
                }
            ],
            "isActive": false
        }, {
            "id": 4,
            "name": "Player Two",
            "img": "img/pl_three.jpg",
            "hands": [
                {
                    "score": 10,
                    "cards": [
                        "img/card/ace.gif", "img/card/back.gif"
                    ],
                    "bet": 11,
                    "isActive": false
                }
            ],
            "isActive": false
        }, {
            "id": 2,
            "name": "Player Three",
            "img": "img/pl_two.png",
            "hands": [
                {
                    "score": 10,
                    "cards": [
                        "img/card/ace.gif", "img/card/back.gif"
                    ],
                    "bet": 33,
                    "isActive": true
                }
            ],
            "isActive": true
        }
    ],
    "controls": {
        "balance": {
            "value": 89.78
        },
        "actions": {
            "surrender": {
                "isActive": true
            },
            "split": {
                "isActive": true
            },
            "double": {
                "isActive": true
            },
            "hit": {
                "isActive": true
            },
            "deal": {
                "isActive": true
            }
        },
        "bid": {
            "isActive": true
        }
    }
}
