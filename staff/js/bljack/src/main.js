import React from 'react';
import ReactDOM from 'react-dom';
import BlackJackApp from './components/BlackJackApp.jsx';

/*import GameField from './components/GameField.jsx';
import Player from './components/Player.jsx';
import Hand from './components/Hand.jsx';
import bet from './components/bet.jsx';
import Controls from './components/Controls.jsx';
import ControlKey from './components/ControlKey.jsx'
*/

import "./dev-data/some_structures.js"

let game = {
    "dealer": {
        "hand": {
            "score": 10,
            "cards": ["/blackjack/img/card/ace.gif", "/blackjack/img/card/back.gif", "/blackjack/img/card/back.gif"]
        }
    },
    "timer": {
        "timeLimit": 5
    },
    "result": null,
    "players": [
        {
            "id": 0,
            "name": "qqq175",
            "img": "/blackjack/img//user/p_one.jpg",
            "hands": [
                {
                    "score": 10,
                    "cards": [
                        "/blackjack/img/card/ace.gif", "/blackjack/img/card/ace.gif"
                    ],
                    "bet": 10,
                    "isActive": false
                }, {
                    "score": 10,
                    "cards": [
                        "/blackjack/img/card/ace.gif", "/blackjack/img/card/ace.gif", "/blackjack/img/card/ace.gif"
                    ],
                    "bet": 75,
                    "isActive": true
                }
            ],
            "isActive": true
        }, {
            "id": 0,
            "name": "Player Two",
            "img": "/blackjack/img/user/pl_three.jpg",
            "hands": [
                {
                    "score": 10,
                    "cards": [
                        "/blackjack/img/card/ace.gif", "/blackjack/img/card/back.gif"
                    ],
                    "bet": 11,
                    "isActive": false
                }
            ],
            "isActive": false
        }, {
            "id": 0,
            "name": "Player Three",
            "img": "/blackjack/img/user/pl_two.png",
            "hands": [
                {
                    "score": 10,
                    "cards": [
                        "/blackjack/img/card/ace.gif", "/blackjack/img/card/back.gif"
                    ],
                    "bet": 17,
                    "isActive": false
                }
            ],
            "isActive": false
        }
    ],
    "controls": {
        "balance": {
            "value": 0.25
        },
        "actions": {
            "surrender": {
                "isActive": false
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
            },
            "stay": {
                "isActive": true
            },
            "insurance": {
                "isActive": true
            }
        },
        "bid": {
            "isActive": true
        }
    }
}

const gameinit = {
    "dealer": {
        "img": "/blackjack/img/dealer.jpg",
        "name": "Dealer"
    },
    "timer": {
        "text": "Time left:"
    },
    "players": {},
    "controls": {
        "balance": {
            "text": "Current balance"
        },
        "actions": {
            "surrender": {
                "text": "Surrender"
            },
            "split": {
                "text": "Split"
            },
            "double": {
                "text": "Double"
            },
            "hit": {
                "text": "Hit"
            },
            "deal": {
                "text": "Deal"
            },
            "stay": {
                "text": "Stay"
            },
            "insurance": {
                "text": "Insurance"
            }
        },
        "bid": {
            "buttonValues": [1, 5, 25, 100]
        }
    }
}

ReactDOM.render(
    <BlackJackApp game={game} initValues={gameinit}/>, document.getElementById('black-jack'));
